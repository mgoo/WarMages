package test.util;

import static org.junit.Assert.assertEquals;

import main.common.util.MapPoint;
import org.junit.Test;

/**
 * Tests the MapPoint class.
 *
 * @author Andrew McGhie
 */
public class MapPointTest {

  @Test
  public void testAngleTo() {
    MapPoint center = new MapPoint(1, 1);
    MapPoint up = new MapPoint(1, 0);
    assertEquals(Math.PI / 2, center.angleTo(up), 0.001);
    MapPoint down = new MapPoint(1, 2);
    assertEquals(3 * Math.PI / 2, center.angleTo(down), 0.001);
    MapPoint right = new MapPoint(0, 1);
    assertEquals(Math.PI, center.angleTo(right), 0.001);
    MapPoint left = new MapPoint(2, 1);
    assertEquals(0, center.angleTo(left), 0.001);
  }
}
