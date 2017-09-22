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
   * Constructor takes the coordinates of the MapEntity
   * @param coord
   */
  public MapEntity(MapPoint coord) {
    super(coord, new  MapSize(1, 1));
  }

  @Override
  public void moveX(float amount) {
    throw new ActionNotSupportedException("MapEntity cannot move");
  }

  @Override
  public void moveY(float amount) {
    throw new ActionNotSupportedException("MapEntity cannot move");
  }
}
