package main.game.model.entity.unit.state;

import java.io.Serializable;
import main.common.entity.Unit;
import main.common.util.MapPoint;
import main.game.model.entity.unit.DefaultUnit;

public class TargetMapPoint extends Target implements Serializable {

  private static final double LEEWAY_FOR_PATH = 0.5;
  private static final long serialVersionUID = 1L;

  private final MapPoint target;

  public TargetMapPoint(Unit unit, MapPoint target) {
    super(unit);
    this.target = target;
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
    return unit.getCentre().
        distanceTo(target) > LEEWAY_FOR_PATH;
  }
}