package main.game.model.entity;

import main.util.MapPoint;

/**
 * Item extends {@link Entity}. An item is something that can be picked up and used by HeroUnit
 */
public abstract class Item extends MapEntity {

  public Item(MapPoint coord, float size) {
    super(coord, size);
  }

  public abstract void applyTo(Unit unit);
}
