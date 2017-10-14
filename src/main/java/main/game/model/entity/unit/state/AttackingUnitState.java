package main.game.model.entity.unit.state;

import static java.util.Objects.requireNonNull;

import main.common.entity.Unit;
import main.common.images.UnitSpriteSheet;
import main.common.entity.Direction;
import main.game.model.entity.Projectile;
import main.game.model.entity.unit.DefaultUnit;
import main.game.model.entity.unit.UnitType;
import main.game.model.entity.unit.state.WalkingUnitState.MapPointTarget;
import main.game.model.world.World;

/**
 * Attacking state for Unit.
 *
 * @author paladogabr
 */
public class AttackingUnitState extends UnitState {

  private static final long serialVersionUID = 1L;

  private final Unit target;

  public AttackingUnitState(DefaultUnit unit, Unit target) {
    super(unit.getUnitType().getAttackSequence(), unit);

    if (target.getHealth() == 0 || !unit.getTeam().canAttack(target.getTeam())) {
      throw new IllegalArgumentException();
    }

    this.target = requireNonNull(target);
  }

  @Override
  public void tick(Long timeSinceLastTick, World world) {
    super.tick(timeSinceLastTick, world);

    double distanceToTarget = unit.getCentre().distanceTo(target.getCentre());
    if (distanceToTarget >= unit.getAttackDistance()) {
      requestedNextState = new WalkingUnitState(
          unit,
          new MapPointTarget(unit, target.getCentre())
      );
      return;
    }

    if (target.getHealth() == 0) {
      return;
    }

    if (imagesComponent.isOnAttackTick()) {
      performAttack(world);
    }
  }

  @Override
  public UnitState updateState() {
    if (requestedNextState != null) {
      return requestedNextState;
    }

    if (target.getHealth() == 0) {
      return new IdleUnitState(unit);
    }

    return this;
  }

  @Override
  public Direction getCurrentDirection() {
    return Direction.between(unit.getCentre(), target.getCentre());
  }

  /**
   * Called when the attack frame is reached in the animation
   * {@link UnitSpriteSheet.Sequence}.
   */
  private void performAttack(World world) {
    UnitType unitType = unit.getUnitType();

    if (unitType.canShootProjectiles()) {
      Projectile projectile = unitType.createProjectile(unit, target);
      world.addProjectile(projectile);
    } else {
      // Non projectile attack (e.g. spear)
      boolean killed = target.takeDamage(unit.getDamageAmount(), world);
      if (killed) {
        unit.nextLevel();
      }
    }
  }
}
