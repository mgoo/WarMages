package main.game.model.world.pathfinder;

import java.util.ArrayList;
import java.util.List;
import main.util.MapPoint;

/**
 * Represents a Node in the A* path finding algorithm. Each node stores the following information: -
 * Current point - How did we get to this point? I.e. parent point - Cost from start - Estimated
 * cost to the goal - The path taken to get to this node
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
