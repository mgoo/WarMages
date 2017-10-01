package main.game.model.entity;

import main.images.GameImage;
import main.util.MapPoint;

/**
 * Item extends {@link MapEntity}. An item is something that can be picked up and used by HeroUnit.
 */
public abstract class Item extends MapEntity {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor takes the coordinates of the item.
   */
  public Item(MapPoint coord) {
    super(coord);
  }

  /**
   * Applies the item to the given unit.
   */
  public abstract void applyTo(Unit unit);

  @Override
  public void setImage(GameImage image) {
    throw new UnsupportedOperationException("Cannot change the image of an item");
  }
}
