package main.game.model.world.pathfinder;

import java.util.ArrayList;
import java.util.List;
import main.util.MapPoint;

public class AStarNode implements Comparable<AStarNode> {

  public final MapPoint currentPoint;
  public final MapPoint from;
  public final double costFromStart;
  public final double totalCost;
  public List<MapPoint> pathTaken;

  public AStarNode(MapPoint currentPoint, MapPoint from, double costFromStart, double totalCost) {
    this.currentPoint = currentPoint;
    this.from = from;
    this.costFromStart = costFromStart;
    this.totalCost = totalCost;
    this.pathTaken = new ArrayList<>();
  }

  public AStarNode(MapPoint currentPoint, MapPoint from, double costFromStart, double totalCost, List<MapPoint> pathTaken) {
    this(currentPoint, from, costFromStart, totalCost);
    this.pathTaken = pathTaken;
  }

//TODO Hrshikesh - remove or include?
//  public Map getPrevPoint(){
//    if(pathTaken.isEmpty()) return null;
//    return pathTaken.get(pathTaken.size()-1);
//  }

  @Override
  public int compareTo(AStarNode other) {
    if (this.totalCost < other.totalCost)
      return -1;
    if (this.totalCost > other.totalCost)
      return 1;
    return 0;
  }

}
