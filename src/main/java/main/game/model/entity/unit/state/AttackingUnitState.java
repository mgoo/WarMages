package main.game.model.entity.unit.state;

import main.common.Unit;
import main.common.images.UnitSpriteSheet;
import main.common.Direction;
import main.game.model.entity.Projectile;
import main.game.model.entity.unit.DefaultUnit;
import main.game.model.entity.unit.UnitType;
import main.game.model.world.World;

public class AttackingUnitState extends UnitState {

  private static final long serialVersionUID = 1L;

  public AttackingUnitState(Unit unit) {
    super(unit.getUnitType().getAttackSequence(), unit);
  }

  @Override
  public void tick(Long timeSinceLastTick, World world) {
    super.tick(timeSinceLastTick, world);

    if (imagesComponent.isOnAttackTick()) {
      onAttackFrame(world);
    }
  }

  @Override
  public UnitState updateState() {
    if (!imagesComponent.isReadyToTransition() || nextState == null) {
      return this;
    }

    return nextState;
  }

  @Override
  public Direction getCurrentDirection() {
    Unit target = unit.getTarget();
    if (target == null) {
      return super.getCurrentDirection();
    }

    return Direction.between(unit.getCentre(), target.getCentre());
  }

  /**
   * Called when the attack frame is reached and the animation
   * {@link UnitSpriteSheet.Sequence}.
   */
  private void onAttackFrame(World world) {
    UnitType unitType = unit.getUnitType();
    Unit target = unit.getTarget();

    if (unitType.canShootProjectiles()) {
      Projectile projectile = unitType.createProjectile(unit, target);
      world.addProjectile(projectile);
    } else {
      // Non projectile attack (e.g. spear)
      target.takeDamage(unit.getDamageAmount(), world);
    }
  }
}
