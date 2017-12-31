package main.game.model.entity.unit.state;

import java.io.Serializable;
import main.common.entity.Unit;
import main.common.util.MapPoint;
import main.game.model.entity.unit.attack.Attack;

public class TargetMapPoint extends Target implements Serializable {

  private static final long serialVersionUID = 1L;

  private final double leewayForPath;
  private final MapPoint target;

  /**
   * Default distance is 0.5
   */
  public TargetMapPoint(Unit unit, MapPoint target) {
    super(unit);
    this.target = target;
    this.leewayForPath = 0.5;
  }

  public TargetMapPoint(Unit unit, MapPoint target, double distance) {
    super(unit);
    this.target = target;
    this.leewayForPath = distance;
  }

  @Override
  MapPoint getDestination() {
    return target;
  }

  @Override
  boolean isStillValid() {
    return true;
  }

  @Override
  public boolean hasArrived() {
    return unit.getCentre().distanceTo(target) < this.acceptableDistanceFromEnd();
  }

  @Override
  public double acceptableDistanceFromEnd() {
    return leewayForPath;
  }
}