package main.game.model.entity;

import com.sun.xml.internal.ws.addressing.model.ActionNotSupportedException;
import main.images.GameImage;
import main.util.MapPoint;
import main.util.MapSize;

/**
 * An {@link Entity} that cannot move / be moved on the map, and takes up a whole square {@link
 * Unit}s cannot move through one of these.
 */
public abstract class MapEntity extends Entity {

  /**
   * Constructor takes the coordinates of the MapEntity.
   */
  public MapEntity(MapPoint coord) {
    super(coord, new MapSize(1, 1));
  }

  @Override
  public void moveX(double amount) {
    throw new ActionNotSupportedException("MapEntity cannot move");
  }

  @Override
  public void moveY(double amount) {
    throw new ActionNotSupportedException("MapEntity cannot move");
  }

  /**
   * Returns boolean representing whether the given point lies inside the MapEntity.
   */
  public boolean contains(MapPoint point) {
    return (point.x >= position.x && point.y >= position.y && point.x <= position.x + size.width
        && point.y <= position.y + size.height);
  }
}
