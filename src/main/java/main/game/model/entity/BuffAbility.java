package main.game.model.entity;

import main.images.GameImage;

/**
 * The BuffAbility is a type of Ability that allows the HeroUnit using it to deal more damage.
 */
public class BuffAbility extends Ability {

  private static final long serialVersionUID = 1L;
  private final int tickTimeout = 60; //todo finalize

  /**
   * Constructor takes the relevant GameImage icon as a parameter.
   */
  public BuffAbility(GameImage icon) {
    super(
        "This ability buffs the heroUnit using it, allowing the heroUnit to cause more damage.",
        icon
    );
  }

  @Override
  public void apply(HeroUnit heroUnit) {
    if (heroUnit == null) {
      throw new IllegalArgumentException("Null HeroUnit");
    }
    heroUnit.setDamageAmount(10);
  }

  @Override
  public void disableOn(HeroUnit heroUnit) {
    if (heroUnit == null) {
      throw new IllegalArgumentException("Null HeroUnit");
    }
    heroUnit.resetDamage();
  }

  @Override
  public boolean tickTimedOut(int tickCount) {
    return (tickCount == tickTimeout);
  }
}
