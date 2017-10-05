package main.game.model.entity.usables;

import main.game.model.entity.Unit;
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
  public BuffAbility(GameImage icon, double coolDownSeconds) {
    super(
        "This ability buffs the heroUnit using it, allowing the heroUnit to cause more damage.",
        icon,
        coolDownSeconds
    );
  }

  @Override
  public Effect _createEffectForUnit(Unit unit) {
    return null;
  }
}
