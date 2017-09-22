package main.game.model.world.pathfinder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

  public static List<MapPoint> findPath(World world, MapPoint start, MapPoint end) {
    PriorityQueue<AStarNode> fringe = new PriorityQueue<AStarNode>();
    fringe.add(new AStarNode(start, null, 0, estimate(start, end)));

    Set<MapPoint> visited = new HashSet<MapPoint>();

    while(!fringe.isEmpty()){
      AStarNode tuple = fringe.poll();

      if(!visited.contains(tuple.currentPoint)){
        visited.add(tuple.currentPoint);

        if(tuple.currentPoint == end) {
          return tuple.pathTaken;
        }

        for(MapPoint neigh: tuple.currentPoint.getNeighbours()){

          if(!visited.contains(neigh)){
            double costToNeigh = tuple.costFromStart + tuple.currentPoint.distance(neigh);
            double estTotal = costToNeigh + estimate(neigh, end);
            List<MapPoint> neighPath = new ArrayList<MapPoint>(tuple.pathTaken);
            neighPath.add(neigh);
            fringe.add(new AStarNode(neigh, tuple.currentPoint, costToNeigh, estTotal, neighPath));
          }
        }
      }
    }
    return new ArrayList<MapPoint>();
  }

  private static double estimate(MapPoint current, MapPoint goal){
    return current.distance(goal);
  }
}
