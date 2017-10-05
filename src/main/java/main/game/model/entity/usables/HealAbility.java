package main.game.model.entity.usables;

import static main.game.model.entity.usables.Effect.INSTANT_EFFECT_DURATION;

import main.game.model.entity.Unit;
import main.images.GameImage;

public class HealAbility extends Ability {

  private static final long serialVersionUID = 1L;

  private final int healAmount;

  /**
   * Constructor takes a string description of the ability, and the icon that represent the ability.
   */
  public HealAbility(
      GameImage icon,
      double coolDownSeconds,
      int healAmount
  ) {
    super("Instantly heals units", icon, coolDownSeconds, INSTANT_EFFECT_DURATION);

    if (healAmount <= 0) {
      throw new IllegalArgumentException();
    }

    this.healAmount = healAmount;
  }

  @Override
  public Effect _createEffectForUnit(Unit unit) {
    return new HealEffect(unit);
  }

  public int getHealAmount() {
    return healAmount;
  }

  private class HealEffect extends Effect {

    private static final long serialVersionUID = 1L;

    private final Unit unit;

    HealEffect(Unit unit) {
      super(unit, INSTANT_EFFECT_DURATION);
      this.unit = unit;
    }

    @Override
    public void start() {
      super.start();
      unit.gainHealth(healAmount);
    }
  }

}
