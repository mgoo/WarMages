package main.game.model.entity.usable;

import static main.game.model.entity.usable.BaseEffect.INSTANT_EFFECT_DURATION;

import main.exceptions.UsableStillInCoolDownException;
import main.game.model.entity.Unit;
import main.game.model.entity.unit.attack.Attack;
import main.game.model.entity.unit.state.TargetToAttack;
import main.game.model.entity.unit.state.Targetable;
import main.game.model.world.World;
import main.images.GameImage;
import main.util.MapPoint;

public class AttackUnitAbility extends BaseAbility {

  private static final long serialVersionUID = 1L;

  private final Attack attack;
  private final String description;

  public AttackUnitAbility(
      GameImage icon, double coolDownSeconds, Attack attack, String description
  ) {
    super(icon, coolDownSeconds, INSTANT_EFFECT_DURATION);
    this.attack = attack;
    this.description = description;
  }

  @Override
  protected void execute(World world, Targetable target) {
    if (!isReadyToBeUsed()) {
      throw new UsableStillInCoolDownException();
    }

    this.owner.setTarget(new TargetToAttack(
        this.owner,
        target,
        this.attack,
        true
    ));

    startCoolDown();
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
  public boolean canApplyTo(Unit unit, World world) {
    return this.attack.getEffectedUnits(this.owner, world, unit).size() != 0;
  }

  @Override
  public boolean canApplyTo(MapPoint target, World world) {
    return false;
  }
}
