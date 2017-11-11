package main.game.model.entity.unit.attack;

import main.common.entity.Projectile;
import main.common.entity.Unit;
import main.common.images.GameImageResource;
import main.common.util.MapSize;
import main.game.model.entity.DefaultProjectile;

/**
 * @author Andrew McGhie
 */
public class BasicArrow extends BaseRangedAttack {

  @Override
  Projectile createProjectile(
      Unit unit, Unit enemyUnit
  ) {
    return new DefaultProjectile(
        unit.getCentre(),
        new MapSize(0.4, 0.4),
        enemyUnit,
        unit,
        GameImageResource.ARROW_PROJECTILE.getGameImage(),
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
    return 20;
  }

  @Override
  public double getWindupPortion(Unit unit) {
    return 0.8;
  }

  @Override
  AttackType getType(Unit unit) {
    return AttackType.MISSILE;
  }
}
