package main.game.model.entity.unit.state;

import java.io.Serializable;
import main.common.World;
import main.common.entity.Unit;
import main.common.util.MapPoint;
import main.game.model.entity.unit.DefaultUnit;

/**
 * Hold information about what a unit is trying to do.
 *
 * @author Andrew McGhie
 */
public abstract class Target implements Serializable {
  private static final long serialVersionUID = 1L;

  protected final Unit unit;
  protected UnitState nextState;
  private final Moving moveToDestination;

  /**
   *
   * @param nextState The state to switch to unit to when it arrives at the target
   */
  protected Target(Unit unit, UnitState nextState) {
    this.unit = unit;
    this.nextState = nextState;
    this.moveToDestination = new Moving(unit, this, nextState);
  }

  protected Target(Unit unit) {
    this.unit = unit;
    this.nextState = new Idle(unit);
    this.moveToDestination = new Moving(unit, this, nextState);
  }

  /**
   * Can Change.
   */
  abstract MapPoint getDestination();

  abstract boolean isStillValid();

  public abstract boolean hasArrived();

  public UnitState getState() {
    if (this.hasArrived()) {
      return this.nextState;
    } else {
      return this.moveToDestination;
    }
  }
}
