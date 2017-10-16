package main.game.model.entity.unit.state;

import java.util.Comparator;
import main.common.World;
import main.common.entity.Unit;
import main.common.images.UnitSpriteSheet.Sequence;
import main.game.model.entity.unit.DefaultUnit;
import main.game.model.entity.unit.state.WalkingUnitState.EnemyUnitTarget;

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
      requestAttackUnit(enemyOrNull);
    }
  }

  @Override
  public UnitState updateState() {
    return (requestedNextState == null) ? this : requestedNextState;
  }

  @Override
  public void onTakeDamage(double amount, World world, Unit attacker) {
    if (unit.getHealth() == 0 || attacker.getHealth() == 0) {
      return;
    }

    requestAttackUnit(attacker);
  }

  private void requestAttackUnit(Unit enemy) {
    requestState(new WalkingUnitState(unit, new EnemyUnitTarget(unit, enemy)));
  }

  private double distanceToUnit(Unit target) {
    return unit.getCentre().distanceTo(target.getCentre());
  }
}
