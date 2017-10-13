package main.game.model.entity.unit.state;

import java.util.Comparator;
import main.common.entity.Unit;
import main.common.images.UnitSpriteSheet.Sequence;
import main.game.model.entity.unit.DefaultUnit;
import main.game.model.world.World;

/**
 * Idle state for Unit.
 *
 * @author paladogabr
 */
public class IdleUnitState extends UnitState {

  private static final long serialVersionUID = 1L;

  public IdleUnitState(DefaultUnit unit) {
    super(Sequence.IDLE, unit);
  }

  @Override
  public void tick(Long timeSinceLastTick, World world) {
    super.tick(timeSinceLastTick, world);

    double autoAttackDistance = unit.getUnitType().getAutoAttackDistance();
    Unit enemyOrNull = world.getAllUnits()
        .stream()
        .filter(worldUnit -> unit.getTeam().canAttack(worldUnit.getTeam()))
        .filter(enemyUnit -> distanceToUnit(enemyUnit) <= autoAttackDistance)
        .filter(enemyUnit -> enemyUnit.getHealth() > 0)
        .sorted(Comparator.comparingDouble(this::distanceToUnit))
        .findFirst()
        .orElse(null);
    if (enemyOrNull != null) {
      // Assume that walking state will switch to attacking state if unit is close enough.
      requestState(new WalkingUnitState(unit, enemyOrNull));
    }
  }

  @Override
  public UnitState updateState() {
    return (requestedNextState == null) ? this : requestedNextState;
  }

  private double distanceToUnit(Unit target) {
    return unit.getCentre().distanceTo(target.getCentre());
  }
}
