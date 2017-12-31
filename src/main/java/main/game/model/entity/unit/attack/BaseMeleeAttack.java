package main.game.model.entity.unit.attack;

import java.util.Optional;
import main.common.World;
import main.common.entity.Unit;
import main.game.model.entity.unit.state.Targetable;

/**
 * The base melee attack.
 * TODO define all other sub classes of this by data rather than code.
 *
 * @author Andrew McGhie
 */
public abstract class BaseMeleeAttack extends Attack {

  public BaseMeleeAttack(CanEffect canEffect) {
    super(canEffect);
  }

  @Override
  public void execute(Unit unit, Targetable target, World world) {
    Optional<Unit> targetUnit = this.getEffectedUnits(unit, world, target)
        .stream()
        .findFirst();
    targetUnit.ifPresent(u -> u.takeDamage(this.getModifiedDamage(unit), world, unit));
  }
}
