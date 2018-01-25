package main.game.model.entity.unit.attack;

import main.game.model.entity.Unit;
import main.images.UnitSpriteSheet.Sequence;

public class Attack extends FixedAttack {

  private final double amount;

  public Attack(
      CanEffect canEffect, String scriptLocation, double range, int attackSpeed,
      double windupPortion,
      Sequence attackSequence,
      AttackType attackType,
      double amount
  ) {
    super(canEffect, scriptLocation, range, attackSpeed, windupPortion, attackSequence, attackType);

    this.amount = amount;
  }

  /**
   * Base damage of the attack.
   */
  double getAmount() {
    return this.amount;
  }

  public double getModifiedAmount(Unit unit) {
    return this.getAmount() * unit.getDamageModifier();
  }

}
