package main.game.model.entity;

import java.util.function.Function;
import main.game.model.world.pathfinder.PathFinder;
import main.util.MapPoint;
import main.util.MapSize;

/**
 * Attackables can attack units.
 */
public abstract class Attackable extends MovableEntity {

  private static final long serialVersionUID = 1L;

  public static final int LEEWAY = 5;
  protected Unit target;
  protected int damageAmount;
  protected int health;

  /**
   * Constructor takes the position of the entity and the size.
   *
   * @param position = position of Entity.
   * @param size = size of Entity.
   */
  public Attackable(MapPoint position, MapSize size) {
    super(position, size);
  }

  /**
   * Attacks the given unit.
   *
   * @param unit to be attacked
   */
  abstract void attack(Unit unit);

  /**
   * Set's the Unit's target to the given Unit.
   *
   * @param target to be attacked
   */
  public void setTarget(Unit target) {
    assert target != null;
    //todo isPassable function
    updatePath();
  }

  /**
   * Updates the path in case target has moved.
   */
  public void updatePath() {
    //todo isPassable function
    Function<MapPoint, Boolean> isPassable = mapPoint -> true;
    if (target != null) {
      super.setPath(PathFinder.findPath(isPassable, position, target.getCentre()));
    }
  }

  /**
   * Sets the damage amount of this Unit's attack to the given amount. Must be 0 < amount < 100.
   *
   * @param amount of damage to deal to target.
   */
  public void setDamageAmount(int amount) {
    assert amount > 0 && amount < 100;
    damageAmount = amount;
  }

  /**
   * Returns boolean whether the distance between the target and the Attackable is less than the
   * leeway.
   *
   * @return boolean representing distance less than leeway.
   */
  public boolean targetWithinProximity() {
    if (target == null) {
      return false;
    }
    return target.getCentre().distance(getCentre()) < LEEWAY;
  }

}
