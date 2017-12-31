package main.game.model.entity.usable;

import main.exceptions.UsableStillInCoolDownException;
import main.game.model.entity.Unit;
import main.game.model.entity.unit.state.Targetable;
import main.game.model.world.World;
import main.util.MapPoint;

/**
 * All {@link Usable}s should extend {@link BaseUsable}.
 */
public abstract class BaseUsable implements Usable {

  private static final long serialVersionUID = 1L;

  protected Unit owner;

  public void setOwner(Unit unit) {
    this.owner = unit;
  }

  @Override
  public void use(World world, Unit unit) {
    if (!isReadyToBeUsed()) {
      throw new UsableStillInCoolDownException();
    }

    this.execute(world, unit);

    startCoolDown();
  }

  @Override
  public void use(World world, MapPoint mapPoint) {
    throw new RuntimeException("This usable cannot be used on a MapPoint");
  }


  protected abstract void execute(World world, Targetable target);

  /**
   * Starts the cool-down period.
   */
  protected abstract void startCoolDown();
}
