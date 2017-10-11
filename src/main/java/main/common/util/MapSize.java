package main.common.util;

import java.io.Serializable;

/**
 * Represents the size of the Map as width and height.
 */
public class MapSize implements Serializable {

  private static final long serialVersionUID = 1L;

  public final double width;
  public final double height;

  public MapSize(double width, double height) {
    this.width = width;
    this.height = height;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    MapSize mapSize = (MapSize) o;

    if (Double.compare(mapSize.width, width) != 0) {
      return false;
    }
    return Double.compare(mapSize.height, height) == 0;
  }

  @Override
  public String toString() {
    return "MapSize(" + width + ", " + height + ")";
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    temp = Double.doubleToLongBits(width);
    result = (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(height);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }

  public MapSize scaledBy(double scale) {
    return new MapSize(width * scale, height * scale);
  }
}
