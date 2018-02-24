package main.game.model.entity.unit.state;

import main.game.model.entity.HeroUnit;
import main.game.model.entity.usable.Item;
import main.game.model.world.World;
import main.images.Animation;

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
    super(
        unit,
        new Animation(unit.getSpriteSheet(), "animation:pickup", 10),
        itemTarget
    );
    this.heroUnit = unit;
    this.item = itemTarget.getItem();
  }

  @Override
  public void tick(Long timeSinceLastTick, World world) {
    super.tick(timeSinceLastTick, world);
    
    if (this.target.hasArrived() && this.unitAnimation.isFinished()) {
      this.heroUnit.pickUp(this.item);
      world.removeItem(this.item);
      this.heroUnit.setState(new Idle(this.heroUnit));
    }
    if (!this.target.hasArrived()) {
      this.heroUnit.setTarget(new TargetItem(heroUnit, item));
    }
  }
}
