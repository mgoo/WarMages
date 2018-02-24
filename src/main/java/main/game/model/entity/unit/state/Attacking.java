package main.game.model.entity.unit.state;

import main.game.model.entity.Unit;
import main.game.model.entity.unit.attack.Attack;
import main.game.model.world.World;
import main.images.Animation;

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
    super(
        unit,
        new Animation(
            unit.getSpriteSheet(),
            attack.getAnimation().getId(),
            attack.getModifiedAttackSpeed(unit)
        ),
        target
    );
    this.targetToAttack = target;
    this.singleAttack = singleAttack;
    this.applicationTick =
        (int) (attack.getModifiedAttackSpeed(unit) * attack.getWindupPortion());
    this.attack = attack;

    if (!target.isStillValid()) {
      this.unit.setState(new Idle(unit));
    }
  }

  @Override
  public void tick(Long timeSinceLastTick, World world) {
    super.tick(timeSinceLastTick, world);

    if (!target.isStillValid()) {
      this.unit.setState(new Idle(this.unit));
      return;
    }
    // Target is valid

    if (!target.hasArrived()) {
      this.unit.setTarget(
          new TargetToAttack(this.unit, targetToAttack.getTarget(), this.attack, this.singleAttack)
      );
      return;
    }

    // If need to execute attack
    if (this.currentTick == this.applicationTick) {
      this.attack.execute(unit, this.targetToAttack.getTarget(), world);
    }

    // Attack is finished
    if (this.unitAnimation.isFinished() && target.isStillValid()) {
      if (!this.singleAttack) {
        this.unit.setTarget(
            new TargetToAttack(this.unit, targetToAttack.getTarget(), this.attack, false)
        );
      } else {
        this.unit.setState(new Idle(unit));
      }
      return;
    }

    currentTick++;
  }
}
