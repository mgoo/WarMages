package main.game.model.entity;

import main.images.GameImage;
import main.util.MapPoint;

/**
 * BuffItem extends{@link Item}. Gives the HeroUnit a buff ability (increases damage made to other
 * units).
 */
public class BuffItem extends Item {

  private static final long serialVersionUID = 1L;
  private Ability buff;

  /**
   * Constructor takes the coords to create the item at.
   */
  public BuffItem(MapPoint coord, GameImage image) {
    super(coord);
    buff = new BuffAbility(null);
    //todo pass image
  }

  @Override
  public void applyTo(HeroUnit heroUnit) {
    assert heroUnit != null;
    buff.apply(heroUnit);
    //todo make last for certain amount of time
  }

  @Override
  public void tick(long timeSinceLastTick) {
    //todo expire
  }
}
