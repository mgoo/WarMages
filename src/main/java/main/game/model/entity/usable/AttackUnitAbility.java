package main.game.model.entity.usable;

import main.exceptions.UsableStillInCoolDownException;

import main.game.model.entity.Unit;
import main.game.model.entity.unit.attack.Attack;
import main.game.model.entity.unit.attack.AttackType;
import main.game.model.entity.unit.state.TargetToAttack;
import main.game.model.world.World;
import main.images.GameImage;
import main.images.UnitSpriteSheet.Sequence;
import main.util.MapPoint;

public class AttackUnitAbility extends BaseAbility {

  private static final long serialVersionUID = 1L;

  public AttackUnitAbility(
      GameImage icon, double coolDownSeconds, String description,
      String scriptLocation, double range, int attackSpeed,
      double windupPortion, Sequence attackSequence, AttackType attackType
  ) {
    super(icon, coolDownSeconds, description,
        scriptLocation, range, attackSpeed, windupPortion, attackSequence, attackType,
        Attack.FIXED_AMOUNT, Attack.INSTANT_DURATION, BaseAbility.INFINITE_USES);
  }

  public AttackUnitAbility(
      GameImage icon, double coolDownSeconds, String description,
      String scriptLocation, double range, int attackSpeed,
      double windupPortion, Sequence attackSequence, AttackType attackType,
      double amount
  ) {
    super(icon, coolDownSeconds, description,
        scriptLocation, range, attackSpeed, windupPortion, attackSequence, attackType,
        amount, Attack.INSTANT_DURATION, BaseAbility.INFINITE_USES);
  }

  public AttackUnitAbility(
      GameImage icon, double coolDownSeconds, String description,
      String scriptLocation, double range, int attackSpeed,
      double windupPortion, Sequence attackSequence, AttackType attackType,
      double amount, double duration
  ) {
    super(icon, coolDownSeconds, description,
        scriptLocation, range, attackSpeed, windupPortion, attackSequence, attackType,
        amount, duration, INFINITE_USES);
  }


  public AttackUnitAbility(
      GameImage icon, double coolDownSeconds, String description,
      String scriptLocation, double range, int attackSpeed,
      double windupPortion, Sequence attackSequence, AttackType attackType,
      double amount, double duration, int uses
  ) {
    super(icon, coolDownSeconds, description,
        scriptLocation, range, attackSpeed, windupPortion, attackSequence, attackType,
        amount, duration, uses);
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
    throw new RuntimeException("This usable cannot be used on a MapPoint");
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
