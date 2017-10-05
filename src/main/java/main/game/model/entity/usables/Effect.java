package main.game.model.entity.usables;

import java.io.Serializable;
import main.game.model.entity.Unit;
import main.util.TickTimer;

/**
 * Is created by the {@link Usable} to actually do the work. JavaDoc below tells which method
 * are useful to override.
 */
public abstract class Effect implements Serializable {

  private static final long serialVersionUID = 1L;

  public static final double INSTANT_EFFECT_DURATION = 0;

  private final Unit targetUnit;
  private final TickTimer expiryTimer;

  /**
   * Default constructor for a non
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
    expiryTimer.restart();
  }

  /**
   * Maybe does something to it's {@link Unit}, or maybe doesn't do anything. If expired, does
   * nothing. If it becomes expired, optionally do some cleanup on the {@link Unit}. If you
   * override make sure to call super.
   */
  public void tick(long timeSinceLastTick) {
    if (isExpired()) {
      throw new IllegalStateException("This is expired already.");
    }

    expiryTimer.tick(timeSinceLastTick);
  }

  public boolean isTargetUnit(Unit unit) {
    return unit == this.targetUnit;
  }

  public boolean isExpired() {
    return expiryTimer.isFinished();
  }

  // Methods below consistently affect properties of the Unit.
  // These methods can do nothing by not overriding.
  // You can assume that this is not expired.

  public int alterDamageAmount(int currentDamageAmount) {
    return currentDamageAmount;
  }

}
