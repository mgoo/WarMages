package main.game.model.entity.usable;

import main.exceptions.UsableStillInCoolDownException;

import main.game.model.entity.Unit;
import main.game.model.entity.unit.attack.Attack;
import main.game.model.entity.unit.attack.AttackType;
import main.game.model.entity.unit.state.MapPointTarget;
import main.game.model.entity.unit.state.TargetToAttack;
import main.game.model.world.World;
import main.images.GameImage;
import main.images.UnitSpriteSheet.Sequence;
import main.util.MapPoint;

public class AttackGroundAbility extends BaseAbility {

  private final double radius;


  public AttackGroundAbility(
      GameImage icon, double coolDownSeconds, double radius, String description,
      String scriptLocation, double range, int attackSpeed, double windupPortion,
      Sequence attackSequence,
      AttackType attackType
  ) {
    this(icon, coolDownSeconds, radius, description, scriptLocation, range,
        attackSpeed, windupPortion, attackSequence, attackType,
        Attack.FIXED_AMOUNT, Attack.INSTANT_DURATION, BaseAbility.INFINITE_USES
    );
  }

  public AttackGroundAbility(
      GameImage icon, double coolDownSeconds, double radius, String description,
      String scriptLocation, double range, int attackSpeed, double windupPortion,
      Sequence attackSequence, AttackType attackType,
      double amount
  ) {
    this(icon, coolDownSeconds, radius, description, scriptLocation, range,
        attackSpeed, windupPortion, attackSequence, attackType,
        amount, Attack.INSTANT_DURATION, BaseAbility.INFINITE_USES
    );
  }

  public AttackGroundAbility(
      GameImage icon, double coolDownSeconds, double radius, String description,
      String scriptLocation, double range, int attackSpeed, double windupPortion,
      Sequence attackSequence, AttackType attackType,
      double amount, double duration
  ) {
    this(icon, coolDownSeconds, radius, description, scriptLocation, range,
        attackSpeed, windupPortion, attackSequence, attackType,
        amount, duration, BaseAbility.INFINITE_USES
    );
  }

  public AttackGroundAbility(
      GameImage icon, double coolDownSeconds, double radius, String description,
      String scriptLocation, double range, int attackSpeed, double windupPortion,
      Sequence attackSequence, AttackType attackType,
      double amount, double duration, int uses
  ) {
    super(icon, coolDownSeconds, description, scriptLocation, range,
        attackSpeed, windupPortion, attackSequence, attackType,
        amount, duration, uses);
    this.radius = radius;
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
            new MapPointTarget(target, this.radius),
            this,
            true
        )
    );
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

