package main.game.model.entity.unit.attack;

import main.common.entity.Projectile;
import main.common.entity.Unit;
import main.common.images.GameImageResource;
import main.common.images.ProjectileSpriteSheet.Sequence;
import main.common.util.MapSize;
import main.game.model.entity.DefaultProjectile;
import main.images.DefaultProjectileSpriteSheet;

/**
 * Laser with a different looking projectile.
 * @author Andrew McGhie
 */
public class LaserWhite extends Laser {

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
        new DefaultProjectileSpriteSheet(GameImageResource.WHITE_PROJECTILE),
        Sequence.WHITEMISSILE_FLY,
        Sequence.WHITEMISSILE_IMPACT,
        0.1
    );
  }
}
