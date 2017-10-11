package main.common;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.Collection;
import main.game.model.entity.exceptions.CantApplyToUnitsException;
import main.game.model.entity.exceptions.UsableStillInCoolDownException;
import main.game.model.entity.usable.Ability;
import main.game.model.entity.usable.Effect;
import main.game.model.entity.usable.Item;
import main.game.model.world.World;
import main.common.images.GameImage;

/**
 * An usable {@link Item} or {@link Ability} - these have some effect on the unit (e.g. instant
 * health increase or a damage increase for a certain amount of time).
 */
public interface Usable extends Serializable {

  /**
   * Creates an {@link Effect} for each unit through {@link Usable#_createEffectForUnit(Unit)} that
   * this {@link Usable} selects through {@link Usable#_selectUnitsToApplyOn(World, Collection)} and
   * applies the effects.
   *
   * @throws IllegalStateException When this is not ready to be used yet (e.g. cool-down).
   */
  default void use(World world, Collection<Unit> selectedUnits) {
    if (!isReadyToBeUsed()) {
      throw new UsableStillInCoolDownException();
    }

    Collection<Unit> unitsToApplyOn = _selectUnitsToApplyOn(
        requireNonNull(world),
        requireNonNull(selectedUnits)
    );

    for (Unit unit : unitsToApplyOn) {
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
   * a method called 'tick' in {@link Entity}.
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
   * Pick what units to apply this ability to.
   *
   * @param selectedUnits The units that are currently selected by the user.
   * @throws CantApplyToUnitsException When there is a unit that we cannot apply this {@link Usable}
   *     to.
   */
  Collection<Unit> _selectUnitsToApplyOn(World world, Collection<Unit> selectedUnits);

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
