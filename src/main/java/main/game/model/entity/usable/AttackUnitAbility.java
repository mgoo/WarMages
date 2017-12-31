package main.game.model.entity.usable;

import static main.game.model.entity.usable.BaseEffect.INSTANT_EFFECT_DURATION;

import main.common.World;
import main.common.entity.Unit;
import main.common.exceptions.UsableStillInCoolDownException;
import main.common.images.GameImage;
import main.common.util.MapPoint;
import main.game.model.entity.unit.attack.Attack;
import main.game.model.entity.unit.attack.Attack.CanEffect;
import main.game.model.entity.unit.state.TargetToAttack;
import main.game.model.entity.unit.state.Targetable;

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
  public boolean canApplyTo(Unit unit) {
    // TODO need world to use attack.getEffectedUnits
    if (this.attack.canEffectUnits == CanEffect.EVERYONE) {
      return true;
    } else if (this.attack.canEffectUnits == CanEffect.ENEMIES) {
      return unit.getTeam().canAttack(this.owner.getTeam());
    } else if (this.attack.canEffectUnits == CanEffect.ALLIES) {
      return !unit.getTeam().canAttack(this.owner.getTeam());
    } else {
      throw new IllegalStateException("The targets value was not recognosed");
    }
  }

  @Override
  public boolean canApplyTo(MapPoint target) {
    return false;
  }
}
