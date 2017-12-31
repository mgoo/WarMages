package main.game.model.entity.unit.attack;

import main.common.World;
import main.common.entity.Unit;
import main.game.model.entity.unit.state.Targetable;

public class HealAttack extends BaseSpellAttack {

  private final double amount;

  public HealAttack(double amount) {
    super(CanEffect.ALLIES);
    this.amount = amount;
  }

  @Override
  public void execute(
      Unit unit, Targetable target, World world
  ) {
    target.getEffectedUnits(world).forEach(u -> {
      u.gainHealth(this.getModifiedDamage(unit), world);
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
    return this.amount;
  }

  @Override
  public double getWindupPortion(Unit unit) {
    return 0.85;
  }

  @Override
  AttackType getType(Unit unit) {
    return AttackType.HEAL;
  }
}
