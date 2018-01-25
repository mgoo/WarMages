package main.game.model.entity.usable;

import static main.game.model.entity.usable.BaseEffect.INSTANT_EFFECT_DURATION;

import main.exceptions.UsableStillInCoolDownException;
import main.game.model.entity.Unit;
import main.game.model.entity.unit.attack.FixedAttack;
import main.game.model.entity.unit.state.MapPointTarget;
import main.game.model.entity.unit.state.TargetToAttack;
import main.game.model.entity.unit.state.Targetable;
import main.game.model.world.World;
import main.images.GameImage;
import main.util.MapPoint;

public class AttackGroundAbility extends BaseAbility {

  private final FixedAttack attack;
  private final double radius;
  private final String description;

  public AttackGroundAbility(
      GameImage icon, double coolDownSeconds, FixedAttack attack, double radius, String description
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
        + "<b>Range</b>: " + Math.round(this.attack.getModifiedRange(this.owner)) + "<br>"
        + "<b>Cooldown</b>: " + coolDownProgressSeconds + "/" + this.coolDownSeconds + "s";
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

