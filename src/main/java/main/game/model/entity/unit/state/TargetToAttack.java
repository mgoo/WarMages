package main.game.model.entity.unit.state;

import java.io.Serializable;
import main.game.model.entity.Unit;
import main.game.model.entity.unit.attack.Attack;
import main.util.MapPoint;

/**
 * A target for a unit that is for when they are trying to attack a targetable.
 * @author Andrew McGhie
 */
public class TargetToAttack extends Target implements Serializable {

  private static final long serialVersionUID = 1L;

  private final Targetable target;
  private final Attack attack;

  /**
   * Creates a TargetToAttack that will continue after the first attack.
   * @param unit unit that the target is for
   * @param target target for that unit
   * @param attack the attack to do to that target
   */
  public TargetToAttack(Unit unit, Targetable target, Attack attack) {
    this(unit, target, attack, false);
  }

  /**
   * Creates a TargetToAttack that the unit will go Idle after finishing the animation.
   */
  public TargetToAttack(Unit unit, Targetable target, Attack attack, boolean singleUse) {
    super(unit);
    this.target = target;
    this.attack = attack;
    this.setNextState(new Attacking(unit, this, attack, singleUse));

    if (!isStillValid()) {
      throw new IllegalArgumentException();
    }
  }

  public Targetable getTarget() {
    return this.target;
  }

  @Override
  boolean isStillValid() {
    return target.isValidTargetFor(this.unit);
  }

  @Override
  MapPoint getDestination() {
    return this.target.getLocation();
  }

  @Override
  public boolean hasArrived() {
    return unit.getCentre()
        .distanceTo(this.getDestination()) < this.acceptableDistanceFromEnd();
  }

  @Override
  public double acceptableDistanceFromEnd() {
    return this.attack.getModifiedRange(this.unit) * 0.999 // avoid floating point inaccuracy
        + unit.getSize().width;
  }
}