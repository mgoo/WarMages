package main.game.model.entity;

import java.util.ArrayList;
import main.game.model.entity.usables.Effect;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import main.game.model.world.World;
import main.images.GameImage;
import main.images.UnitSpriteSheet;
import main.util.MapPoint;
import main.util.MapSize;

/**
 * Unit extends{@link Entity}. A unit is a part of a team, specified by an enum colour. It has
 * health, and can attack other team units.
 */
public class Unit extends Attackable implements Damageable {

  private static final long serialVersionUID = 1L;

  private final UnitSpriteSheet spriteSheet;
  private final Team team;

  private boolean isDead = false;
  private UnitType unitType;
  private UnitState unitState;
  private List<Effect> activeEffects = new ArrayList<>();

  /**
   * Constructor takes the unit's position, size, and team.
   */
  public Unit(
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
    this.speed = unitType.getMovingSpeed();
    this.spriteSheet = sheet;
    this.unitState = new IdleUnitState(Direction.DOWN, this);

    setDamageAmount(unitType.getBaselineDamage());
  }

  /**
   * Sets the type of attack the Unit will apply to it's targets.
   *
   * @param healing either true for healing or false for hurting.
   */
  public void setHealing(boolean healing) {
//    this.healing = healing;
  }

  /**
   * Sets the Unit's next state to be the given state.
   *
   * @param state to be changed to.
   */
  private void setNextState(UnitState state) {
    unitState.requestState(Objects.requireNonNull(state));
  }

  public UnitType getUnitType() {
    return unitType;
  }

  /**
   * Sets direction of Unit based on x and y coordinate differences between the given oldPosition
   * and the current position.
   */
  protected Direction updateDirection(MapPoint oldPosition) {
    double gradient = (position.y - oldPosition.y) / (position.x - oldPosition.x);
    if (gradient < 1) {
      if (position.y < oldPosition.y) {
        return Direction.UP;
      } else {
        return Direction.DOWN;
      }
    } else {
      if (position.x < oldPosition.x) {
        return Direction.LEFT;
      } else {
        return Direction.RIGHT;
      }
    }
  }

  /**
   * Returns a DeadUnit to replace the current Unit when it dies.
   *
   * @return DeadUnit to represent dead current Unit.
   */
  public DeadUnit getDeadUnit() {
    return new DeadUnit(position);
  }

  @Override
  public void tick(long timeSinceLastTick, World world) {
    //update image and state if applicable
    unitState.tick(timeSinceLastTick, world);
    unitState = unitState.updateState();
    //update path in case there is a target and it has moved.
    updatePath(world);
    //update position
    MapPoint oldPosition = position;
    super.tick(timeSinceLastTick, world);
    if (!oldPosition.equals(position) && updateDirection(oldPosition) != unitState.getDirection()) {
      setNextState(new WalkingUnitState(updateDirection(oldPosition), this));
    }
    //check if has target and target is within attacking proximity. Request state change.
    if (target != null && targetWithinProximity()) {
      attack();
    }

    tickEffects(timeSinceLastTick);
  }

  @Override
  public GameImage getImage() {
    return unitState.getImage();
  }

  @Override
  protected void attack() {
    if (isDead) {
      throw new IllegalStateException("Is dead");
    }
    if (target == null) {
      throw new IllegalStateException(
          "No target to attack. Check if there is a target before calling attack"
      );
    }

    setNextState(new AttackingUnitState(unitState.getDirection(), this));
  }

  @Override
  public void moveY(double amount) {
    if (isDead) {
      return;
    }
    super.moveY(amount);
  }

  @Override
  public void moveX(double amount) {
    if (isDead) {
      return;
    }
    super.moveX(amount);
  }

  @Override
  public void takeDamage(int amount) {
    if (isDead) {
      return;
    }
    if (health - amount < 0) {
      isDead = true;
      health = 0;
    } else {
      setNextState(new BeenHitUnitState(unitState.getDirection(), this));
      health -= amount;
    }
  }

  @Override
  public void gainHealth(int amount) {
    if (isDead) {
      return;
    }
    health += amount;
  }

  /**
   * Returns the team that the Unit belongs to.
   *
   * @return Team Unit is a part of.
   */
  public Team getTeam() {
    return team;
  }

  /**
   * Returns the current health of the unit.
   *
   * @return int health of the Unit.
   */
  public int getHealth() {
    return health;
  }

  public void addEffect(Effect effect) {
    if (!effect.isTargetUnit(this)) {
      throw new IllegalArgumentException();
    }

    activeEffects.add(effect);
  }

  @Override
  public int getDamageAmount() {
    int amount = super.getDamageAmount();

    for (Effect activeEffect : activeEffects) {
      amount = activeEffect.alterDamageAmount(amount);
    }

    return amount;
  }

  private void tickEffects(long timeSinceLastTick) {
    for (Iterator<Effect> iterator = activeEffects.iterator(); iterator.hasNext(); ) {
      Effect effect = iterator.next();
      effect.tick(timeSinceLastTick);

      if (effect.isExpired()) {
        iterator.remove();
      }
    }
  }

  public Unit getTarget() {
    return target;
  }

  public UnitSpriteSheet getSpriteSheet() {
    return spriteSheet;
  }
}

