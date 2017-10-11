package main.common.util;

import java.awt.Polygon;
import java.util.stream.IntStream;

/**
 * Represents a rotated rectangle.
 * @author Andrew McGhie
 */
public class MapPolygon {

  final Polygon shape;

  public MapPolygon(MapPoint... points) {
    int[] x = new int[points.length];
    int[] y = new int[points.length];

    IntStream.range(0, points.length).forEach(i -> {
      x[i] = (int)points[i].x;
      y[i] = (int)points[i].y;
    });
    shape = new Polygon(x, y, points.length);
  }

  /**
   * Checks if the point is contained in the diamond.
   */
  public boolean contains(MapPoint point) {
    return this.shape.contains(point.x, point.y);
  }
}
