package main.game.model.entity;

/**
 * Damageables can take damage and gain health.
 */
public interface Damageable {

  /**
   * Applies the given damage to the Damageable.
   * Requires the amount given is a positive integer.
   * @param amount
   */
  void takeDamage(int amount);

  /**
   * Gives the Damageable the given amount of health.
   * Requires the amount given is a positive integer.
   * @param amount
   */
  void gainHealth(int amount);
}
