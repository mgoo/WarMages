package main.game.model.entity.unit.state;

import main.common.World;
import main.common.entity.Projectile;
import main.common.entity.Unit;
import main.common.images.UnitSpriteSheet;
import main.game.model.entity.unit.DefaultUnit;
import main.game.model.entity.unit.UnitAnimation;
import main.game.model.entity.unit.UnitType;

/**
 * @author Andrew McGhie
 */
public class Attacking extends Interacting {

  private final int applicationTick;
  private final double windUp;

  private int currentTick = 0;

  public Attacking(
      Unit unit,
      TargetEnemyUnit target,
      double windUp
  ) {
    super(unit,
        new UnitAnimation(unit, unit.getUnitType().getAttackSequence(), unit.getAttackSpeed()),
        target);

    this.applicationTick = (int) (unit.getAttackSpeed() * windUp);
    this.windUp = windUp;

    if (target.isStillValid()) {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public void tick(Long timeSinceLastTick, World world) {
    super.tick(timeSinceLastTick, world);
    currentTick++;

    if (!target.hasArrived()) {
      requestedNextState = new Moving(
          unit,
          new TargetMapPoint(unit, target.getDestination()),
          new Attacking(
              this.unit,
              ((TargetEnemyUnit) this.target),
              this.windUp // TODO get the windup from the unit rather than the parameter
          )
      );
      return;
    }

    if (!target.isStillValid()) {
      return;
    }

    if (this.currentTick == this.applicationTick) {
      performAttack(world);
    }
  }

  @Override
  public UnitState updateState() {
    if (requestedNextState != null) {
      return requestedNextState;
    }
    if (!target.isStillValid()) {
      return new Idle(unit);
    }
    return this;
  }

  /**
   * Called when the attack frame is reached in the unitAnimation.
   * TODO shift this into attack Object
   * objects {@link UnitSpriteSheet.Sequence}.
   */
  private void performAttack(World world) {
    UnitType unitType = unit.getUnitType();

    if (unitType.canShootProjectiles()) {
      Projectile projectile =
          unitType.createProjectile(unit, ((TargetEnemyUnit) target).getEnemyUnit());
      world.addProjectile(projectile);
    } else {
      // Non projectile attack (e.g. spear)
      boolean killed =
          ((TargetEnemyUnit) target).getEnemyUnit().takeDamage(unit.getDamageAmount(), world, unit);
      if (killed) {
        unit.nextLevel();
      }
    }
  }
}
