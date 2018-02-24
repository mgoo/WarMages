package main.game.model.entity.usable;

import main.exceptions.UsableStillInCoolDownException;

import main.game.model.data.DataLoader;
import main.game.model.data.dataObject.AbilityData;
import main.game.model.entity.Unit;
import main.game.model.entity.unit.state.TargetToAttack;
import main.game.model.world.World;
import main.util.MapPoint;

public class AttackUnitAbility extends BaseAbility {

  private static final long serialVersionUID = 1L;

  protected AttackUnitAbility(AbilityData abilityData, DataLoader dataLoader) {
    super(abilityData, dataLoader, INFINITE_USES);
  }


  protected AttackUnitAbility(AbilityData abilityData, DataLoader dataLoader, int uses) {
    super(abilityData, dataLoader, uses);
  }

  @Override
  public void use(World world, Unit unit) {
    if (!isReadyToBeUsed()) {
      throw new UsableStillInCoolDownException();
    }
    this.owner.setTarget(new TargetToAttack(
        this.owner,
        unit,
        this,
        true
    ));
  }

  @Override
  public void use(World world, MapPoint mapPoint) {
    throw new RuntimeException("This ability cannot be used on a MapPoint");
  }


  @Override
  public boolean canApplyTo(Unit unit, World world) {
    return this.getEffectedUnits(this.owner, world, unit).size() != 0;
  }

  @Override
  public boolean canApplyTo(MapPoint target, World world) {
    return false;
  }
}
