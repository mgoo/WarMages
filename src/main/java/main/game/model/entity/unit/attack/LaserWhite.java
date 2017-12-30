package main.game.model.entity.unit.attack;

import main.common.entity.Projectile;
import main.common.entity.Unit;
import main.common.images.SpriteSheet.Sequence;
import main.common.images.SpriteSheet.Sheet;
import main.common.util.MapSize;
import main.game.model.entity.DefaultProjectile;

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
        Sheet.WHITEMISSILE_PROJECTILE,
        Sequence.WHITEMISSILE_FLY,
        Sequence.WHITEMISSILE_IMPACT,
        0.1
    );
  }
}
