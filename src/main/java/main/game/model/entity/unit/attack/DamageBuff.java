package main.game.model.entity.unit.attack;

import main.game.model.entity.Unit;
import main.game.model.entity.usable.BaseEffect;
import main.game.model.world.World;
import main.images.UnitSpriteSheet;
import main.images.UnitSpriteSheet.Sequence;

public class DamageBuff extends FixedAttack {

  private final double duration;
  private final double amount;

  public DamageBuff(double duration, double amount) {
    super(CanEffect.ALLIES,
        "resources/scripts/damageBuff.js",
        4,
        10,
        0.85,
        Sequence.SPELL_CAST,
        AttackType.MAGIC);
    this.duration = duration;
    this.amount = amount;
  }

  /**
   * Expose the DamageBuffEffect to javascript.
   */
  public DamageBuffEffect makeEffect(Unit targetUnit, World world) {
    return new DamageBuffEffect(targetUnit, world, this.duration);
  }

  private class DamageBuffEffect extends BaseEffect {

    private static final long serialVersionUID = 1L;

    DamageBuffEffect(Unit targetUnit, World world, double durationSeconds) {
      super(targetUnit, world, durationSeconds);
    }

    @Override
    public double alterDamageModifier(double currentDamageModifier) {
      return super.alterDamageModifier(currentDamageModifier) + amount;
    }

  }
}
