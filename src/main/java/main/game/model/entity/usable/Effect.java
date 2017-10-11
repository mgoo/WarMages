package main.game.model.entity.usable;

import java.io.Serializable;
import main.common.Usable;
import main.game.model.entity.Unit;
import main.common.util.TickTimer;

/**
 * Is created by the {@link Usable} to actually do the work. JavaDoc below tells which method
 * are useful to override. Effects are single use - once they are expired, they cannot be used
 * again. Multiple effect objects can be created to apply on effects to multiple units.
 */
public abstract class Effect implements Serializable {

  private static final long serialVersionUID = 1L;

  public static final double INSTANT_EFFECT_DURATION = 0;

  private final Unit targetUnit;
  private final TickTimer expiryTimer;
  private boolean hasStarted;

  /**
   * Default constructor.
   * @param durationSeconds Number of seconds before this expires. Set to
   *     {@link Effect#INSTANT_EFFECT_DURATION} for one-shot effects.
   */
  public Effect(Unit targetUnit, double durationSeconds) {
    if (durationSeconds < 0) {
      throw new IllegalArgumentException();
    }

    this.targetUnit = targetUnit;
    this.expiryTimer = TickTimer.withPeriodInSeconds(durationSeconds);
  }

  /**
   * Optionally apply something to the unit (when the effect starts). Override and call super.
   */
  public void start() {
    if (hasStarted) {
      throw new IllegalStateException();
    }

    hasStarted = true;
    expiryTimer.restart();
  }

  /**
   * Maybe does something to it's {@link Unit}, or maybe doesn't do anything. If expired, does
   * nothing. If it becomes expired, optionally do some cleanup on the {@link Unit}. If you
   * override make sure to call super.
   */
  public void tick(long timeSinceLastTick) {
    if (!isActive()) {
      throw new IllegalStateException();
    }

    expiryTimer.tick(timeSinceLastTick);
  }

  public boolean isTargetUnit(Unit unit) {
    return unit == this.targetUnit;
  }

  public boolean isExpired() {
    return hasStarted && expiryTimer.isFinished();
  }

  private boolean isActive() {
    return !isExpired() && hasStarted;
  }

  // These methods can do nothing by not overriding.
  // Methods below consistently affect properties of the Unit.

  /**
   * Optionally change the damage amount. The unit will call this method and pass in its damage
   * amount and use the returned value. Do nothing by not overriding (i.e. return the parameter).
   */
  public int alterDamageAmount(int currentDamageAmount) {
    if (!isActive()) {
      throw new IllegalStateException();
    }

    return currentDamageAmount;
  }
}
