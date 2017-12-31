package main.game.model.entity.unit.attack;

import main.game.model.entity.Unit;
import main.images.UnitSpriteSheet;
import main.images.UnitSpriteSheet.Sequence;

/**
 * A dagger attack for melee units that don't have spears.
 * @author Andrew McGhie
 */
public class Dagger extends BaseMeleeAttack {

  public Dagger() {
    super(CanEffect.ENEMIES);
  }

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
  public UnitSpriteSheet.Sequence getAttackSequence() {
    return Sequence.SLASH;
  }

  @Override
  AttackType getType(Unit unit) {
    return AttackType.SLASH;
  }
}
