package main.game.model.entity.unit.attack;

import main.common.entity.Projectile;
import main.common.entity.Unit;
import main.common.images.GameImageResource;
import main.common.util.MapSize;
import main.game.model.entity.DefaultProjectile;

/**
 * Fireball attack for mages.
 * Lots of damage but slow to cast.
 * @author Andrew McGhie
 */
public class FireBall extends BaseRangedAttack {

  @Override
  Projectile createProjectile(
      Unit unit, Unit enemyUnit
  ) {
    return new DefaultProjectile(
        unit.getCentre(),
        new MapSize(0.5, 0.5),
        enemyUnit,
        unit,
        this,
        GameImageResource.FIREBALL_PROJECTILE.getGameImage(),
        0.2
    );
  }

  @Override
  double getRange(Unit unit) {
    return 4;
  }

  @Override
  int getAttackSpeed(Unit unit) {
    return 30;
  }

  @Override
  double getDamage(Unit unit) {
    return 60;
  }

  @Override
  public double getWindupPortion(Unit unit) {
    return 0.85;
  }

  @Override
  AttackType getType(Unit unit) {
    return AttackType.MAGIC;
  }
}
