package world;

import util.*;

/**
 * World class is a representation of all the in-play entities and
 * in-play entities: all entity objects that have been instantiated.
 */
public class World {

  public World(Collection<MapEntity> mapEntities) {
  }

  public Collection<Entity> findEntities(MapRect rect) {
    throw new Error("NYI");
  }

  public boolean isPassable(MapPoint point) {
    throw new Error("NYI");
  }

  public void easeTrigger(int index){
    throw new Error("NYI");
  }

  public void tick(long timeSinceLastTick) {
    throw new Error("NYI");
  }

}
