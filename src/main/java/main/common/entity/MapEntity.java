package main.common.entity;

/**
 * An {@link Entity} that cannot move / be moved on the map, and {@link
 * Unit}s cannot move through one of these.
 * @author paladogabr
 */
public interface MapEntity extends Entity {

  /**
   * Returns whether the pathfinder should take this entity into account.
   */
  boolean isPassable();
}
