package main.game.model.entity.usable;

import java.io.Serializable;
import java.util.Collection;
import main.game.model.entity.Unit;
import main.game.model.entity.exceptions.CantApplyToUnitsException;
import main.game.model.entity.exceptions.UsableStillInCoolDownException;
import main.images.GameImage;

/**
 * An usable {@link Item} or {@link Ability} - these have some effect on the unit (e.g. instant
 * health increase or a damage increase for a certain amount of time).
 */
public interface Usable extends Serializable {

  /**
   * Creates an {@link Effect} for each unit and applies it to each {@link Unit}.
   *
   * @throws IllegalStateException When this is not ready to be used yet (e.g. cool-down).
   */
  default void useOnUnits(Collection<Unit> units) {
    if (!isReadyToBeUsed()) {
      throw new UsableStillInCoolDownException();
    }

    if (units.isEmpty()) {
      throw new IllegalArgumentException();
    }

    _requireValidUnits(units);

    for (Unit unit : units) {
      Effect effect = _createEffectForUnit(unit);
      unit.addEffect(effect);
    }

    _startCoolDown();
  }

  /**
   * False if currently in a cool-down state.
   */
  boolean isReadyToBeUsed();

  /**
   * Should update any cool-down timers. This is not called 'tick' because there is already
   * a method called 'tick' in {@link main.game.model.entity.Entity}.
   */
  void usableTick(long timeSinceLastTick);

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
   * PROTECTED - DON"T CALL FROM OUTSIDE THIS CLASS!
   * Check that it is allowed to apply this {@link Usable} to all the {@link Unit}s.
   *
   * @throws CantApplyToUnitsException When there is a unit that we cannot apply this {@link Usable}
   *     to.
   */
  default void _requireValidUnits(Collection<Unit> units) {
    // All units are valid by default
  }

  /**
   * PROTECTED - DON"T CALL FROM OUTSIDE THIS CLASS! Starts the cool-down period.
   */
  void _startCoolDown();

  /**
   * PROTECTED - DON"T CALL FROM OUTSIDE THIS CLASS! Creates a new effect. Does not need to check
   * {@link Usable#isReadyToBeUsed()}.
   */
  Effect _createEffectForUnit(Unit unit);

}
