package main.game.model.entity.unit.attack;

import main.common.entity.Unit;
import main.common.images.UnitSpriteSheet;
import main.common.images.UnitSpriteSheet.Sequence;

/**
 * Spear attack.
 * @author Andrew McGhie
 */
public class Spear extends BaseMeleeAttack {

  public Spear() {
    super(CanEffect.ENEMIES);
  }

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
    return 0.625;
  }

  @Override
  public UnitSpriteSheet.Sequence getAttackSequence() {
    return Sequence.THRUST;
  }

  @Override
  AttackType getType(Unit unit) {
    return AttackType.STAB;
  }
}
