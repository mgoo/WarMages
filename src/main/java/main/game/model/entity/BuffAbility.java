package main.game.model.entity;

import java.io.Serializable;
import main.images.GameImage;

/**
 * The BuffAbility is a type of Ability that allows the unit using it to deal more damage.
 */
public class BuffAbility extends Ability implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor takes no parameters.
   */
  public BuffAbility(GameImage icon) {
    super("This ability buffs the unit using it, allowing the unit to cause more damage.", icon);
  }

  @Override
  public void apply(Unit unit) {
    assert unit != null;
    unit.setDamageAmount(10);
  }
}
