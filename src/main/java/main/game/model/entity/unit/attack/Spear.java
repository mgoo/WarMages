package main.game.model.entity.unit.attack;

import main.common.entity.Unit;

/**
 * @author Andrew McGhie
 */
public class Spear extends BaseMeleeAttack{

  @Override
  double getRange(Unit unit) {
    return 0.2;
  }

  @Override
  int getAttackSpeed(Unit unit) {
    return 16;
  }

  @Override
  double getDamage(Unit unit) {
    return 10;
  }

  @Override
  public double getWindupPortion(Unit unit) {
    return 0.5;
  }

  @Override
  AttackType getType(Unit unit) {
    return AttackType.STAB;
  }
}
