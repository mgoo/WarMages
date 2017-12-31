package main.game.model.entity.unit.attack;

import java.util.Collection;
import java.util.stream.Collectors;
import main.common.World;
import main.common.entity.Unit;
import main.common.images.UnitSpriteSheet;
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
  public UnitSpriteSheet.Sequence getAttackSequence() {
    return UnitSpriteSheet.Sequence.SPELL_CAST;
  }

  @Override
  AttackType getType(Unit unit) {
    return AttackType.HEAL;
  }

  @Override
  public Collection<Unit> getEffectedUnits(
      Unit owner, World world, Targetable target
  ) {
    return super.getEffectedUnits(owner, world, target)
        .stream()
        .filter(u -> u.getHealthPercent() < 0.999)
        .collect(Collectors.toSet());
  }
}
