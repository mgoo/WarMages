package main.game.model.entity;

import java.util.Objects;
import main.game.model.world.World;
import main.game.model.world.pathfinder.PathFinder;
import main.common.util.MapPoint;
import main.common.util.MapSize;

/**
 * Attackables can attack units.
 */
public abstract class Attackable extends MovableEntity {

  private static final long serialVersionUID = 1L;

  public static final int LEEWAY = 5; // TODO don't hard code
  protected Unit target;
  protected int health;
  private int damageAmount;

  /**
   * Constructor takes the position of the entity, the size, as well as it's speed.
   *
   * @param position = position of Entity.
   * @param size = size of Entity.
   * @param speed = speed of MovableEntity
   */
  public Attackable(MapPoint position, MapSize size, double speed) {
    super(position, size, speed);
  }

  /**
   * Attacks the current target.
   */
  protected abstract void attack();

  /**
   * Set's the Unit's target to the given Unit.
   *
   * @param target to be attacked
   */
  public void setTarget(Unit target, World world) {
    this.target = Objects.requireNonNull(target);
    updatePath(world);
  }

  public void clearTarget() {
    this.target = null;
  }

  /**
   * Updates the path in case target has moved.
   */
  protected void updatePath(World world) {
    if (target == null) {
      return;
    }
    setPath(world.getPathfinder().findPath(world::isPassable, position, target.getCentre()));
  }

  /**
   * Sets the damage amount of this Unit's attack to the given amount. Must be 0 < amount < 100.
   *
   * @param amount of damage to deal to target.
   */
  public void setDamageAmount(int amount) {
    if (amount <= 0 || amount >= 100) {
      throw new IllegalArgumentException("Invalid damage: " + amount);
    }

    damageAmount = amount;
  }

  public int getDamageAmount() {
    return damageAmount;
  }

  /**
   * Returns boolean whether the distance between the target and the Attackable is less than the
   * leeway.
   *
   * @return boolean representing distance less than leeway.
   */
  public boolean targetWithinProximity() {
    if (target == null) {
      throw new IllegalStateException("No target set");
    }
    return target.getCentre().distanceTo(getCentre()) < LEEWAY;
  }

}
