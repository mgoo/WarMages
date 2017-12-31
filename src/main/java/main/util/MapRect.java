package main.util;

import java.awt.geom.Rectangle2D;
import java.io.Serializable;

/**
 * Represents a rectangle on the Map.
 */
public class MapRect implements Serializable {

  private static final long serialVersionUID = 1L;

  public final MapPoint topLeft;
  public final MapPoint bottomRight;

  /**
   * Creates a {@link MapRect} without worrying about the order of the corners.
   *
   * @param aCorner Some corner (not necessarily the top left).
   * @param oppositeCorner Some corner (not necessarily the bottom right).
   */
  public MapRect(MapPoint aCorner, MapPoint oppositeCorner) {
    double minX = Math.min(aCorner.x, oppositeCorner.x);
    double maxX = Math.max(aCorner.x, oppositeCorner.x);
    double minY = Math.min(aCorner.y, oppositeCorner.y);
    double maxY = Math.max(aCorner.y, oppositeCorner.y);

    this.topLeft = new MapPoint(minX, minY);
    this.bottomRight = new MapPoint(maxX, maxY);
  }

  public MapRect(MapPoint topLeft, MapSize size) {
    this(topLeft, new MapPoint(topLeft.x + size.width, topLeft.y + size.height));
  }

  public MapRect(double x, double y, double width, double height) {
    this(new MapPoint(x, y), new MapPoint(x + width, y + height));
  }

  /**
   * Returns true iff the rect is inside this (which includes when this and rect are equal).
   */
  public boolean contains(MapRect rect) {
    return topLeft.x <= rect.topLeft.x
        && topLeft.y <= rect.topLeft.y
        && bottomRight.x >= rect.bottomRight.x
        && bottomRight.y >= rect.bottomRight.y;
  }

  /**
   * Checks whether a point is inside this MapRect.
   * @param mapPoint point to check
   * @return returns true if point is within rect. False otherwise.
   */
  public boolean contains(MapPoint mapPoint) {
    return mapPoint.x >= topLeft.x
        && mapPoint.x <= bottomRight.x
        && mapPoint.y >= topLeft.y
        && mapPoint.y <= bottomRight.y;
  }

  /**
   * Checks whether a point is inside this MapRect.
   * @param x the x coordinate
   * @param y the y coordinate
   */
  public boolean contains(double x, double y) {
    return x >= topLeft.x
        && x <= bottomRight.x
        && y >= topLeft.y
        && y <= bottomRight.y;
  }

  /**
   * The point in the exact centre of this rectangle. The coordinates, of course, may not be
   * rounded.
   */
  public MapPoint getCenter() {
    return new MapPoint(
        getWidth() / 2 + topLeft.x,
        getHeight() / 2 + topLeft.y
    );
  }

  public double x() {
    return this.topLeft.x;
  }

  public double y() {
    return this.topLeft.y;
  }

  public double getWidth() {
    return bottomRight.x - topLeft.x;
  }

  public double getHeight() {
    return bottomRight.y - topLeft.y;
  }

  public MapRect move(double x, double y) {
    return new MapRect(this.topLeft.x + x, this.topLeft.y + y, this.getWidth(), this.getHeight());
  }

  public boolean overlapsWith(MapRect mapRect) {
    return toAwtRectangle().intersects(mapRect.toAwtRectangle());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    MapRect mapRect = (MapRect) o;

    if (topLeft != null ? !topLeft.equals(mapRect.topLeft) : mapRect.topLeft != null) {
      return false;
    }
    return bottomRight != null ? bottomRight.equals(mapRect.bottomRight)
        : mapRect.bottomRight == null;
  }

  @Override
  public int hashCode() {
    int result = topLeft != null ? topLeft.hashCode() : 0;
    result = 31 * result + (bottomRight != null ? bottomRight.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return String.format(
        "MapRect(x=%f, y=%f, w=%f, h=%f)",
        topLeft.x,
        topLeft.y,
        getWidth(),
        getHeight()
      );
  }

  private Rectangle2D.Double toAwtRectangle() {
    return new Rectangle2D.Double(
        topLeft.x,
        topLeft.y,
        getWidth(),
        getHeight()
    );
  }
}
