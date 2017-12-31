package main.game.model.entity.unit.state;

import main.game.model.entity.Direction;
import main.game.model.entity.unit.DefaultUnit;
import main.game.model.entity.unit.UnitAnimation;
import main.game.model.world.World;
import main.images.UnitSpriteSheet.Sequence;

/**
 * Used for representing the dying unitAnimation when a unit is dead.
 * @author chongdyla
 */
public class Dying extends UnitState {

  private static final long serialVersionUID = 1L;

  public Dying(Sequence sequence, DefaultUnit unit) {
    super(new UnitAnimation(unit, sequence, sequence.frames * 2), unit);
  }

  @Override
  public void tick(Long timeSinceLastTick, World world) {
    super.tick(timeSinceLastTick, world);

    if (unitAnimation.isFinished()) {
      world.onEnemyKilled(unit);
    }
  }

  @Override
  public Direction getCurrentDirection() {
    return Direction.DOWN;
  }

  @Override
  public UnitState updateState() {
    return this;
  }


}
