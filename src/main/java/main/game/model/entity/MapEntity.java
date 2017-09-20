package main.game.model.entity;

import main.images.GameImage;
import main.util.MapPoint;

/**
 * An {@link Entity} that cannot move / be moved on the map, and takes up a whole square
 * {@link Unit}s cannot move through one of these.
 */
public class MapEntity extends Entity {

  public MapEntity(MapPoint coord, float size){
    super(coord, size);
  }

  @Override
  public GameImage getImage() {
    throw new Error("NYI");
  }
}
