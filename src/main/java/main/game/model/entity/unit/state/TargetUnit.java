package main.game.model.entity.unit.state;

import main.common.entity.Team;
import main.common.entity.Unit;
import main.common.util.MapPoint;

public class TargetUnit extends Target {

  protected final Unit targetUnit;

  public TargetUnit(
      Unit unit, Unit targetUnit
  ) {
    super(unit);
    this.targetUnit = targetUnit;
  }

  @Override
  MapPoint getDestination() {
    return this.targetUnit.getCentre();
  }

  @Override
  boolean isStillValid() {
    return this.targetUnit.getHealth() > 0;
  }

  @Override
  public boolean hasArrived() {
    return unit.getCentre()
        .distanceTo(this.targetUnit.getCentre()) < this.acceptableDistanceFromEnd();
  }

  @Override
  public double acceptableDistanceFromEnd() {
    return unit.getSize().width
        + this.targetUnit.getSize().width;
  }

  public Unit getTargetUnit() {
    return this.targetUnit;
  }

  public boolean isUnitPlayers() {
    return this.targetUnit.getTeam() == Team.PLAYER;
  }
}
