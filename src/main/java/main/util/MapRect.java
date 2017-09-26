package main.util;

/**
 * Represents a rectangle on the Map.
 */
public class MapRect {

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

  /**
   * Returns true iff the rect is inside this (which includes when this and rect are equal).
   */
  public boolean contains(MapRect rect) {
    return topLeft.x <= rect.topLeft.x &&
        topLeft.y <= rect.topLeft.y &&
        bottomRight.x >= rect.bottomRight.x &&
        bottomRight.y >= rect.bottomRight.y;
  }

  public double getWidth() {
    return bottomRight.x - topLeft.x;
  }

  public double getHeight() {
    return bottomRight.y - topLeft.y;
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
}
