package main.game.model.entity.unit.state;

import java.util.Comparator;
import main.game.model.entity.Team;
import main.game.model.entity.Unit;
import main.game.model.world.World;
import main.images.Animation;

/**
 * Idle state for Unit.
 *
 * @author paladogabr
 */
public class Idle extends UnitState {

  private static final long serialVersionUID = 1L;
  private final double angle;

  public Idle(Unit unit) {
    super(new Animation(unit.getSpriteSheet(), "animation:idle", 2), unit);
    this.angle = unit.getCurrentAngle();
  }

  @Override
  public void tick(Long timeSinceLastTick, World world) {
    super.tick(timeSinceLastTick, world);

    double autoAttackDistance = unit.getAutoAttackDistance();
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
  public double getCurrentAngle() {
    return this.angle;
  }

  @Override
  public void onTakeDamage(double amount, World world, Unit attacker) {
    if (unit.getHealth() <= 0 || attacker.getHealth() <= 0 || attacker.getTeam() == Team.PLAYER) {
      return;
    }

    requestAttackUnit(attacker);
  }

  private void requestAttackUnit(Unit enemy) {
    TargetToAttack enemyTarget =
        new TargetToAttack(unit, enemy, unit.getBaseAttack());
    this.unit.setTarget(enemyTarget);
  }

  private double distanceToUnit(Unit target) {
    return unit.getCentre().distanceTo(target.getCentre());
  }
}
