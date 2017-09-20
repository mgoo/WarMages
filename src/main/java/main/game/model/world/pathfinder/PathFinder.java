package main.game.model.world.pathfinder;

import java.util.List;
import main.game.model.world.World;
import main.util.MapPoint;

/**
 * Implements the A* path finding algorithm to find the shortest path
 * between two places on the map (as a list of {@link MapPoint}). Ignores
 * other units/entities and a one-tile is passable by all units (they can
 * go through it).
 */
public class PathFinder {

  public static List<MapPoint> findPath(World world, MapPoint start, MapPoint end) {
    //TODO
    throw new Error("NYI");
  }
}
