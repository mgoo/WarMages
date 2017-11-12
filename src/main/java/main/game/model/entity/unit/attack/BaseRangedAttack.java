package main.game.model.entity.unit.attack;

import main.common.World;
import main.common.entity.Projectile;
import main.common.entity.Unit;

/**
 * The base Ranged attack.
 * @author Andrew McGhie
 */
public abstract class BaseRangedAttack extends Attack {

  @Override
  public void execute(
      Unit unit, Unit enemyUnit, World world
  ) {
    Projectile projectile = this.createProjectile(unit, enemyUnit);
    world.addProjectile(projectile);
  }

  abstract Projectile createProjectile(Unit unit, Unit enemyUnit);
}
