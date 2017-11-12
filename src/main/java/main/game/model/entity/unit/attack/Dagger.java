package main.game.model.entity.unit.attack;

import main.common.entity.Unit;

/**
 * A dagger attack for melee units that don't have spears.
 * @author Andrew McGhie
 */
public class Dagger extends BaseMeleeAttack {

  @Override
  double getRange(Unit unit) {
    return 0.02;
  }

  @Override
  int getAttackSpeed(Unit unit) {
    return 14;
  }

  @Override
  double getDamage(Unit unit) {
    return 15;
  }

  @Override
  public double getWindupPortion(Unit unit) {
    return 0.66;
  }

  @Override
  AttackType getType(Unit unit) {
    return AttackType.SLASH;
  }
}
