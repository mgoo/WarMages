package main.game.model.entity;

/**
 * Damageables can take damage and gain health.
 */
public interface Damageable {
  int startingHealth = 200; //todo refactor for special starting health per unit

  /**
   * Applies the given damage to the Damageable. Requires the amount given is a positive integer.
   */
  void takeDamage(int amount);

  /**
   * Gives the Damageable the given amount of health. Requires the amount given is a positive
   * integer.
   */
  void gainHealth(int amount);
}
