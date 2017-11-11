package main.game.model.entity.unit.state;

import java.io.Serializable;
import main.common.entity.Unit;
import main.common.util.MapPoint;
import main.game.model.entity.unit.attack.Attack;

public class TargetEnemyUnit extends Target implements Serializable {

  private static final long serialVersionUID = 1L;

  private final Unit enemyUnit;
  private final Attack attack;

  public TargetEnemyUnit(Unit unit, Unit enemyUnit, Attack attack) {
    super(unit, null);
    this.enemyUnit = enemyUnit;
    this.attack = attack;
    this.setNextState(new Attacking(unit, this, attack));

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
        distanceTo(enemyUnit.getCentre()) > this.acceptableDistanceFromEnd();
  }

  @Override
  public double acceptableDistanceFromEnd() {
    return this.attack.getModifiedRange(this.unit) * 0.999 // avoid floating point inaccuracy
        + unit.getSize().width
        + enemyUnit.getSize().width;
  }

  Unit getEnemyUnit() {
    return enemyUnit;
  }
}