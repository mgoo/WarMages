package main.game.model.world.pathfinder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import main.game.model.world.World;
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
   * on the world returning a list of points along this path.
   *
   * @param world the world to find the path in
   * @param start the start point of the path
   * @param end the end/goal point of the path
   * @return a list of points representing the shortest path
   */
  public static List<MapPoint> findPath(World world, MapPoint start, MapPoint end) {
    PriorityQueue<AStarNode> fringe = new PriorityQueue<>();
    fringe.add(new AStarNode(start, null, 0, estimate(start, end)));

    Set<MapPoint> visited = new HashSet<>();

    while (!fringe.isEmpty()) {
      System.out.println(fringe.size());
      System.out.println("fringe = "+fringe);
      AStarNode tuple = fringe.poll();
      System.out.println("polling lowest size node with total cost "+tuple.getTotalCost());

      if (visited.contains(tuple.getPoint())) {
        continue;
      }

      visited.add(tuple.getPoint());

      if (tuple.getPoint().equals(end)) {
        return tuple.getPath();
      }

      for (MapPoint neigh : tuple.getPoint().getNeighbours()) {

        if (!visited.contains(neigh) && world.isPassable(neigh)) {

          double costToNeigh = tuple.getCostFromStart() + tuple.getPoint().distance(neigh);
          double estTotal = costToNeigh + estimate(neigh, end);
          List<MapPoint> neighPath = new ArrayList<>(tuple.getPath());
          neighPath.add(neigh);
          fringe.add(
              new AStarNode(neigh, tuple.getPoint(), costToNeigh, estTotal, neighPath));
        }
      }

    }
    return new ArrayList<>();
  }

  private static double estimate(MapPoint current, MapPoint goal) {
    return current.distance(goal);
  }
}
