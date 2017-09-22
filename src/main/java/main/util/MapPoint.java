package main.util;

import java.awt.Point;

/**
 * Represents a point on the Map.
 */
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
}
