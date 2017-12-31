package main.game.model.entity.unit.attack;

import main.game.model.entity.DefaultProjectile;
import main.game.model.entity.Direction;
import main.game.model.entity.Projectile;
import main.game.model.entity.Unit;
import main.game.model.entity.unit.state.Targetable;
import main.images.SpriteSheet.Sequence;
import main.images.SpriteSheet.Sheet;
import main.util.MapSize;

public class IceBall extends FireBall {
  @Override
  Projectile createProjectile(
      Unit unit, Targetable target
  ) {
    Direction direction = Direction.between(unit.getCentre(), target.getLocation());
    return new DefaultProjectile(
        unit.getCentre(),
        new MapSize(0.8, 0.8),
        unit,
        target,
        this,
        Sheet.ICEMISSSILE_PROJECTILE.getImagesForSequence(Sequence.ICE_FLY, direction),
        Sheet.ICEMISSSILE_PROJECTILE.getImagesForSequence(Sequence.ICE_IMPACT, direction),
        new MapSize(1, 1),
        0.4
    );
  }
}
