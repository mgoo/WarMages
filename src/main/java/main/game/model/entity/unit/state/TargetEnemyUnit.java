package main.game.model.entity.unit.state;

import java.io.Serializable;
import main.common.entity.Team;
import main.common.entity.Unit;
import main.common.util.MapPoint;
import main.game.model.entity.unit.attack.Attack;

public class TargetEnemyUnit extends TargetUnit implements Serializable {

  private static final long serialVersionUID = 1L;

  private final Attack attack;

  public TargetEnemyUnit(Unit unit, Unit enemyUnit, Attack attack) {
    super(unit, enemyUnit);
    this.attack = attack;
    this.setNextState(new Attacking(unit, this, attack));

    if (!isStillValid()) {
      throw new IllegalArgumentException();
    }
  }

  @Override
  boolean isStillValid() {
    return super.isStillValid()
        && unit.getTeam().canAttack(this.targetUnit.getTeam());
  }

  @Override
  public double acceptableDistanceFromEnd() {
    return this.attack.getModifiedRange(this.unit) * 0.999 // avoid floating point inaccuracy
        + unit.getSize().width
        + this.targetUnit.getSize().width;
  }
}