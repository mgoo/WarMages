package main.game.model.entity.usables;

import main.game.model.entity.Unit;
import main.images.GameImage;

public class HealingAbility extends Ability {

  private static final long serialVersionUID = 1L;

  private final int healAmount;

  /**
   * Constructor takes a string description of the ability, and the icon that represent the ability.
   */
  public HealingAbility(
      GameImage icon,
      double coolDownSeconds,
      int healAmount
  ) {
    super("Instantly heals units", icon, coolDownSeconds, 0);
    this.healAmount = healAmount;
  }

  @Override
  public Effect _createEffectForUnit(Unit unit) {
    return new HealEffect(unit, getEffectDurationSeconds());
  }

  private class HealEffect extends Effect {

    private static final long serialVersionUID = 1L;

    private final Unit unit;

    HealEffect(Unit unit, double durationSeconds) {
      super(unit, durationSeconds);
      this.unit = unit;
    }

    @Override
    public void start() {
      super.start();
      unit.gainHealth(healAmount);
    }
  }

}
