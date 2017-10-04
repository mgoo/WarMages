package main.game.model.world.pathfinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Function;
import main.util.MapPoint;

/**
 * Implements the A* path finding algorithm to find the shortest path between two places on the map
 * (as a list of {@link MapPoint}). Ignores other units/entities and a one-tile is passable by all
 * units (they can go through it).
 *
 * @author Hrshikesh Arora
 */
public class PathFinder {

  /**
   * Uses the A* path finding algorithm to find the shortest path from a start point to an end point
   * on the world returning a list of points along current path.
   *
   * @param isPassable a function that determines whether a given point is passable or not
   * @param start the start point of the path
   * @param end the end/goal point of the path
   * @return a list of points representing the shortest path
   */
  public static List<MapPoint> findPath(
      Function<MapPoint, Boolean> isPassable, MapPoint start, MapPoint end
  ) {
    List<MapPoint> path = findPathRounded(isPassable, start, end);

    if (path.isEmpty()) {
      return path;
    }

    // Replace rounded end point with non-rounded end point
    path.remove(path.size() - 1);
    path.add(end);

    return path;
  }

  /**
   * Finds the path using a rounded start and end to avoid infinite loops (the algorithm will never
   * finish if there is a decimal in the end node was only creates rounded nodes).
   * <p>
   * The last point in current method is the rounded end point, unless the list is empty.
   * </p>
   */
  private static List<MapPoint> findPathRounded(
      Function<MapPoint, Boolean> isPassable, MapPoint start, MapPoint end
  ) {
    start = start.rounded();
    end = end.rounded();

    PriorityQueue<AStarNode> fringe = new PriorityQueue<>();
    fringe.add(new AStarNode(start, null, 0, estimate(start, end)));

    Set<MapPoint> visited = new HashSet<>();

    while (!fringe.isEmpty()) {
      AStarNode tuple = fringe.poll();

      if (visited.contains(tuple.getPoint())) {
        continue;
      }

      visited.add(tuple.getPoint());

      if (tuple.getPoint().equals(end)) {
        return tuple.getPath();
      }

      for (MapPoint neigh : getPassableNeighbours(isPassable, tuple.getPoint())) {

        if (!visited.contains(neigh)) {

          double costToNeigh = tuple.getCostFromStart() + tuple.getPoint().distance(neigh);
          double estTotal = costToNeigh + estimate(neigh, end);
          List<MapPoint> neighPath = new ArrayList<>(tuple.getPath());
          neighPath.add(neigh);
          fringe.add(
              new AStarNode(neigh, tuple.getPoint(), costToNeigh, estTotal, neighPath));
        }
      }

    }

    return Collections.emptyList();
  }



  /**
   * Returns the neighbouring MapPoints of current MapPoint. current is achieved by hardcoding the
   * neighbours in a list and returning that list.
   *
   * @return the list of neighbours
   */
  private static Set<MapPoint> getPassableNeighbours(Function<MapPoint, Boolean> isPassable, MapPoint current) {
    Set<MapPoint> passableNeighbours = new HashSet<MapPoint>(){
      /**
       * TODO javadoc.
       */
      public boolean addOnlyIfPasssable(Function<MapPoint, Boolean> isPassable, MapPoint toAdd) {
        if(isPassable.apply(toAdd)) {
          return this.add(toAdd);
        }
        return false;
      }
    };

    //store the four adjacent sides to the current mapPoint in an array first
    MapPoint[] sides = new MapPoint[]{
        new MapPoint(current.x - 1, current.y), //left
        new MapPoint(current.x + 1, current.y), //right
        new MapPoint(current.x, current.y - 1), //top
        new MapPoint(current.x, current.y + 1) //bottom
    };

    for(MapPoint side : sides) {
      if(isPassable.apply(side)) {
        passableNeighbours.add(side);
      }
    }


//            new MapPoint(current.x - 1, current.y - 1), //top-left
//            new MapPoint(current.x + 1, current.y - 1), //top-right
//            new MapPoint(current.x - 1, current.y + 1), //bottom-left
//            new MapPoint(current.x + 1, current.y + 1) //bottom-right
//        ));

    return passableNeighbours;
  }

  private static double estimate(MapPoint current, MapPoint goal) {
    return current.distance(goal);
  }
}
