package main.game.model.entity;

import main.util.MapPoint;
import main.util.MapSize;

/**
 * An {@link Entity} that cannot move / be moved on the map, and takes up a whole square {@link
 * Unit}s cannot move through one of these.
 */
public abstract class MapEntity extends Entity {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor takes the coordinates of the MapEntity.
   */
  public MapEntity(MapPoint coord) {
    super(coord, new MapSize(1, 1));
  }

  @Override
  public void translatePosition(double dx, double dy) {
    throw new UnsupportedOperationException();
  }

  /**
   * Returns boolean representing whether the given point lies inside the MapEntity.
   */
  public boolean contains(MapPoint point) {
    return (point.x >= position.x && point.y >= position.y && point.x <= position.x + size.width
        && point.y <= position.y + size.height);
  }
}
