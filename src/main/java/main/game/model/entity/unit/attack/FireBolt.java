package main.game.model.entity.unit.attack;

import main.game.model.entity.DefaultProjectile;
import main.game.model.entity.Direction;
import main.game.model.entity.Projectile;
import main.game.model.entity.Unit;
import main.game.model.entity.unit.state.Targetable;
import main.images.SpriteSheet.Sequence;
import main.images.SpriteSheet.Sheet;
import main.images.UnitSpriteSheet;
import main.util.MapSize;

public class FireBolt extends BaseRangedAttack {

  public FireBolt() {
    super(CanEffect.EVERYONE);
  }

  @Override
  public Projectile createProjectile(
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
        Sheet.EXPLOSIONS.getImagesForSequence(Sequence.EXPLOSION_1, direction),
        new MapSize(2, 2),
        0.6
    );
  }

  @Override
  double getRange(Unit unit) {
    return 4;
  }

  @Override
  int getAttackSpeed(Unit unit) {
    return 10;
  }

  @Override
  double getDamage(Unit unit) {
    return 200;
  }

  @Override
  public double getWindupPortion(Unit unit) {
    return 0.66;
  }

  @Override
  public UnitSpriteSheet.Sequence getAttackSequence() {
    return UnitSpriteSheet.Sequence.SLASH;
  }

  @Override
  AttackType getType(Unit unit) {
    return AttackType.MAGIC;
  }
}
