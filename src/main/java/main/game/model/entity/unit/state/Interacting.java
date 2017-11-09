package main.game.model.entity.unit.state;

import main.common.entity.Direction;
import main.common.entity.Unit;
import main.game.model.entity.unit.DefaultUnit;
import main.game.model.entity.unit.UnitAnimation;
import main.common.World;

/**
 * Attacking state for Unit.
 *
 * @author paladogabr
 */
public class Interacting extends UnitState {

  private static final long serialVersionUID = 1L;

  protected final Target target;

  public Interacting(Unit unit,
                     UnitAnimation unitAnimation,
                     Target target) {
    super(unitAnimation, unit);
    this.target = target;
  }

  @Override
  public void tick(Long timeSinceLastTick, World world) {
    super.tick(timeSinceLastTick, world);

  }

  @Override
  public UnitState updateState() {
    if (requestedNextState != null) {
      return requestedNextState;
    }

    return this;
  }

  /**
   * Assumes that units look at what they are interacting with
   * @return
   */
  @Override
  public Direction getCurrentDirection() {
    return Direction.between(unit.getCentre(), target.getDestination());
  }
}
