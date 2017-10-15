package main.game.model.entity.unit;

import static java.util.Objects.requireNonNull;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import main.common.entity.Direction;
import main.common.entity.Team;
import main.common.entity.Unit;
import main.common.entity.usable.Effect;
import main.common.images.GameImage;
import main.common.images.UnitSpriteSheet;
import main.common.images.UnitSpriteSheet.Sequence;
import main.common.util.MapPoint;
import main.common.util.MapSize;
import main.game.model.entity.DefaultEntity;
import main.game.model.entity.unit.state.DyingState;
import main.game.model.entity.unit.state.IdleUnitState;
import main.game.model.entity.unit.state.UnitState;
import main.game.model.entity.unit.state.WalkingUnitState;
import main.game.model.entity.unit.state.WalkingUnitState.EnemyUnitTarget;
import main.game.model.entity.unit.state.WalkingUnitState.MapPointTarget;
import main.game.model.world.World;

/**
 * Default Unit implementation.
 * @author paladogabr
 * @author chongdyla (Secondary author)
 */
public class DefaultUnit extends DefaultEntity implements Unit {

  private static final long serialVersionUID = 1L;
  private static final double LEVEL_DIVISOR = 10;
  private static final double UNIT_MAX_SPEED = 0.12;
  private static final double UNIT_MAX_SIZE = 1;

  private final UnitSpriteSheet spriteSheet;
  private final Team team;
  private Unit target;

  private UnitType unitType;
  private UnitState unitState;
  private List<Effect> activeEffects = new ArrayList<>();
  private boolean isDead = false;

  private boolean hasCreatedDeadUnit = false;

  private int level;
  private double health;
  private double damageAmount;
  private final double attackDistance;
  private double speed;
  private MapSize originalSize;

  /**
   * Constructor takes the unit's position, size, and team.
   * Defaults the level to 0
   */
  public DefaultUnit(
      MapPoint position,
      MapSize size,
      Team team,
      UnitSpriteSheet sheet,
      UnitType unitType
  ) {
    this(position, size, team, sheet, unitType, 0);
  }

  /**
   * Constructor takes the unit's position, size, and team.
   */
  public DefaultUnit(
      MapPoint position,
      MapSize size,
      Team team,
      UnitSpriteSheet sheet,
      UnitType unitType,
      int level
  ) {
    super(position, size);
    this.level = level;
    this.originalSize = size;
    this.setSize(new MapSize(this.levelMultiplyer(size.width, UNIT_MAX_SIZE),
        this.levelMultiplyer(size.height, UNIT_MAX_SIZE)));
    this.team = team;
    this.unitType = unitType;
    this.health = this.levelMultiplyer(unitType.getStartingHealth());
    this.spriteSheet = sheet;
    this.unitState = new IdleUnitState(this);

    this.speed = unitType.getMovingSpeed();
    this.attackDistance = unitType.getAttackDistance();
    setDamageAmount(unitType.getBaselineDamage());
  }

  /**
   * Buffs the base stats based on what level the unit is on.
   * @param val the value of the base stat
   */
  private double levelMultiplyer(double val) {
    return val * (1 + ((double)this.level / LEVEL_DIVISOR));
  }

  /**
   * Buffs the base stats based on what level  the unit is on.
   * Cannot go above max
   * @param val the value of the base stat
   * @param max the maximum that the stat can be
   */
  private double levelMultiplyer(double val, double max) {
    return Math.min(max, levelMultiplyer(val));
  }

  /**
   * Sets the level and adjusts the size of the unit accordingly.
   */
  public void setLevel(int level) {
    this.level = level;
    this.setSize(new MapSize(this.levelMultiplyer(this.originalSize.width, UNIT_MAX_SIZE),
        this.levelMultiplyer(this.originalSize.height, UNIT_MAX_SIZE)));
  }



  /**
   * Sets the Unit's next state to be the given state.
   *
   * @param state to be changed to.
   */
  private void setNextState(UnitState state) {
    unitState.requestState(requireNonNull(state));
  }

  public UnitType getUnitType() {
    return unitType;
  }

  @Override
  public double getLineOfSight() {
    return this.levelMultiplyer(this.unitType.lineOfSight);
  }

  @Override
  public DeadUnit createDeadUnit() {
    if (!isDead || hasCreatedDeadUnit) {
      throw new IllegalStateException();
    }

    hasCreatedDeadUnit = true;
    GameImage deadImage = spriteSheet.getImagesForSequence(Sequence.DEAD, Direction.DOWN).get(0);
    return new DeadUnit(getTopLeft(), getSize(), deadImage);
  }

  @Override
  public void tick(long timeSinceLastTick, World world) {
    //update image and state if applicable
    unitState.tick(timeSinceLastTick, world);
    unitState = requireNonNull(this.unitState.updateState());
    tickEffects(timeSinceLastTick);
  }

  @Override
  public GameImage getImage() {
    return unitState.getImage();
  }

  @Override
  public void translatePosition(double dx, double dy) {
    if (isDead) {
      return;
    }
    super.translatePosition(dx, dy);
  }

  @Override
  public boolean takeDamage(double amount, World world) {
    if (isDead) {
      return false;
    }
    if (amount < 0) {
      throw new IllegalArgumentException("Amount: " + amount);
    }

    if (health - amount > 0) {
      // Not dead
      health -= amount;
      return false;
    } else {
      isDead = true;
      health = 0;
      setNextState(new DyingState(Sequence.DYING, this));
      return true;
    }
  }

  @Override
  public void gainHealth(double amount) {
    if (isDead) {
      return;
    }
    if (amount < 0) {
      throw new IllegalArgumentException("Amount: " + amount);
    }

    health += amount;
  }

  /**
   * Returns the team that the Unit belongs to.
   *
   * @return Team Unit is a part of.
   */
  @Override
  public Team getTeam() {
    return team;
  }

  /**
   * Returns the current health of the unit.
   *
   * @return int health of the Unit.
   */
  @Override
  public double getHealth() {
    return health;
  }

  /**
   * Add a new effect and start it.
   */
  @Override
  public void addEffect(Effect effect) {
    if (!effect.isTargetUnit(this)) {
      throw new IllegalArgumentException();
    }

    effect.start();

    // The effect may expire immediately
    if (!effect.isExpired()) {
      activeEffects.add(effect);
    }
  }

  @Override
  public double getDamageAmount() {
    double amount = damageAmount;

    for (Effect activeEffect : activeEffects) {
      amount = activeEffect.alterDamageAmount(amount);
    }

    return this.levelMultiplyer(amount);
  }

  private void tickEffects(long timeSinceLastTick) {
    for (Iterator<Effect> iterator = activeEffects.iterator(); iterator.hasNext(); ) {
      Effect effect = iterator.next();

      effect.tick(timeSinceLastTick);

      // Expire after tick
      if (effect.isExpired()) {
        iterator.remove();
      }
    }
  }

  @Override
  public Unit getTarget() {
    return target;
  }

  @Override
  public UnitSpriteSheet getSpriteSheet() {
    return spriteSheet;
  }

  @Override
  public GameImage getIcon() {
    return this.spriteSheet.getImagesForSequence(Sequence.IDLE, Direction.DOWN).get(0);
  }

  /**
   * Gets the direction from the current state.
   */
  @Override
  public Direction getCurrentDirection() {
    return unitState.getCurrentDirection();
  }

  @Override
  public void setTargetUnit(Unit targetUnit) {
    this.target = requireNonNull(targetUnit);
    setNextState(new WalkingUnitState(this, new EnemyUnitTarget(this, targetUnit)));
  }

  @Override
  public void setTargetPoint(MapPoint targetPoint) {
    setNextState(new WalkingUnitState(this, new MapPointTarget(this, targetPoint)));
  }

  @Override
  public void clearTarget() {
    this.target = null;
  }

  private void setDamageAmount(double amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Invalid damage: " + amount);
    }

    damageAmount = amount;
  }

  @Override
  public boolean contains(MapPoint point) {
    return getRect().contains(point);
  }

  @Override
  public double getHealthPercent() {
    return health / this.levelMultiplyer(unitType.getStartingHealth());
  }

  public double getSpeed() {
    return this.levelMultiplyer(speed, UNIT_MAX_SPEED);
  }

  /**
   * Public for testing only.
   */
  public UnitState _getUnitState() {
    return unitState;
  }

  public double getAttackDistance() {
    return this.levelMultiplyer(attackDistance);
  }


  @Override
  public UnitType getType() {
    return this.unitType;
  }

  @Override
  public void nextLevel() {
    double originalHealth = this.getHealthPercent();
    this.setLevel(this.level + 1);
    // Maintain current level of health
    this.gainHealth(levelMultiplyer(this.unitType.getStartingHealth()) * originalHealth
        - this.health);
  }

  @Override
  public int getLevel() {
    return this.level;
  }
}
