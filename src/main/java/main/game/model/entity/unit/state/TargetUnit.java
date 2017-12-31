package main.game.model.entity.unit.state;

import main.game.model.entity.Team;
import main.game.model.entity.Unit;
import main.util.MapPoint;

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
