package test.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import main.util.MapPoint;
import main.util.MapPolygon;
import org.junit.Test;

/**
 * Created by mgoo on 12/10/17.
 */
public class MapShapeTest {

  @Test
  public void testContains() {
    MapPolygon shape = new MapPolygon(new MapPoint(1D, 1D),
        new MapPoint(-1D,-1D),
        new MapPoint(-1D,  1D),
        new MapPoint(1D, -1D));
    assertTrue(shape.contains(new MapPoint(0, 0)));
    assertTrue(shape.contains(new MapPoint(0.999, 0)));
    assertFalse(shape.contains(new MapPoint(1, 0)));
    assertFalse(shape.contains(new MapPoint(2,2)));
    assertFalse(shape.contains(new MapPoint(1.1,0)));
    assertFalse(shape.contains(new MapPoint(0,1.1)));

  }

}
