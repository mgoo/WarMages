package main.game.model.entity.unit.attack;

import main.common.World;
import main.common.entity.Unit;

/**
 * The base melee attack.
 * TODO define all other sub classes of this by data rather than code.
 *
 * @author Andrew McGhie
 */
public abstract class BaseMeleeAttack extends Attack {

  @Override
  public void execute(Unit unit, Unit enemyUnit, World world) {
    enemyUnit.takeDamage(this.getModifiedDamage(unit), world, unit);
  }
}
