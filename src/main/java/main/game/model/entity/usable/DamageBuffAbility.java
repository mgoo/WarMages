package main.game.model.entity.usable;

import main.game.model.entity.Unit;
import main.images.GameImage;

/**
 * The {@link DamageBuffAbility} is a type of Ability that allows the HeroUnit using it to deal more
 * damage.
 */
public class DamageBuffAbility extends Ability {

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
  public Effect _createEffectForUnit(Unit unit) {
    return new DamageBuffEffect(unit, getEffectDurationSeconds());
  }

  private class DamageBuffEffect extends Effect {

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
