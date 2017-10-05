package main.game.model.entity;

import java.io.Serializable;
import java.util.Collection;
import main.images.GameImage;

/**
 * An usable {@link Item} or {@link Ability} - these have some effect on the unit (e.g. instant
 * health increase or a damage increase for a certain amount of time).
 */
public interface Usable extends Serializable {

  double COOL_DOWN_JUST_STARTED = 0;
  double READY = 1;

  /**
   * Creates an {@link Effect} for each unit and applies it to each {@link Unit}.
   *
   * @throws IllegalStateException When this is not ready to be used yet (e.g. cool-down).
   */
  default void useOnUnits(Collection<Unit> units) {
    if (!isReadyToBeUsed()) {
      throw new IllegalStateException("Not ready");
    }

    for (Unit unit : units) {
      Effect effect = _createEffectForUnit(unit);
      unit.addEffect(effect);
    }

    _startCoolDown();
  }

  /**
   * False if currently in a cool-down state.
   */
  default boolean isReadyToBeUsed() {
    return getCoolDownProgress() == READY;
  }

  /**
   * Should update any cool-down timers.
   */
  void tick(long timeSinceLastTick);

  /**
   * Returns the GameImage of this Ability.
   *
   * @return GameImage of the Ability.
   */
  GameImage getIconImage();

  /**
   * Returns a string description of the Ability.
   *
   * @return String describing the Ability
   */
  String getDescription();

  /**
   * 0 if just used, 1 if ready to use.
   */
  double getCoolDownProgress();

  /**
   * PROTECTED - DON"T USE FROM OUTSIDE THIS CLASS! Sets a cool-down ticks variable to
   * {@link Usable#COOL_DOWN_JUST_STARTED}.
   */
  void _startCoolDown();

  /**
   * PROTECTED - DON"T USE FROM OUTSIDE THIS CLASS! Creates a new effect. Does not need to check
   * {@link Usable#isReadyToBeUsed()}.
   */
  Effect _createEffectForUnit(Unit unit);

  /**
   * Is created by the {@link Usable} to actually do the work.
   */
  abstract class Effect implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Unit targetUnit;

    protected Effect(Unit targetUnit) {
      this.targetUnit = targetUnit;
    }

    /**
     * Optionally apply something to the unit (when the effect starts).
     */
    public void start() {
    }

    /**
     * Maybe does something to it's {@link Unit}, or maybe doesn't do anything. If expired, does
     * nothing. If it becomes expired, optionally do some cleanup on the {@link Unit}.
     */
    public void tick(long timeSinceLastTick) {
    }

    /**
     * Returns true if this activity has been active for whatever it's activation period is.
     * One-shot {@link Effect}s like what is used in {@link HealingAbility} should immediately
     * return true.
     */
    public abstract boolean isExpired();

    public boolean isApplyingTo(Unit unit) {
      return unit == this.targetUnit;
    }

    // Methods that consistently affect properties of the Unit if not expired.
    // These methods can do nothing by not overriding.

    public int getDamageAmount(int currentDamageAmount) {
      return currentDamageAmount;
    }

  }
}
