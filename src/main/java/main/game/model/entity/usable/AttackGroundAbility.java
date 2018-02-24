package main.game.model.entity.usable;

import main.exceptions.UsableStillInCoolDownException;

import main.game.model.data.DataLoader;
import main.game.model.data.dataobject.AbilityData;
import main.game.model.entity.Unit;
import main.game.model.entity.unit.state.MapPointTarget;
import main.game.model.entity.unit.state.TargetToAttack;
import main.game.model.entity.unit.state.Targetable;
import main.game.model.world.World;
import main.util.MapPoint;

/**
 * An ability that targets a MapPoint rather than a unit.
 * @author Andrew McGhie
 */
public class AttackGroundAbility extends BaseAbility {

  protected AttackGroundAbility(
      AbilityData abilityData,
      DataLoader dataLoader,
      int uses
  ) {
    super(abilityData, dataLoader, uses);
  }

  @Override
  public void use(World world, Unit target) {
    this.use(world, target.getCentre());
  }

  @Override
  public void use(World world, MapPoint target) {
    if (!isReadyToBeUsed()) {
      throw new UsableStillInCoolDownException();
    }

    this.owner.setTarget(
        new TargetToAttack(
            this.owner,
            new MapPointTarget(target),
            this,
            true
        )
    );
  }

  @Override
  public void execute(
      Unit unit, Targetable target, World world
  ) {
    if (target instanceof MapPointTarget) {
      super.execute(unit, target, world);
    } else {
      super.execute(unit, new MapPointTarget(target.getLocation()), world);
    }
  }

  @Override
  public boolean canApplyTo(Unit unit, World world) {
    return false;
  }

  @Override
  public boolean canApplyTo(MapPoint target, World world) {
    return true;
  }
}

