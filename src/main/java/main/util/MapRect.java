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
   * @param cornerA Some corner (not necessarily the top left).
   * @param cornerB Some corner (not necessarily the bottom right).
   */
  public MapRect(MapPoint cornerA, MapPoint cornerB) {
    double minX = Math.min(cornerA.x, cornerB.x);
    double maxX = Math.max(cornerA.x, cornerB.x);
    double minY = Math.min(cornerA.y, cornerB.y);
    double maxY = Math.max(cornerA.y, cornerB.y);

    this.topLeft = new MapPoint(minX, minY);
    this.bottomRight = new MapPoint(maxX, maxY);
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
}
