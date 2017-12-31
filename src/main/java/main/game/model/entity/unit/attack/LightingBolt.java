package main.game.model.entity.unit.attack;

import java.util.Optional;
import main.game.model.entity.StaticEntity;
import main.game.model.entity.Unit;
import main.game.model.entity.unit.state.Targetable;
import main.game.model.world.World;
import main.images.SpriteSheet.Sequence;
import main.images.SpriteSheet.Sheet;
import main.images.UnitSpriteSheet;
import main.util.MapSize;

public class LightingBolt extends BaseSpellAttack {


  public LightingBolt() {
    super(CanEffect.ENEMIES);
  }

  @Override
  public void execute(
      Unit unit, Targetable target, World world
  ) {
    Optional<Unit> targetUnit = target.getEffectedUnits(world)
        .stream()
        .findFirst();
    targetUnit.ifPresent(u -> u.takeDamage(this.getModifiedDamage(unit), world, unit));
    world.addStaticEntity(
        new StaticEntity(
            target.getLocation().translate(-1.5, -1.5),
            new MapSize(2, 2),
            Sheet.LIGHTING.getImagesForSequence(Sequence.LIGHTING_1),
            false,
            0.5
        )
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
    return 400;
  }

  @Override
  public double getWindupPortion(Unit unit) {
    return 0.85;
  }

  @Override
  public UnitSpriteSheet.Sequence getAttackSequence() {
    return UnitSpriteSheet.Sequence.SPELL_CAST;
  }

  @Override
  AttackType getType(Unit unit) {
    return AttackType.MAGIC;
  }
}
