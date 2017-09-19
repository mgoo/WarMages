package main.Entity;

/**
 * An {@link Entity} that cannot move / be moved on the map, and takes up a whole square
 * {@link Unit}s cannot move through one of these.
 */
public class MapEntity extends Entity {

  public MapEntity(MapPoint coord, float size){
    super(coord, size);
  }

  @Override
  public GameImage.Config getImage() {
    throw new Error("NYI");
  }
}
