package main.game.model.entity;

import main.images.GameImage;
import main.util.MapPoint;

/**
 * HealingItem extends{@link Item}. Can be used to heal a HeroUnit.
 */
public class HealingItem extends Item {

  private static final long serialVersionUID = 1L;
  private HealingAbility healing;

  /**
   * Constructor takes the coords of the HealingItem.
   */
  public HealingItem(MapPoint coord, GameImage image) {
    super(coord);
    //todo set image
  }

  @Override
  public void applyTo(HeroUnit heroUnit) {
    assert heroUnit != null;
    healing.apply(heroUnit);
    //todo implement healing
  }

  @Override
  public void tick(long timeSinceLastTick) {
    //todo expire
    //heroUnit.setHealing(false);
  }
}
