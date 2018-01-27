package main.game.model.entity.usable;

import main.game.model.entity.Unit;
import main.game.model.world.World;

public class DamageBuffEffect extends BaseEffect {

  private final double amount;

  /**
   * Default constructor.
   *
   * @param durationSeconds Number of seconds before this expires. Set to {@link
   * BaseEffect#INSTANT_EFFECT_DURATION} for one-shot effects.
   */
  public DamageBuffEffect(
      Unit targetUnit, World world,
      double durationSeconds, double amount
  ) {
    super(targetUnit, world, durationSeconds);
    this.amount = amount;
  }

  @Override
  public double alterDamageModifier(double currentDamageModifier) {
    return super.alterDamageModifier(currentDamageModifier) + amount;
  }
}
