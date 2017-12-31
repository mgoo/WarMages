package main.game.model.entity.unit.attack;

import main.game.model.entity.DefaultProjectile;
import main.game.model.entity.Direction;
import main.game.model.entity.Projectile;
import main.game.model.entity.Unit;
import main.game.model.entity.unit.state.Targetable;
import main.images.SpriteSheet.Sequence;
import main.images.SpriteSheet.Sheet;
import main.util.MapSize;

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
