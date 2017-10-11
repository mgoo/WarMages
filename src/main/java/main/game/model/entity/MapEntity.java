package main.game.model.entity;

import main.common.util.MapPoint;
import main.common.util.MapSize;

/**
 * An {@link Entity} that cannot move / be moved on the map, and takes up a whole square {@link
 * Unit}s cannot move through one of these.
 */
public abstract class MapEntity extends Entity {

  private static final long serialVersionUID = 1L;

  public MapEntity(MapPoint coord) {
    this(coord, new MapSize(1, 1));
  }

  public MapEntity(MapPoint coord, MapSize size) {
    super(coord, size);
  }

  @Override
  public void translatePosition(double dx, double dy) {
    throw new UnsupportedOperationException();
  }

  /**
   * Returns boolean representing whether the given point lies inside the MapEntity.
   */
  public boolean contains(MapPoint point) {
    return getRect().contains(point);
  }
}
