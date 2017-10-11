package main.common.util;

import java.awt.Point;
import java.io.Serializable;
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

  /**
   * Floors the x and y coordinates.
   */
  public MapPoint floored() {
    return new MapPoint(
        Math.floor(x),
        Math.floor(y)
    );
  }

  /**
   * Returns a new point where this point is translated by the given amount.
   *
   * @param x -- translation in x
   * @param y -- translation in y
   * @return -- returns the translated point
   */
  public MapPoint translate(double x, double y) {
    return new MapPoint(this.x + x, this.y + y);
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
    return "MapPoint(" + x + ", " + y + ")";
  }

  /**
   * Returns the distance to another MapPoint object. Uses the Math.hypot function to find this
   * distance.
   *
   * @param other -- the other MapPoint
   * @return the distance from this MapPoint to the other MapPoint
   */
  public double distanceTo(MapPoint other) {
    return Math.hypot(this.x - other.x, this.y - other.y);
  }

  /**
   * Gets the angle in radians to the other point from the positive x direction
   *
   * @return radians
   */
  public double angleTo(MapPoint A) {
    MapPoint B = this.getRight();
    MapPoint C = this;
    // Cosign rule for C
    double a = B.distanceTo(C);
    double b = C.distanceTo(A);
    double c = B.distanceTo(A);
    double Cangle = Math.acos(
        (Math.pow(a, 2) + Math.pow(b ,2) - Math.pow(c, 2))
        / (2*(a*b)));
    // make sure the angle is from upwards of the positive horizontal
    if (A.y > C.y) {
      return Math.PI + (Math.PI - Cangle);
    } else {
      return Cangle;
    }
  }

  public List<MapPoint> getSides() {
    return Arrays.asList(getLeft(), getRight(), getTop(), getBottom());
  }

  public MapPoint getLeft() {
    return new MapPoint(this.x - 1, this.y);
  }

  public MapPoint getRight() {
    return new MapPoint(this.x + 1, this.y);
  }

  public MapPoint getTop() {
    return new MapPoint(this.x, this.y - 1);
  }

  public MapPoint getBottom() {
    return new MapPoint(this.x, this.y + 1);
  }
}
