package main.game.model.entity.unit.attack;

import main.common.entity.Projectile;
import main.common.entity.Unit;
import main.common.images.SpriteSheet.Sequence;
import main.common.images.SpriteSheet.Sheet;
import main.common.util.MapSize;
import main.game.model.entity.DefaultProjectile;

/**
 * A basic arrow attack for any archer.
 * @author Andrew McGhie
 */
public class BasicArrow extends BaseRangedAttack {

  @Override
  Projectile createProjectile(
      Unit unit, Unit enemyUnit
  ) {
    return new DefaultProjectile(
        unit.getCentre(),
        new MapSize(0.7, 0.7),
        enemyUnit,
        unit,
        this,
        Sheet.ARROW_PROJECTILE,
        Sequence.ARROW,
        Sequence.ARROW,
        0.5
    );
  }

  @Override
  double getRange(Unit unit) {
    return 5;
  }

  @Override
  int getAttackSpeed(Unit unit) {
    return 20;
  }

  @Override
  double getDamage(Unit unit) {
    return 18;
  }

  @Override
  public double getWindupPortion(Unit unit) {
    return 0.7;
  }

  @Override
  AttackType getType(Unit unit) {
    return AttackType.MISSILE;
  }
}
