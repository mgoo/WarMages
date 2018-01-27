package main.game.model.entity.unit.state;

import main.game.model.entity.Unit;
import main.game.model.entity.unit.UnitAnimation;
import main.game.model.entity.unit.attack.Attack;
import main.game.model.world.World;

/**
 * The State for when a unit is attacking another one.
 * @author Andrew McGhie
 */
public class Attacking extends Interacting {

  private final int applicationTick;
  private final Attack attack;
  private final TargetToAttack targetToAttack;
  private final boolean singleAttack;

  private int currentTick = 0;

  public Attacking(Unit unit, TargetToAttack target, Attack attack) {
    this(unit, target, attack, false);
  }

  public Attacking(Unit unit, TargetToAttack target, Attack attack, boolean singleAttack) {
    super(unit,
        new UnitAnimation(unit,
            attack.getAttackSequence(),
            attack.getModifiedAttackSpeed(unit)),
        target);
    this.targetToAttack = target;
    this.singleAttack = singleAttack;
    this.applicationTick =
        (int)(attack.getModifiedAttackSpeed(unit) * attack.getWindupPortion());
    this.attack = attack;

    if (!target.isStillValid()) {
      this.setState(new Idle(unit));
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
              ((TargetToAttack) this.target),
              this.attack,
              singleAttack
          )
      );
      return;
    }

    if (!target.isStillValid()) {
      requestedNextState = new Idle(this.unit);
      return;
    }

    if (this.currentTick == this.applicationTick) {
      this.attack.execute(unit, this.targetToAttack.getTarget(), world);
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
      if (!this.singleAttack) {
        return new Attacking(this.unit, this.targetToAttack, this.attack, singleAttack);
      } else {
        return new Idle(unit);
      }
    }

    return this;
  }
}
