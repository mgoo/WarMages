package main.game.model.entity.usables;

import main.game.model.entity.Unit;
import main.images.GameImage;

/**
 * The BuffAbility is a type of Ability that allows the HeroUnit using it to deal more damage.
 */
public class BuffAbility extends Ability {

  private static final long serialVersionUID = 1L;
  private final int damageIncrease;

  /**
   * Constructor takes the relevant GameImage icon as a parameter.
   */
  public BuffAbility(
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
    this.damageIncrease = damageIncrease;
  }

  @Override
  public Effect _createEffectForUnit(Unit unit) {
    return new BuffEffect(unit, getEffectDurationSeconds());
  }

  private class BuffEffect extends Effect {

    private static final long serialVersionUID = 1L;

    BuffEffect(Unit targetUnit, double durationSeconds) {
      super(targetUnit, durationSeconds);
    }

    @Override
    public int getDamageAmount(int currentDamageAmount) {
      return currentDamageAmount + damageIncrease;
    }
  }
}
