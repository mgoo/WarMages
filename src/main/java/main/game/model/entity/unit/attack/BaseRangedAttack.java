package main.game.model.entity.unit.attack;

import main.common.World;
import main.common.entity.Projectile;
import main.common.entity.Unit;
import main.game.model.entity.unit.state.Targetable;

/**
 * The base Ranged attack.
 * @author Andrew McGhie
 */
public abstract class BaseRangedAttack extends Attack {

  public BaseRangedAttack(CanEffect canEffect) {
    super(canEffect);
  }

  @Override
  public void execute(
      Unit unit, Targetable target, World world
  ) {
    Projectile projectile = this.createProjectile(unit, target);
    world.addProjectile(projectile);
  }

  abstract Projectile createProjectile(Unit unit, Targetable target);
}
