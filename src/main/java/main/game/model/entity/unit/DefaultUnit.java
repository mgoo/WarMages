package main.game.model.entity.unit;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import main.game.model.data.DataLoader;
import main.game.model.data.dataObject.ImageData;
import main.game.model.data.dataObject.SpriteSheetData;
import main.game.model.data.dataObject.UnitData;
import main.game.model.entity.DefaultEntity;
import main.game.model.entity.StaticEntity;
import main.game.model.entity.unit.attack.Attack;
import main.game.view.Renderable;
import main.game.model.entity.Team;
import main.game.model.entity.Unit;
import main.game.model.entity.unit.state.Idle;
import main.game.model.entity.unit.state.Target;
import main.game.model.entity.unit.state.Targetable;
import main.game.model.entity.unit.state.UnitState;
import main.game.model.entity.usable.Effect;
import main.game.model.world.World;
import main.game.view.ViewVisitor;
import main.images.Animation;
import main.util.Config;
import main.util.Event;
import main.util.MapPoint;
import main.util.MapSize;

/**
 * Default Unit implementation.
 * @author paladogabr
 * @author chongdyla (Secondary author)
 */
public class DefaultUnit extends DefaultEntity implements Unit, Targetable {

  private static final long serialVersionUID = 1L;
  private static final double LEVEL_DIVISOR = 10;
  private static final double UNIT_MAX_SPEED = 0.12;
  private static final double UNIT_MAX_SIZE = 1;

  private final SpriteSheetData spriteSheet;
  private final Team team;

  private UnitState unitState;
  private List<Effect> activeEffects = new ArrayList<>();
  private boolean isDead = false;

  private boolean hasCreatedDeadUnit = false;

  private int level;
  private double health;

  private MapSize originalSize;
  private Attack baseAttack;
  private UnitData unitData;

  private final Event<Double> damagedEvent = new Event<>();
  private final Event<Double> healedEvent = new Event<>();

  /**
   * Constructor takes the unit's position, size, and team.
   * Defaults the level to 0
   */
  public DefaultUnit(
      UnitData unitData,
      MapPoint position,
      Team team,
      DataLoader dataLoader
  ) {
    this(unitData, position, team,  dataLoader, 0);
  }

  /**
   * Constructor takes the unit's position, size, and team.
   */
  public DefaultUnit(
      UnitData unitData,
      MapPoint position,
      Team team,
      DataLoader dataLoader,
      int level
  ) {
    super(position, new MapSize(unitData.getSize(), unitData.getSize()));
    this.unitData = unitData;
    this.level = level;
    this.originalSize = new MapSize(unitData.getSize(), unitData.getSize());
    this.setSize(
        new MapSize(this.levelMultiplyer(this.originalSize.width, UNIT_MAX_SIZE),
        this.levelMultiplyer(this.originalSize.height, UNIT_MAX_SIZE)));
    this.team = team;
    this.health = this.levelMultiplyer(unitData.getStartingHealth());
    this.spriteSheet = unitData.getSpritesheetData();
    this.unitState = new Idle(this);
    this.baseAttack = new Attack(unitData.getBaseAttackData(), dataLoader);
  }

  /**
   * Buffs the base stats based on what level the unit is on.
   * @param val the value of the base stat
   */
  private double levelMultiplyer(double val) {
    return val * this.getLevelMultiplyer();
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

  private double getLevelMultiplyer() {
    return (1 + ((double)this.level / LEVEL_DIVISOR));
  }

  /**
   * Sets the level and adjusts the size of the unit accordingly.
   */
  public void setLevel(int level) {
    this.level = level;
    this.setSize(new MapSize(this.levelMultiplyer(this.originalSize.width, UNIT_MAX_SIZE),
        this.levelMultiplyer(this.originalSize.height, UNIT_MAX_SIZE)));
  }

  @Override
  public double getLineOfSight() {
    return this.unitData.getLineOfSight();
  }

  @Override
  public void tick(long timeSinceLastTick, World world) {
    //update image and state if applicable
    this.unitState.tick(timeSinceLastTick, world);
    tickEffects(timeSinceLastTick);
  }

  @Override
  public void setState(UnitState state) {
    this.unitState = state;
  }

  @Override
  public ImageData getImage() {
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
  public Attack getBaseAttack() {
    return this.baseAttack;
  }

  @Override
  public void takeDamage(double amount, World world, Unit attacker) {
    if (isDead) {
      return;
    }
    if (amount < 0) {
      throw new IllegalArgumentException("Amount: " + amount);
    }

    unitState.onTakeDamage(amount, world, attacker);

    if (health - amount > 0) {
      // Not dead
      health -= amount;
      this.damagedEvent.broadcast(amount);
    } else {
      this.die(world);
      attacker.nextLevel();
    }
  }

  private void die(World world) {
    isDead = true;
    health = 0;
    world.removeUnitEntity(this);
    StaticEntity deadUnit = new StaticEntity(
        this.getTopLeft(),
        this.getSize(),
        new Animation(this.getSpriteSheet(), "animation:dying", 5),
        this.getCurrentAngle()
    );
    deadUnit.setLayer(3);
    world.addStaticEntity(deadUnit);
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

    if (health > this.levelMultiplyer(this.unitData.getStartingHealth())) {
      health = this.levelMultiplyer(this.unitData.getStartingHealth());
    }
    this.healedEvent.broadcast(amount);
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

  public double getMaxHealth() {
    return this.levelMultiplyer(this.unitData.getStartingHealth());
  }

  @Override
  public double getHealthPercent() {
    return this.getHealth() / this.getMaxHealth();
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
  public SpriteSheetData getSpriteSheet() {
    return spriteSheet;
  }

  @Override
  public ImageData getIcon() {
    return this.spriteSheet.getImage("animation:idle", 0, 0);
  }

  /**
   * Gets the direction from the current state.
   */
  @Override
  public double getCurrentAngle() {
    if (unitState == null) {
      return 0;
    }
    return unitState.getCurrentAngle();
  }

  @Override
  public void setTarget(Target target) {
    this.setState(target.getState());
  }

  @Override
  public boolean contains(MapPoint point) {
    return getRect().contains(point);
  }

  @Override
  public Renderable accept(
      Config config, ViewVisitor viewVisitor
  ) {
    return viewVisitor.makeUnitView(config, this);
  }

  @Override
  public boolean isSameTypeAs(Unit other) {
    return this.getType().equals(other.getType());
  }

  @Override
  public double getSpeed() {
    return this.levelMultiplyer(this.unitData.getMovementSpeed(), UNIT_MAX_SPEED);
  }

  @Override
  public double getAutoAttackDistance() {
//    double range = this.baseAttack.getModifiedRange(this);
//    double los = this.unitData.getLineOfSight();
//    return Math.max(los * 0.7, range);
     return 1;
  }

  @Override
  public Event<Double> getDamagedEvent() {
    return this.damagedEvent;
  }

  @Override
  public Event<Double> getHealedEvent() {
    return this.healedEvent;
  }

  @Override
  public String getType() {
    return this.unitData.getId();
  }

  @Override
  public void nextLevel() {
    double originalHealth = this.getHealthPercent();
    this.setLevel(this.level + 1);
    // Maintain current level of health
    this.health = levelMultiplyer(this.unitData.getStartingHealth()) * originalHealth;
  }

  @Override
  public int getLevel() {
    return this.level;
  }

  @Override
  public double getAttackSpeedModifier() {
    return 1;
  }

  @Override
  public double getDamageModifier() {
    double amount = 1;

    for (Effect activeEffect : activeEffects) {
      amount = activeEffect.alterDamageModifier(amount);
    }

    return amount + this.getLevelMultiplyer();
  }

  @Override
  public double getRangeModifier() {
    return 1;
  }

  @Override
  public MapPoint getLocation() {
    return this.getCentre();
  }

  @Override
  public List<Unit> getEffectedUnits(World world, double radius) {
    if (radius == 0) {
      return Collections.singletonList(this);
    } else {
      return world.getAllUnits().stream()
          .filter(unit -> unit.getCentre().distanceTo(this.getLocation()) < radius)
          .collect(Collectors.toList());
    }
  }

  /**
   * TODO check diplomacy and stuff here need attack
   */
  @Override
  public boolean isValidTargetFor(Unit unit) {
    return !this.isDead;
  }
}
