package main.game.model.entity.unit.state;

import main.common.images.UnitSpriteSheet.Sequence;
import main.game.model.entity.unit.DefaultUnit;
import main.game.model.world.World;

public class DyingState extends UnitState {

  private static final long serialVersionUID = 1L;

  public DyingState(Sequence sequence, DefaultUnit unit) {
    super(sequence, unit);
  }

  @Override
  public void tick(Long timeSinceLastTick, World world) {
    super.tick(timeSinceLastTick, world);

    if (imagesComponent.isLastTick()) {
      world.onEnemyKilled(unit);
    }
  }

  @Override
  public UnitState updateState() {
    return this;
  }
}
