package main.game.model.entity.unit.attack;

import main.common.entity.Projectile;
import main.common.entity.Unit;
import main.common.images.GameImageResource;
import main.common.images.ProjectileSpriteSheet.Sequence;
import main.common.util.MapSize;
import main.game.model.entity.DefaultProjectile;
import main.images.DefaultProjectileSpriteSheet;

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
        new MapSize(0.8, 0.8),
        enemyUnit,
        unit,
        this,
        new DefaultProjectileSpriteSheet(GameImageResource.FIREBALL_PROJECTILE),
        Sequence.FIREBALL_FLY,
        Sequence.FIREBALL_IMPACT,
        0.4
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
