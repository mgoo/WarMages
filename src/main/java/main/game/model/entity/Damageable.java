package main.game.model.entity;

import main.game.model.world.World;

/**
 * Damageables can take damage and gain health.
 */
public interface Damageable {

  /**
   * Applies the given damage to the Damageable. Requires the amount given is a positive integer.
   */
  void takeDamage(int amount, World world);

  /**
   * Gives the Damageable the given amount of health. Requires the amount given is a positive
   * integer.
   */
  void gainHealth(int amount);
}
