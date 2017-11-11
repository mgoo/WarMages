package main.common;

import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Function;
import main.common.util.MapPoint;

/**
 * Implements the A* path finding algorithm to find the shortest path between two places on the map.
 * Ignores other units/entities and a one-tile is passable by all units (they can go through it).
 *
 * @author Hrshikesh Arora
 */
public interface PathFinder {

  /**
   * Uses the A* path finding algorithm to find the shortest path from a start point to an end point
   * on the world returning a list of points along current path.
   *
   * @param isPassable a function that determines whether a given point is passable or not
   * @param start the start point of the path
   * @param end the end/goal point of the path
   * @return a list of points representing the shortest path
   */
  Stack<MapPoint> findPath(Function<MapPoint, Boolean> isPassable, MapPoint start, MapPoint end);

  Stack<MapPoint> findPath(Function<MapPoint, Boolean> isPassable,
                           MapPoint start,
                           MapPoint end,
                           double AcceptableDistanceFromEnd);

}
