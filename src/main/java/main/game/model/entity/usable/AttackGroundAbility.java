package main.game.model.entity.usable;

import static main.game.model.entity.usable.BaseEffect.INSTANT_EFFECT_DURATION;

import main.common.World;
import main.common.entity.Unit;
import main.common.exceptions.UsableStillInCoolDownException;
import main.common.images.GameImage;
import main.common.util.MapPoint;
import main.game.model.entity.unit.attack.Attack;
import main.game.model.entity.unit.state.MapPointTarget;
import main.game.model.entity.unit.state.TargetToAttack;
import main.game.model.entity.unit.state.Targetable;

public class AttackGroundAbility extends BaseAbility {

  private final Attack attack;
  private final double radius;
  private final String description;

  public AttackGroundAbility(
      GameImage icon, double coolDownSeconds, Attack attack, double radius, String description
  ) {
    super(icon, coolDownSeconds, INSTANT_EFFECT_DURATION);
    this.attack = attack;
    this.radius = radius;
    this.description = description;
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

    this.execute(world, new MapPointTarget(target, this.radius));

    startCoolDown();
  }

  @Override
  protected void execute(World world, Targetable target) {
    this.owner.setTarget(
        new TargetToAttack(
            this.owner,
            target,
            this.attack,
            true
        )
    );
  }

  @Override
  public String getDescription() {
    int coolDownProgressSeconds = (int)(this.coolDownSeconds * this.getCoolDownProgress());
    return this.description + "<br>"
        + "<b>Damage</b>: " + Math.round(this.attack.getModifiedDamage(this.owner)) + "<br>"
        + "<b>Range</b>: " + Math.round(this.attack.getModifiedRange(this.owner)) + "<br>"
        + "<b>Cooldown</b>: " + coolDownProgressSeconds + "/" + this.coolDownSeconds + "s";
  }

  @Override
  public boolean canApplyTo(Unit unit) {
    return false;
  }

  @Override
  public boolean canApplyTo(MapPoint target) {
    return true;
  }
}

