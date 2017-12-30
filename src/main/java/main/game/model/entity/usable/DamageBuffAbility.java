package main.game.model.entity.usable;

import java.util.Collection;
import main.common.entity.usable.Effect;
import main.common.entity.Team;
import main.common.entity.Unit;
import main.common.World;
import main.common.images.GameImage;
import main.common.util.MapPoint;
import main.game.model.entity.unit.state.TargetUnit;

/**
 * The {@link DamageBuffAbility} is a type of Ability that allows the HeroUnit using it to deal more
 * damage.
 * @author chongdyla
 */
public class DamageBuffAbility extends BaseAbility {

  private static final long serialVersionUID = 1L;
  private final int damageIncrease;

  /**
   * Constructor takes the relevant GameImage icon as a parameter.
   */
  public DamageBuffAbility(
      GameImage icon,
      int damageIncrease,
      double coolDownSeconds,
      double effectDurationSeconds
  ) {
    super(icon,
        coolDownSeconds,
        effectDurationSeconds
    );

    if (damageIncrease <= 0) {
      throw new IllegalArgumentException();
    }

    this.damageIncrease = damageIncrease;
  }

  public int getDamageIncrease() {
    return damageIncrease;
  }

  @Override
  public Collection<Unit> selectUnitsToApplyOn(World world, Collection<Unit> selectedUnits) {
    return selectedUnits;
  }

  @Override
  public Effect createEffectForUnit(Unit unit, World world) {
    return new DamageBuffEffect(unit, world, getEffectDurationSeconds());
  }

  private class DamageBuffEffect extends BaseEffect {

    private static final long serialVersionUID = 1L;

    DamageBuffEffect(Unit targetUnit, World world, double durationSeconds) {
      super(targetUnit, world, durationSeconds);
    }

    @Override
    public double alterDamageModifier(double currentDamageModifier) {
      return super.alterDamageModifier(currentDamageModifier) + damageIncrease;
    }

  }

  @Override
  public boolean canApplyTo(Unit unit) {
    return unit.getTeam() == Team.PLAYER;
  }

  @Override
  public boolean canApplyTo(MapPoint target) {
    return false;
  }

  @Override
  public String getDescription() {
    return "This ability buffs a unit, allowing it to cause more damage.<br>"
        + "<b>Amount</b>: " + this.damageIncrease + "<br>"
        + "<b>Duration</b>: " + (int)this.effectDurationSeconds + "s<br>"
        + "<b>Cooldown</b>: " + this.coolDownSeconds + "s";
  }
}
