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

  private final Unit targetUnit;
  private final TickTimer expiryTimer;

  /**
   * Default constructor.
   * @param durationSeconds Number of seconds before this expires. Set to 0 for one-shot effects.
   */
  public Effect(Unit targetUnit, double durationSeconds) {
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
    expiryTimer.tick(timeSinceLastTick);
  }

  public boolean isApplyingTo(Unit unit) {
    return unit == this.targetUnit;
  }

  public boolean isExpired() {
    return expiryTimer.isFinished();
  }

  // Methods below consistently affect properties of the Unit.
  // These methods can do nothing by not overriding.
  // You can assume that this is not expired.

  public int getDamageAmount(int currentDamageAmount) {
    return currentDamageAmount;
  }

}
