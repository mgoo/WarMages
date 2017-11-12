package main.game.model.entity.unit.attack;

import main.common.entity.Projectile;
import main.common.entity.Unit;
import main.common.images.GameImageResource;
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
        GameImageResource.WHITE_PROJECTILE.getGameImage(),
        0.1
    );
  }
}
