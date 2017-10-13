package main.game.model.entity.unit;

import static java.util.Objects.requireNonNull;

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
import main.game.model.entity.unit.state.AttackingUnitState;
import main.game.model.entity.unit.state.DeadUnit;
import main.game.model.entity.unit.state.DyingState;
import main.game.model.entity.unit.state.IdleUnitState;
import main.game.model.entity.unit.state.UnitState;
import main.game.model.entity.unit.state.WalkingUnitState;
import main.game.model.world.World;

/**
 * Default Unit implementation.
 * @author paladogabr
 * @author chongdyla (Secondary author)
 */
public class DefaultUnit extends DefaultEntity implements Unit {

  private static final long serialVersionUID = 1L;

  private final UnitSpriteSheet spriteSheet;
  private final Team team;

  private Unit target;
  private UnitType unitType;
  private UnitState unitState;
  private List<Effect> activeEffects = new ArrayList<>();

  private boolean isDead = false;
  private boolean hasCreatedDeadUnit = false;

  private int health;
  private int damageAmount;
  private final double attackDistance;
  private double speed;

  /**
   * Constructor takes the unit's position, size, and team.
   */
  public DefaultUnit(
      MapPoint position,
      MapSize size,
      Team team,
      UnitSpriteSheet sheet,
      UnitType unitType
  ) {
    super(position, size);
    this.team = team;
    this.unitType = unitType;
    this.health = unitType.getStartingHealth();
    this.spriteSheet = sheet;
    this.unitState = new IdleUnitState(this);
    this.speed = unitType.getMovingSpeed();
    this.attackDistance = unitType.getAttackDistance();
    setDamageAmount(unitType.getBaselineDamage());
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
    return this.unitType.lineOfSight;
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
    UnitState nextState = requireNonNull(this.unitState.updateState());
    if (nextState != unitState) {
      // Tests rely on unit moving immediately. Unit starts in Idle state and if target is set
      // it takes one tick to move to the walking state, then another to start moving.
      unitState = nextState;
    }
    // TODO
//    //check if has target and target is within attacking proximity. Request state change.
//    if (target != null && targetWithinProximity()) {
//      attack();
//    } else {
//      //if no target, check if unit reached destination and change to idle if so
//      if (this.path.size() == 0) {
//        setNextState(new IdleUnitState(this));
//      }
//    }
    tickEffects(timeSinceLastTick);
  }

  @Override
  public GameImage getImage() {
    return unitState.getImage();
  }

  private void attack() {
    if (isDead) {
      throw new IllegalStateException("Is dead");
    }
    if (target == null) {
      throw new IllegalStateException(
          "No target to attack. Check if there is a target before calling attack"
      );
    }

    setNextState(new AttackingUnitState(this));
  }

  @Override
  public void translatePosition(double dx, double dy) {
    if (isDead) {
      return;
    }
    super.translatePosition(dx, dy);
  }

  @Override
  public void takeDamage(int amount, World world) {
    if (isDead) {
      return;
    }
    if (amount < 0) {
      throw new IllegalArgumentException("Amount: " + amount);
    }

    if (health - amount >= 0) {
      // Not dead
      health -= amount;
    } else {
      isDead = true;
      health = 0;
      setNextState(new DyingState(Sequence.DYING, this));
    }
  }

  @Override
  public void gainHealth(int amount) {
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
  public int getHealth() {
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
  public int getDamageAmount() {
    int amount = damageAmount;

    for (Effect activeEffect : activeEffects) {
      amount = activeEffect.alterDamageAmount(amount);
    }

    return amount;
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

  /**
   * Gets the direction from the current state.
   */
  @Override
  public Direction getCurrentDirection() {
    return unitState.getCurrentDirection();
  }

  @Override
  public void setTargetUnit(Unit targetUnit, World world) {
    this.target = requireNonNull(targetUnit);
    setNextState(new WalkingUnitState(this, targetUnit));
  }

  @Override
  public void setTargetPoint(MapPoint targetPoint, World world) {
    setNextState(new WalkingUnitState(this, targetPoint));
  }

  @Override
  public void clearTarget() {
    this.target = null;
  }

  @Override
  public void setDamageAmount(int amount) {
    if (amount <= 0 || amount >= 100) {
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
    return health / unitType.getStartingHealth();
  }

  public double getSpeed() {
    return speed;
  }

  /**
   * Public for testing only!
   */
  public UnitState _getUnitState() {
    return unitState;
  }

  /**
   * Returns boolean whether the distance between the target and the Unit is less than the
   * attackDistance.
   *
   * @return boolean representing distance less than attackDistance.
   */
  private boolean targetWithinProximity() {
    if (target == null) {
      throw new IllegalStateException("No target set");
    }
    return target.getCentre().distanceTo(getCentre()) < attackDistance;
  }
}
