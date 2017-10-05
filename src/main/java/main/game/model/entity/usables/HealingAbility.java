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
    super("Allows the holder to heal teammates", icon, coolDownSeconds);
    this.healAmount = healAmount;
  }

  @Override
  public Effect _createEffectForUnit(Unit unit) {
    return new HealEffect(unit);
  }

  private class HealEffect extends Effect {

    private static final long serialVersionUID = 1L;

    private final Unit unit;

    HealEffect(Unit unit) {
      super(unit);
      this.unit = unit;
    }

    @Override
    public void start() {
      unit.gainHealth(healAmount);
    }

    @Override
    public boolean isExpired() {
      return true;
    }
  }

}
