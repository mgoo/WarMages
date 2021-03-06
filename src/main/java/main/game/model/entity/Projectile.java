package main.game.model.entity;

import main.game.model.world.World;

/**
 * Projectile extends {@link Entity}. A projectile is fired by a unit at a target (another unit) and
 * affects it in some way upon impact.
 * @author paladogabr
 */
public interface Projectile extends Entity {

  /**
   * Returns a boolean based on whether the Projectile has hit it's target or not.
   *
   * @return boolean hit target
   */
  boolean hasHit();

  /**
   * Applies actions to given unit when it is hit by the Projectile.
   */
  void hitTarget(World world);

  /**
   * Returns the distance to the target.
   *
   * @return double distance
   */
  double getDistanceToTarget();
}

