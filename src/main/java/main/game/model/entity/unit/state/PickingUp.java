package main.game.model.entity.unit.state;

import main.common.World;
import main.common.entity.HeroUnit;
import main.common.entity.usable.Item;
import main.common.images.UnitSpriteSheet.Sequence;
import main.game.model.entity.unit.UnitAnimation;

/**
 * State for when the unit is picking an item up off the ground.
 * @author Andrew McGhie
 */
public class PickingUp extends Interacting {

  private final Item item;
  private final HeroUnit heroUnit;

  public PickingUp(
      HeroUnit unit,
      TargetItem itemTarget
  ) {
    super(unit,
        new UnitAnimation(unit, Sequence.PICKUP, Sequence.PICKUP.frames * 3),
        itemTarget);
    this.heroUnit = unit;
    this.item = itemTarget.getItem();
  }

  @Override
  public void tick(Long timeSinceLastTick, World world) {
    super.tick(timeSinceLastTick, world);
    if (this.target.hasArrived() && this.unitAnimation.isFinished()) {
      this.heroUnit.pickUp(this.item);
      world.removeItem(this.item);
    }
  }

  @Override
  public UnitState updateState() {
    if (requestedNextState != null) {
      return requestedNextState;
    }

    if (this.unitAnimation.isFinished()) {
      return new Idle(unit);
    }

    return this;
  }
}
