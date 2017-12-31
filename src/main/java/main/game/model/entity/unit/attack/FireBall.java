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
 * Fireball attack for mages.
 * Lots of damage but slow to cast.
 * @author Andrew McGhie
 */
public class FireBall extends BaseRangedAttack {

  public FireBall() {
    super(CanEffect.ENEMIES);
  }

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
        Sheet.FIREBALL_PROJECTILE.getImagesForSequence(Sequence.FIREBALL_FLY, direction),
        Sheet.FIREBALL_PROJECTILE.getImagesForSequence(Sequence.FIREBALL_IMPACT, direction),
        new MapSize(1, 1),
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
