package main.common.entity;

import java.io.Serializable;

/**
 * Is created by the {@link Usable} to actually do the work. JavaDoc below tells which method
 * are useful to override. Effects are single use - once they are expired, they cannot be used
 * again. Multiple effect objects can be created to apply on effects to multiple units.
 * @author chongdyla
 */
public interface Effect extends Serializable {

  /**
   * Optionally apply something to the unit (when the effect starts). Override and call super.
   */
  void start();

  /**
   * Maybe does something to it's {@link Unit}, or maybe doesn't do anything. If expired, does
   * nothing. If it becomes expired, optionally do some cleanup on the {@link Unit}. If you
   * override make sure to call super.
   */
  void tick(long timeSinceLastTick);

  boolean isTargetUnit(Unit unit);

  boolean isExpired();

  // These methods can do nothing by not overriding.
  // Methods below consistently affect properties of the Unit.

  /**
   * Optionally change the damage amount. The unit will call this method and pass in its damage
   * amount and use the returned value. Do nothing by not overriding (i.e. return the parameter).
   */
  int alterDamageAmount(int currentDamageAmount);
}
