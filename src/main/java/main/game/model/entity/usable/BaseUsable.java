package main.game.model.entity.usable;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import main.common.Effect;
import main.common.Usable;
import main.game.model.entity.Unit;
import main.common.exceptions.CantApplyToUnitsException;
import main.common.exceptions.UsableStillInCoolDownException;
import main.game.model.world.World;

/**
 * All {@link Usable}s should extend {@link BaseUsable}.
 */
public interface BaseUsable extends Usable {

  @Override
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
