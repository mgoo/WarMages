package main.game.model.entity;

import main.game.model.world.World;
import main.images.GameImage;
import main.util.MapPoint;

/**
 * BuffItem extends{@link Item}. Gives the unit a buff ability (increases damage made to other
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
  public void applyTo(Unit unit) {
    assert unit != null;
    buff.apply(unit);
    //todo make last for certain amount of time
  }

  @Override
  public void tick(long timeSinceLastTick, World world) {
    //todo expire
  }
}
