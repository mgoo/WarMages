package main.game.model.entity.unit.attack;

import main.common.entity.Projectile;
import main.common.entity.Unit;
import main.common.images.SpriteSheet.Sequence;
import main.common.images.SpriteSheet.Sheet;
import main.common.util.MapSize;
import main.game.model.entity.DefaultProjectile;

public class IceBall extends FireBall {
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
        Sheet.ICEMISSSILE_PROJECTILE,
        Sequence.ICE_FLY,
        Sequence.ICE_IMPACT,
        0.4
    );
  }
}