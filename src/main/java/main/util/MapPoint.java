package main.util;

import java.awt.Point;
import java.util.List;

public class MapPoint {

  public final double x;
  public final double y;

  public MapPoint(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public MapPoint(Point p) {
    this.x = p.x;
    this.y = p.y;
  }

  public double distance(MapPoint other) {
    //TODO Hrshikesh
    return 0.0;
  }

  public List<MapPoint> getNeighbours(){
    //TODO Hrshikesh
    return null;
  }
}
