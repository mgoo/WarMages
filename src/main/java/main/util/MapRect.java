package main.util;

public class MapRect {
  public final MapPoint topLeft, bottomRight;

  public MapRect(MapPoint topLeft, MapPoint bottomRight) {
    this.topLeft = topLeft;
    this.bottomRight = bottomRight;
  }
}
