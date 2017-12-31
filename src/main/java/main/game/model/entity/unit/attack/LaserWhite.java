package main.game.model.entity.unit.attack;

import main.common.entity.Direction;
import main.common.entity.Projectile;
import main.common.entity.Unit;
import main.common.images.SpriteSheet.Sequence;
import main.common.images.SpriteSheet.Sheet;
import main.common.util.MapSize;
import main.game.model.entity.DefaultProjectile;
import main.game.model.entity.unit.state.Targetable;

/**
 * Laser with a different looking projectile.
 * @author Andrew McGhie
 */
public class LaserWhite extends Laser {

  @Override
  public Projectile createProjectile(
      Unit unit, Targetable target
  ) {
    Direction direction = Direction.between(unit.getCentre(), target.getLocation());
    return new DefaultProjectile(
        unit.getCentre(),
        new MapSize(0.5, 0.5),
        unit,
        target,
        this,
        Sheet.WHITEMISSILE_PROJECTILE.getImagesForSequence(Sequence.WHITEMISSILE_FLY, direction),
        Sheet.WHITEMISSILE_PROJECTILE.getImagesForSequence(Sequence.WHITEMISSILE_FLY, direction),
        new MapSize(0.5, 0.5),
        0.1
    );
  }
}
