package main.game.model.entity;

import java.util.ArrayList;
import java.util.List;
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
    unitState = new DefaultUnitState(Direction.LEFT, sheet);
  }

  /**
   * Sets the Unit's next state to be the given state.
   *
   * @param state to be changed to.
   */
  protected void setStateTo(UnitState state) {
    unitState.requestState(state);
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
  public void tick(long timeSinceLastTick) {
    //update image and state if applicable
    unitState.tick(timeSinceLastTick);
    unitState = unitState.updateState();
    //update path in case there is a target and it has moved.
    super.updatePath();
    //update position
    MapPoint oldPosition = position;
    super.tick(timeSinceLastTick);
    if (!oldPosition.equals(position) && updateDirection(oldPosition) != unitState.getDirection()) {
      setStateTo(new WalkingUnitState(updateDirection(oldPosition), spriteSheet));
    }
    //check if has target and target is within attacking proximity. Request state change.
    if (targetWithinProximity()) {
      attack(target);
      setStateTo(new AttackingUnitState(unitState.getDirection(), spriteSheet, unitType));
    }
  }

  @Override
  public GameImage getImage() {
    return unitState.getImage();
  }

  @Override
  public void setImage(GameImage image) {
    //will not change anything
  }

  @Override
  public void attack(Unit unit) {
    if (!unit.equals(target) || isDead || target == null) {
      return;
    }
    setStateTo(new AttackingUnitState(unitState.getDirection(), spriteSheet, unitType));
    if (team.canAttackOtherTeam(unit.team)) {
      unit.takeDamage(damageAmount);
    }
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
      setStateTo(new BeenHitUnitState(unitState.getDirection(), spriteSheet));
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
}

