package main.util;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a point on the Map.
 */
public class MapPoint implements Serializable {

  private static final long serialVersionUID = 1L;

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

  /**
   * Rounds x and y.
   */
  public MapPoint rounded() {
    return new MapPoint(
        Math.round(x),
        Math.round(y)
    );
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    MapPoint mapPoint = (MapPoint) o;

    if (Double.compare(mapPoint.x, x) != 0) {
      return false;
    }
    return Double.compare(mapPoint.y, y) == 0;
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    temp = Double.doubleToLongBits(x);
    result = (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(y);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }

  @Override
  public String toString() {
    return "(" + x + ", " + y + ")";
  }

  /**
   * Returns the distance to another MapPoint object. Uses the Math.hypot function to find this
   * distance.
   *
   * @param other -- the other MapPoint
   * @return the distance from this MapPoint to the other MapPoint
   */
  public double distance(MapPoint other) {
    return Math.hypot(this.x - other.x, this.y - other.y);
  }

  /**
   * Returns the neighbouring MapPoints of this MapPoint. This is achieved by hardcoding the
   * neighbours in a list and returning that list.
   *
   * @return the list of neighbours
   */
  public List<MapPoint> getNeighbours() {
    return new ArrayList<MapPoint>(
        Arrays.asList(
            new MapPoint(this.x - 1, this.y), //left
            new MapPoint(this.x + 1, this.y), //right
            new MapPoint(this.x, this.y - 1), //top
            new MapPoint(this.x, this.y + 1), //bottom
            new MapPoint(this.x - 1, this.y - 1), //top-left
            new MapPoint(this.x + 1, this.y - 1), //top-right
            new MapPoint(this.x - 1, this.y + 1), //bottom-left
            new MapPoint(this.x + 1, this.y + 1) //bottom-right
        ));
  }
}
