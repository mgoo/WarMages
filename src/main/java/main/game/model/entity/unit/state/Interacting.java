package main.game.model.entity.unit.state;

import main.game.model.entity.Unit;
import main.game.model.world.World;
import main.images.Animation;

/**
 * Attacking state for Unit.
 *
 * @author paladogabr
 */
public class Interacting extends UnitState {

  private static final long serialVersionUID = 1L;

  protected final Target target;

  public Interacting(Unit unit, Animation animation, Target target) {
    super(animation, unit);
    this.target = target;
  }

  @Override
  public void tick(Long timeSinceLastTick, World world) {
    super.tick(timeSinceLastTick, world);
  }

  /**
   * Assumes that units look at what they are interacting with.
   */
  @Override
  public double getCurrentAngle() {
    return unit.getCentre().angleTo(target.getDestination());
  }
}
