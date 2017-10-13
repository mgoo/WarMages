package main.game.model.entity.usable;

import java.util.Collection;
import java.util.stream.Collectors;
import main.common.entity.usable.Effect;
import main.common.entity.Team;
import main.common.entity.Unit;
import main.game.model.world.World;
import main.common.images.GameImage;

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
    super(
        "This ability buffs the heroUnit using it, allowing the heroUnit to cause more damage.",
        icon,
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
  public Collection<Unit> _selectUnitsToApplyOn(World world, Collection<Unit> selectedUnits) {
    return world.getAllUnits()
        .stream()
        .filter(unit -> unit.getTeam() == Team.PLAYER)
        .collect(Collectors.toList());
  }

  @Override
  public Effect _createEffectForUnit(Unit unit) {
    return new DamageBuffEffect(unit, getEffectDurationSeconds());
  }

  private class DamageBuffEffect extends BaseEffect {

    private static final long serialVersionUID = 1L;

    DamageBuffEffect(Unit targetUnit, double durationSeconds) {
      super(targetUnit, durationSeconds);
    }

    @Override
    public int alterDamageAmount(int currentDamageAmount) {
      return super.alterDamageAmount(currentDamageAmount) + damageIncrease;
    }
  }
}
