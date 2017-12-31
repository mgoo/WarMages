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

/**
 * A basic arrow attack for any archer.
 * @author Andrew McGhie
 */
public class BasicArrow extends BaseRangedAttack {

  public BasicArrow() {
    super(CanEffect.ENEMIES);
  }

  @Override
  Projectile createProjectile(Unit unit, Targetable target) {
    Direction direction = Direction.between(unit.getCentre(), target.getLocation());
    return new DefaultProjectile(
        unit.getCentre(),
        new MapSize(0.7, 0.7),
        unit,
        target,
        this,
        Sheet.ARROW_PROJECTILE.getImagesForSequence(Sequence.ARROW, direction),
        Sheet.ARROW_PROJECTILE.getImagesForSequence(Sequence.ARROW, direction),
        new MapSize(0.7, 0.7),
        0.5
    );
  }

  @Override
  double getRange(Unit unit) {
    return 5;
  }

  @Override
  int getAttackSpeed(Unit unit) {
    return 20;
  }

  @Override
  double getDamage(Unit unit) {
    return 18;
  }

  @Override
  public double getWindupPortion(Unit unit) {
    return 0.7;
  }

  @Override
  public UnitSpriteSheet.Sequence getAttackSequence() {
    return UnitSpriteSheet.Sequence.SHOOT;
  }

  @Override
  AttackType getType(Unit unit) {
    return AttackType.MISSILE;
  }
}
