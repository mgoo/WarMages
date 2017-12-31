package main.game.model.entity.unit.attack;

import main.game.model.entity.Unit;
import main.game.model.entity.unit.state.Targetable;
import main.game.model.entity.usable.BaseEffect;
import main.game.model.world.World;
import main.images.UnitSpriteSheet;

public class DamageBuff extends BaseSpellAttack {

  private final double duration;
  private final double amount;

  public DamageBuff(double duration, double amount) {
    super(CanEffect.ALLIES);
    this.duration = duration;
    this.amount = amount;
  }

  @Override
  public void execute(
      Unit unit, Targetable target, World world
  ) {
    target.getEffectedUnits(world).stream().forEach(u -> {
      u.addEffect(new DamageBuffEffect(u, world, this.duration));
    });

  }

  @Override
  double getRange(Unit unit) {
    return 4;
  }

  @Override
  int getAttackSpeed(Unit unit) {
    return 10;
  }

  @Override
  double getDamage(Unit unit) {
    return 0;
  }

  @Override
  public double getWindupPortion(Unit unit) {
    return 0.85;
  }

  @Override
  public UnitSpriteSheet.Sequence getAttackSequence() {
    return UnitSpriteSheet.Sequence.SPELL_CAST;
  }


  @Override
  AttackType getType(Unit unit) {
    return AttackType.DEBUFF;
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
