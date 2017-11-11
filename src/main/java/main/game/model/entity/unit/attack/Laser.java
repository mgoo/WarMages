package main.game.model.entity.unit.attack;

import main.common.entity.Projectile;
import main.common.entity.Unit;
import main.common.images.GameImageResource;
import main.common.util.MapSize;
import main.game.model.entity.DefaultProjectile;

/**
 * @author Andrew McGhie
 */
public class Laser extends BaseRangedAttack {

  @Override
  Projectile createProjectile(
      Unit unit, Unit enemyUnit
  ) {
    return new DefaultProjectile(
        unit.getCentre(),
        new MapSize(0.5, 0.5),
        enemyUnit,
        unit,
        GameImageResource.FIREBALL_PROJECTILE.getGameImage(),
        0.1
    );
  }

  @Override
  double getRange(Unit unit) {
    return 3;
  }

  @Override
  int getAttackSpeed(Unit unit) {
    return 1;
  }

  @Override
  double getDamage(Unit unit) {
    return 0.1;
  }

  @Override
  public double getWindupPortion(Unit unit) {
    return 0;
  }

  @Override
  AttackType getType(Unit unit) {
    return AttackType.MAGIC;
  }
}
