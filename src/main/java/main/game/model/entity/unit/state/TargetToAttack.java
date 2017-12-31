package main.game.model.entity.unit.state;

import java.io.Serializable;
import main.common.entity.Team;
import main.common.entity.Unit;
import main.common.util.MapPoint;
import main.game.model.entity.unit.attack.Attack;

public class TargetToAttack extends Target implements Serializable {

  private static final long serialVersionUID = 1L;

  private final Targetable target;
  private final Attack attack;

  public TargetToAttack(Unit unit, Targetable target, Attack attack) {
    this(unit, target, attack, false);
  }

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