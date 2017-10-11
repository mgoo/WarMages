package main.common.util;

import java.awt.Polygon;
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

  final Polygon shape;

  public MapPolygon(MapPoint... points) {
    OptionalDouble avgX = Arrays.stream(points).mapToDouble(mp -> mp.x).average();
    OptionalDouble avgY = Arrays.stream(points).mapToDouble(mp -> mp.y).average();
    assert avgX.isPresent()
        : "There was a problem finding the average of x have you passed atleast one point in";
    assert avgY.isPresent()
        : "There was a problem finding the average of y have you passed atleast one point in";
    MapPoint middle = new MapPoint(avgX.getAsDouble(), avgY.getAsDouble());

    List<MapPoint> orderedPoints = Arrays.asList(points);
    orderedPoints.sort(Comparator.comparingDouble(o -> middle.angleTo(o)));

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
}
