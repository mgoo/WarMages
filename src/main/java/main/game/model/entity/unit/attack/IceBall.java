package main.game.model.entity.unit.attack;

import main.common.entity.Direction;
import main.common.entity.Projectile;
import main.common.entity.Unit;
import main.common.images.SpriteSheet.Sequence;
import main.common.images.SpriteSheet.Sheet;
import main.common.util.MapSize;
import main.game.model.entity.DefaultProjectile;
import main.game.model.entity.unit.state.Targetable;

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
