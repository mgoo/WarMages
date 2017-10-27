package main.game.model.entity.unit.state;

import java.util.List;
import java.util.stream.Collectors;
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
    List<Unit> enemiesInRange = world.getAllUnits()
        .stream()
        .filter(worldUnit -> unit.getTeam().canAttack(worldUnit.getTeam()))
        .filter(enemyUnit -> distanceToUnit(enemyUnit) <= autoAttackDistance)
        .filter(enemyUnit -> enemyUnit.getHealth() > 0)
        .collect(Collectors.toList());
    if (!enemiesInRange.isEmpty()) {
      // Assume that walking state will switch to attacking state if unit is close enough.
      Unit randomEnemy = enemiesInRange.get((int) (enemiesInRange.size() * Math.random()));
      requestAttackUnit(randomEnemy);
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
