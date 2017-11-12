package main.game.model.entity.unit.state;

import main.common.World;
import main.common.entity.Unit;
import main.game.model.entity.unit.UnitAnimation;
import main.game.model.entity.unit.attack.Attack;

/**
 * The State for when a unit is attacking another one.
 * @author Andrew McGhie
 */
public class Attacking extends Interacting {

  private final int applicationTick;
  private final Attack attack;
  private final TargetEnemyUnit targetEnemyUnit;

  private int currentTick = 0;

  public Attacking(
      Unit unit,
      TargetEnemyUnit target,
      Attack attack
  ) {
    super(unit,
        new UnitAnimation(unit,
            unit.getUnitType().getAttackSequence(),
            attack.getModifiedAttackSpeed(unit)),
        target);
    this.targetEnemyUnit = target;
    this.applicationTick =
        (int)(attack.getModifiedAttackSpeed(unit) * attack.getWindupPortion(unit));
    this.attack = attack;

    if (!target.isStillValid()) {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public void tick(Long timeSinceLastTick, World world) {
    super.tick(timeSinceLastTick, world);

    if (!target.hasArrived()) {
      requestedNextState = new Moving(
          unit,
          new TargetMapPoint(unit, target.getDestination()),
          new Attacking(
              this.unit,
              ((TargetEnemyUnit) this.target),
              this.attack
          )
      );
      return;
    }

    if (!target.isStillValid()) {
      requestedNextState = new Idle(this.unit);
      return;
    }

    if (this.currentTick == this.applicationTick) {
      this.attack.execute(unit, this.targetEnemyUnit.getEnemyUnit(), world);
    }
    currentTick++;
  }

  @Override
  public UnitState updateState() {
    if (requestedNextState != null) {
      return requestedNextState;
    }
    if (!target.isStillValid()) {
      return new Idle(unit);
    }

    if (this.unitAnimation.isFinished() && target.isStillValid()) {
      return new Attacking(this.unit, this.targetEnemyUnit, this.attack);
    }

    return this;
  }
}
