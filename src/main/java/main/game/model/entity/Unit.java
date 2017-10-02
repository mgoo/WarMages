package main.game.model.entity;

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

  protected final Team team;
  protected boolean isDead;
  protected boolean healing;
  protected UnitSpriteSheet spriteSheet;
  protected UnitType unitType;
  protected UnitState unitState;

  /**
   * Constructor takes the unit's position, size, and team.
   */
  public Unit(
      MapPoint position, MapSize size, Team team, UnitSpriteSheet sheet, UnitType unitType
  ) {
    super(position, size);
    this.team = team;
    this.unitType = unitType;
    isDead = false;
    health = unitType.getStartingHealth();
    speed = unitType.getMovingSpeed();
    damageAmount = unitType.getBaselineDamage();
    spriteSheet = sheet;
    unitState = UnitState.DEFAULT_STATE;
    unitState.setDirection(Direction.LEFT);
  }

  /**
   * Sets the type of attack the Unit will apply to it's targets.
   *
   * @param healing either true for healing or false for hurting.
   */
  public void setHealing(boolean healing) {
    this.healing = healing;
  }

  /**
   * Sets the Unit's next state to be the given state.
   *
   * @param state to be changed to.
   */
  private void setStateTo(UnitState state) {
    unitState.requestState(state);
  }

  public UnitType getUnitType() {
    return unitType;
  }

  /**
   * Sets direction of Unit based on x and y coordinate differences between the given oldPosition
   * and the current position.
   */
  private void updateDirection(MapPoint oldPosition) {
    double gradient = (position.y - oldPosition.y) / (position.x - oldPosition.x);
    if (gradient < 1) {
      if (position.y < oldPosition.y) {
        unitState.setDirection(Direction.UP);
      } else {
        unitState.setDirection(Direction.DOWN);
      }
    } else {
      if (position.x < oldPosition.x) {
        unitState.setDirection(Direction.LEFT);
      } else {
        unitState.setDirection(Direction.RIGHT);
      }
    }
  }

  @Override
  public void tick(long timeSinceLastTick) {
    //update image and state if applicable
    unitState.tick(timeSinceLastTick);
    unitState = unitState.updateState();
    //update position
    MapPoint oldPosition = position;
    super.tick(timeSinceLastTick);
    //check if position changed
    if (!oldPosition.equals(position)) {
      setStateTo(UnitState.WALKING);
      updateDirection(oldPosition);
    }
    //check if has target and target is within attacking proximity
    if (target != null && targetWithinProximity()) {
      attack();
      setStateTo(UnitState.ATTACKING);
    }
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

    setStateTo(UnitState.ATTACKING);
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
      setStateTo(UnitState.BEEN_HIT);
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

  public Team getTeam() {
    return team;
  }

  public int getHealth() {
    return health;
  }
}

