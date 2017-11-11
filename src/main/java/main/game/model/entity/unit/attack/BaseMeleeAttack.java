package main.game.model.entity.unit.attack;

import main.common.World;
import main.common.entity.Unit;

/**
 * @author Andrew McGhie
 */
public abstract class BaseMeleeAttack extends Attack {
  @Override
  public void execute(Unit unit, Unit enemyUnit, World world) {
      enemyUnit.takeDamage(unit.getDamageModifier() * this.getDamage(unit), world, unit);
  }
}
