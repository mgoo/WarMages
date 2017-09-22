package main.game.model.world;

import java.util.Collection;
import main.game.model.entity.Entity;
import main.game.model.entity.MapEntity;
import main.util.MapPoint;
import main.util.MapRect;

/**
 * World class is a representation of all the in-play entities and in-play entities: all entity
 * objects that have been instantiated.
 */
public class World {

  public World(Collection<MapEntity> mapEntities) {
  }

  /**
   * A getter method which returns all the entities in the world thats within the selection. The
   * collection to be return must be ordered. (TODO ordering to be discussed)
   *
   * @param rect a selection box.
   * @return A collection of Entities within the given selection rect.
   */
  public Collection<Entity> findEntities(MapRect rect) {
    throw new Error("NYI");
  }

  /**
   * A getter method which checks if a certain point in the map can be moved into.
   *
   * TODO - make sure that the method returns false for points outside the Map
   *
   * @param point a point in the map.
   * @return returns whether the point can be moved into.
   */
  public boolean isPassable(MapPoint point) {
    throw new Error("NYI");
  }

  /**
   * A method specific for progression of game. Triggers are specific quests/goals to be achieved
   * for progression.
   *
   * @param index the index of selected trigger.
   */
  public void easeTrigger(int index) {
    throw new Error("NYI");
  }

  /**
   * A method to change all the current positions/animations of all entities in the world.
   */
  public void tick(long timeSinceLastTick) {
    throw new Error("NYI");
  }

}
