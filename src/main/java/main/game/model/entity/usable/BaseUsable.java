package main.game.model.entity.usable;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import main.common.entity.Unit;
import main.common.entity.Usable;
import main.common.entity.usable.Effect;
import main.common.exceptions.CantApplyToUnitsException;
import main.common.exceptions.UsableStillInCoolDownException;
import main.common.World;

/**
 * All {@link Usable}s should extend {@link BaseUsable}.
 */
public abstract class BaseUsable implements Usable {

  private static final long serialVersionUID = 1L;

  @Override
  public final void use(World world, Collection<Unit> selectedUnits) {
    if (!isReadyToBeUsed()) {
      throw new UsableStillInCoolDownException();
    }

    Collection<Unit> unitsToApplyOn = selectUnitsToApplyOn(
        requireNonNull(world),
        requireNonNull(selectedUnits)
    );

    for (Unit unit : unitsToApplyOn) {
      Effect effect = createEffectForUnit(unit, world);
      unit.addEffect(effect);
    }

    startCoolDown();
  }

  /**
   * Pick what units to apply this ability to.
   *
   * @param selectedUnits The units that are currently selected by the user.
   * @throws CantApplyToUnitsException When there is a unit that we cannot apply this {@link Usable}
   *     to.
   */
  protected abstract Collection<Unit> selectUnitsToApplyOn(
      World world,
      Collection<Unit> selectedUnits
  );


  /**
   * Starts the cool-down period.
   */
  protected abstract void startCoolDown();

  /**
   * Creates a new effect. Does not need to check {@link Usable#isReadyToBeUsed()}.
   */
  protected abstract Effect createEffectForUnit(Unit unit, World world);

  public abstract boolean canApplyTo(Unit unit);

}
