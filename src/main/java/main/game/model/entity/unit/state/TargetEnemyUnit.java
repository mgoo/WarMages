package main.game.model.entity.unit.state;

import java.io.Serializable;
import main.common.entity.Unit;
import main.common.util.MapPoint;

public class TargetEnemyUnit extends Target implements Serializable {

  private static final long serialVersionUID = 1L;

  private final Unit enemyUnit;

  public TargetEnemyUnit(Unit unit, Unit enemyUnit) {
    super(unit, null);
    this.enemyUnit = enemyUnit;
    this.nextState = new Attacking(unit, this, 1.0);

    if (!isStillValid()) {
      throw new IllegalArgumentException();
    }
  }

  @Override
  MapPoint getDestination() {
    return enemyUnit.getCentre();
  }

  @Override
  boolean isStillValid() {
    return enemyUnit.getHealth() > 0
        && unit.getTeam().canAttack(enemyUnit.getTeam());
  }

  @Override
  public boolean hasArrived() {
    return unit.getCentre().
        distanceTo(enemyUnit.getCentre()) > this.getDestinationLeeway();
  }

  private double getDestinationLeeway() {
    return unit.getUnitType().getAttackDistance() * 0.999 // avoid floating point inaccuracy
        + unit.getSize().width
        + enemyUnit.getSize().width;
  }

  Unit getEnemyUnit() {
    return enemyUnit;
  }
}