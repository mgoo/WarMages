package main.game.model.world.pathfinder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import main.common.entity.PathFinder;
import main.common.util.MapPoint;

/**
 * Implements the A* path finding algorithm to find the shortest path between two places on the map
 * (as a list of {@link MapPoint}). Ignores other units/entities and a one-tile is passable by all
 * units (they can go through it).
 *
 * @author Hrshikesh Arora
 */
public class DefaultPathFinder implements PathFinder, Serializable {

  private static final long serialVersionUID = 1L;

  private static final int SEARCH_LIMIT = 200;

  /**
   * Uses the A* path finding algorithm to find the shortest path from a start point to an end point
   * on the world returning a list of points along current path.
   *
   * @param isPassable a function that determines whether a given point is passable or not
   * @param start the start point of the path
   * @param end the end/goal point of the path
   * @return a list of points representing the shortest path
   */
  public List<MapPoint> findPath(
      Function<MapPoint, Boolean> isPassable, MapPoint start, MapPoint end
  ) {
    MapPoint endUnrounded = end;

    start = start.rounded();
    end = end.rounded();

    PriorityQueue<AStarNode> fringe = new PriorityQueue<>();
    fringe.add(new AStarNode(start, null, 0, estimate(start, end)));

    Set<MapPoint> visited = new HashSet<>();

    AStarNode bestPath = null;

    while (!fringe.isEmpty()) {
      AStarNode tuple = fringe.poll();

      if (bestPath == null || tuple.getEstimateToGoal() < bestPath.getEstimateToGoal()) {
        bestPath = tuple;
      }

      //stop finding a path if we have explored too many nodes
      if (tuple.getCostFromStart() > start.distanceTo(end) * 3
          && tuple.getCostFromStart() > SEARCH_LIMIT) {
        return bestPath.getPath();
      }

      if (visited.contains(tuple.getPoint())) {
        continue;
      }

      visited.add(tuple.getPoint());


      if (tuple.getPoint().equals(end)) {
        List<MapPoint> path = tuple.getPath();

        if (!path.isEmpty()) {
          path.remove(path.size() - 1);
          path.add(endUnrounded);
        }
        return path;
      }

      for (MapPoint neigh : getPassableNeighbours(isPassable, tuple.getPoint())) {
        if (!visited.contains(neigh)) {
          double costToNeigh = tuple.getCostFromStart() + tuple.getPoint().distanceTo(neigh);
          double estTotal = costToNeigh + estimate(neigh, end);
          List<MapPoint> neighPath = new ArrayList<>(tuple.getPath());
          neighPath.add(neigh);
          fringe.add(
              new AStarNode(neigh, tuple.getPoint(), costToNeigh, estTotal, neighPath));
        }
      }

    }

    return (bestPath != null) ? bestPath.getPath() : Collections.emptyList();
  }


  /**
   * Returns the PASSABLE neighbouring MapPoints of given MapPoint. Checks the sides of the point
   * (left,right,up,bottom) and if they are passable, add them to the set. For the corners, we
   * ensure that atleast one of the two sides adjacent to the corner must be passable in addition to
   * the corner being passable.
   *
   * @return the set of passable neighbours
   */
  private static Set<MapPoint> getPassableNeighbours(
      Function<MapPoint, Boolean> isPassable, MapPoint current
  ) {
    Set<MapPoint> passableNeighbours = new HashSet<>(current.getSides());

    MapPoint[] corners = new MapPoint[]{
        new MapPoint(current.x - 1, current.y - 1), //top-left
        new MapPoint(current.x + 1, current.y - 1), //top-right
        new MapPoint(current.x - 1, current.y + 1), //bottom-left
        new MapPoint(current.x + 1, current.y + 1) //bottom-right
    };

    //note: only add the corners if atleast one of the adjacent sides to the corner is passable

    //check top-left corner
    if (isPassable.apply(corners[0].getRight()) || isPassable.apply(corners[0].getBottom())) {
      passableNeighbours.add(corners[0]);
    }

    //check top-right corner
    if (isPassable.apply(corners[1].getLeft()) || isPassable.apply(corners[1].getBottom())) {
      passableNeighbours.add(corners[1]);
    }

    //check bottom-left corner
    if (isPassable.apply(corners[2].getRight()) || isPassable.apply(corners[2].getTop())) {
      passableNeighbours.add(corners[2]);
    }

    //check bottom-right corner
    if (isPassable.apply(corners[3].getLeft()) || isPassable.apply(corners[3].getTop())) {
      passableNeighbours.add(corners[3]);
    }

    return passableNeighbours.stream().filter(isPassable::apply).collect(Collectors.toSet());
  }

  private static double estimate(MapPoint current, MapPoint goal) {
    return current.distanceTo(goal);
  }

  /**
   * Represents a Node in the A* path finding algorithm. Each node stores the following info:
   * <ul><li>Current point</li><li>How did we get to this point? I.e. parent point</li><li>Cost from
   * start</li> <li>Estimated cost to the goal</li><li>The path taken to get to this node</li></ul>
   */
  public class AStarNode implements Comparable<AStarNode> {

    private final MapPoint currentPoint;
    private final MapPoint from;
    private final double costFromStart;
    private final double totalCost;
    private List<MapPoint> pathTaken;

    /**
     * Constructor for this AStarNode class.
     *
     * @param currentPoint -- the point that this AStarNode is at
     * @param from -- the point that this node came from
     * @param costFromStart -- the cost to get up to this node
     * @param totalCost -- the total estimated cost to the goal
     */
    public AStarNode(MapPoint currentPoint, MapPoint from, double costFromStart, double totalCost) {
      this.currentPoint = currentPoint;
      this.from = from;
      this.costFromStart = costFromStart;
      this.totalCost = totalCost;
      this.pathTaken = new ArrayList<>();
    }

    public AStarNode(
        MapPoint currentPoint, MapPoint from, double costFromStart, double totalCost,
        List<MapPoint> pathTaken
    ) {
      this(currentPoint, from, costFromStart, totalCost);
      this.pathTaken = pathTaken;
    }

    @Override
    public int compareTo(AStarNode other) {
      return Double.compare(this.totalCost, other.totalCost);
    }

    public MapPoint getPoint() {
      return currentPoint;
    }

    public double getCostFromStart() {
      return costFromStart;
    }

    public double getEstimateToGoal() {
      return totalCost - costFromStart;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      AStarNode aStarNode = (AStarNode) o;

      return (Double.compare(aStarNode.costFromStart, costFromStart) == 0)
          && (Double.compare(aStarNode.totalCost, totalCost) == 0)
          && currentPoint.equals(aStarNode.currentPoint)
          && from.equals(aStarNode.from)
          && pathTaken.equals(aStarNode.pathTaken);
    }

    @Override
    public int hashCode() {
      int result;
      long temp;
      result = currentPoint.hashCode();
      result = 31 * result + from.hashCode();
      temp = Double.doubleToLongBits(costFromStart);
      result = 31 * result + (int) (temp ^ (temp >>> 32));
      temp = Double.doubleToLongBits(totalCost);
      result = 31 * result + (int) (temp ^ (temp >>> 32));
      result = 31 * result + pathTaken.hashCode();
      return result;
    }

    public List<MapPoint> getPath() {
      return pathTaken;
    }

    @Override
    public String toString() {
      return "[point: " + currentPoint.toString() + " cost:" + totalCost + "]";
    }
  }
}
