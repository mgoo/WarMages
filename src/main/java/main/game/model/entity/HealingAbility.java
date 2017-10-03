package main.game.model.entity;

import main.images.GameImage;

public class HealingAbility extends Ability {

  private static final long serialVersionUID = 1L;
  private final int tickTimeout = 100; //todo finalize

  /**
   * Constructor takes the icon that represent the ability.
   */
  public HealingAbility(GameImage icon) {
    super("Allows the holder to heal teammates", icon);
  }

  @Override
  public void apply(HeroUnit heroUnit) {
    heroUnit.setHealing(true);
  }

  @Override
  public void disableOn(HeroUnit heroUnit) {
    heroUnit.setHealing(false);
  }

  @Override
  public boolean tickTimedOut(int tickCount) {
    return (tickCount == tickTimeout);
  }
}
