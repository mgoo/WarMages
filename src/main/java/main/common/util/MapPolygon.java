package main.common.util;

import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.IntStream;

/**
 * Represents a rotated rectangle.
 * @author Andrew McGhie
 */
public class MapPolygon {

  private final Polygon shape;
  private final MapPoint center;

  public MapPolygon(MapPoint... points) {
    OptionalDouble avgX = Arrays.stream(points).mapToDouble(mp -> mp.x).average();
    OptionalDouble avgY = Arrays.stream(points).mapToDouble(mp -> mp.y).average();
    assert avgX.isPresent()
        : "There was a problem finding the average of x have you passed atleast one point in";
    assert avgY.isPresent()
        : "There was a problem finding the average of y have you passed atleast one point in";
    this.center = new MapPoint(avgX.getAsDouble(), avgY.getAsDouble());

    List<MapPoint> orderedPoints = Arrays.asList(points);
    orderedPoints.sort(Comparator.comparingDouble(this.center::angleTo));

    int[] x = new int[points.length];
    int[] y = new int[points.length];

    IntStream.range(0, orderedPoints.size()).forEach(i -> {
      x[i] = (int)orderedPoints.get(i).x;
      y[i] = (int)orderedPoints.get(i).y;
    });
    shape = new Polygon(x, y, points.length);
  }

  /**
   * Checks if the point is contained in the diamond.
   */
  public boolean contains(MapPoint point) {
    return this.shape.contains(point.x, point.y);
  }

  /**
   * Checks if the mapRect is in the polygon.
   * This is not the same as contains as it will pick up any part being in not just if
   * the entire shape is in.
   */
  public boolean contains(MapRect mapRect) {
    Rectangle2D rect = new java.awt.geom.Rectangle2D.Double(mapRect.x(),
        mapRect.y(),
        mapRect.getWidth(),
        mapRect.getHeight());
    return this.shape.contains(rect) // If unit is in shape
        || this.shape.intersects(rect) // If shape intersects unit bounding box
        || rect.contains(center.x, center.y); // catch if shape is completely contained in unit
  }
}
