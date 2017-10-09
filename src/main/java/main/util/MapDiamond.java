package main.util;

/**
 * Represents a rotated rectangle.
 * @author Andrew McGhie
 */
public class MapDiamond {

  public final MapPoint left;
  public final MapPoint right;
  public final MapPoint up;
  public final MapPoint down;

  public MapDiamond(MapPoint... points) {
    assert points.length == 4 : "This constructor takes 4 elements";
    MapPoint tempLeft = null;
    MapPoint tempRight = null;
    MapPoint tempUp = null;
    MapPoint tempDown = null;
    for (MapPoint point : points) {
      if (tempLeft == null || point.x < tempLeft.x) {
        tempLeft = point;
      }
      if (tempRight == null || point.x > tempRight.x) {
        tempRight = point;
      }
      if (tempUp == null || point.y < tempUp.y) {
        tempUp = point;
      }
      if (tempDown == null || point.y > tempUp.y) {
        tempDown = point;
      }
    }
    this.left = tempLeft;
    this.right = tempRight;
    this.up = tempUp;
    this.down = tempDown;
  }

  /**
   * Checks if the point is contained in the diamond.
   */
  public boolean contains(MapPoint point) {
    if (!(point.x > left.x
        && point.x < right.x
        && point.y > up.y
        && point.y < down.y)) {
      return false;
    }
    // Top left.
    if (point.x < up.x && point.y < left.y) {
      return (up.x - left.x) / (left.y - up.y) < (point.x - left.x) / (left.y - point.y);
    }
    // Top right.
    if (point.x > up.x && point.y < right.y) {
      return (right.x - up.x) / (right.y - up.y) < (right.x - point.x) / (right.y - point.y);
    }
    // Bottom right.
    if (point.x > down.x && point.y > right.y) {
      return (right.x - down.x) / (down.y - right.y) < (right.x - point.x) / (point.y - right.y);
    }
    // Bottom left.
    if (point.x < down.x && point.y > left.y) {
      return (down.x - left.x) / (down.y - left.y) < (point.x - left.x) / (point.y - right.y);
    }
    return true;
  }
}
