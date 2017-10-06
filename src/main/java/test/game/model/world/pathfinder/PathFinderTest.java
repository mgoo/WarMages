package test.game.model.world.pathfinder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import main.game.model.world.pathfinder.PathFinder;
import main.util.MapPoint;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests for the Pathfinder API.
 *
 * @author Hrshikesh Arora
 */
public class PathFinderTest {

  Function<MapPoint, Boolean> allwaysPassable = mapPoint -> true;

  @Test
  public void test01_testHorizontalPath() {
    Function<MapPoint, Boolean> isPassable = mapPoint -> {
      return true;
    };

    List<MapPoint> actual = PathFinder.findPath(isPassable, mp(1, 1), mp(3, 1));
    List<MapPoint> expected = new ArrayList<>(Arrays.asList(mp(2, 1), mp(3, 1)));

    assertEquals(expected, actual);
  }

  @Test
  public void test02_testVerticalPath() {
    Function<MapPoint, Boolean> isPassable = mapPoint -> {
      return true;
    };

    List<MapPoint> actual = PathFinder.findPath(isPassable, mp(1, 1), mp(1, 3));
    List<MapPoint> expected = new ArrayList<>(Arrays.asList(mp(1, 2), mp(1, 3)));

    assertEquals(expected, actual);
  }

  @Test
  public void test03_testDiagonalPath() {
    Function<MapPoint, Boolean> isPassable = mapPoint -> {
      return true;
    };

    List<MapPoint> actual = PathFinder.findPath(isPassable, mp(1, 1), mp(3, 3));
    List<MapPoint> expected = new ArrayList<>(Arrays.asList(mp(2, 2), mp(3, 3)));

    assertEquals(expected, actual);
  }

  @Test
  public void test04_testDiagonalPath() {
    Function<MapPoint, Boolean> isPassable = mapPoint -> {
      return true;
    };

    List<MapPoint> actual = PathFinder.findPath(isPassable, mp(1, 1), mp(5, 5));
    List<MapPoint> expected = new ArrayList<>(
        Arrays.asList(mp(2, 2), mp(3, 3), mp(4, 4), mp(5, 5)));

    assertEquals(expected, actual);
  }

  @Test
  public void test06_testPathWithObstacle() {
    Function<MapPoint, Boolean> isPassable = mapPoint -> {
      if (mapPoint.y == 2 && (mapPoint.x == 2 || mapPoint.x == 3 || mapPoint.x == 4)) {
        return false;
      }
      if (mapPoint.y == 1 && mapPoint.x == 4) {
        return false;
      }
      return true;
    };

    //  12345
    //0 +++++
    //1 S++x+
    //2 +xxxG
    //3 +++++

    //S = start; G = goal; x = obstacle; + = free space

    List<MapPoint> actual = PathFinder.findPath(isPassable, mp(1, 1), mp(5, 2));
    List<MapPoint> expected = new ArrayList<>(
        Arrays.asList(mp(2, 1), mp(3, 0), mp(4, 0), mp(5, 1), mp(5, 2)));

    assertEquals(expected, actual);
  }

  @Test
  public void test07_testPathWithObstacle() {
    Function<MapPoint, Boolean> isPassable = mapPoint -> {
      if (mapPoint.y == 2 && (mapPoint.x == 2 || mapPoint.x == 3 || mapPoint.x == 4)) {
        return false;
      }
      if ((mapPoint.y == 1 || mapPoint.y == 0) && mapPoint.x == 4) {
        return false;
      }
      return true;
    };

    //  12345
    //0 +++x+
    //1 S++x+
    //2 +xxxG
    //3 +++++

    //S = start; G = goal; x = obstacle; + = free space

    List<MapPoint> actual = PathFinder.findPath(isPassable, mp(1, 1), mp(5, 2));
    List<MapPoint> expected = new ArrayList<>(
        Arrays.asList(mp(1, 2), mp(2, 3), mp(3, 3), mp(4, 3), mp(5, 2)));

    assertEquals(expected, actual);
  }

  @Test
  public void test08_testPathWhereStartAndEndAreDecimals() {
    Function<MapPoint, Boolean> isPassable = mapPoint -> true;

    MapPoint end = mp(3.45, 1.234);
    List<MapPoint> actual = PathFinder.findPath(isPassable, mp(1.1, 1.1), end);
    List<MapPoint> expected = new ArrayList<>(Arrays.asList(mp(2, 1), end));

    assertEquals(expected, actual);
  }

  @Test
  public void test_endOfPathMatchesEnd() {
    MapPoint start = new MapPoint(2, 3);
    MapPoint end = new MapPoint(50,1);

    List<MapPoint> path = PathFinder.findPath(this.allwaysPassable, start, end);
    assertEquals(end, path.get(path.size() - 1));

    start = new MapPoint(2.20444, -345.21344);
    end = new MapPoint(-30.948957,1.99034857);

    path = PathFinder.findPath(this.allwaysPassable, start, end);
    MapPoint pathEnd = path.get(path.size() - 1);
    assertEquals((int)end.x, (int)pathEnd.x);
    assertEquals((int)end.y, (int)pathEnd.y);
  }

  @Test
  public void test_FindClosestPossiblePath() {
    Function<MapPoint, Boolean> isPassable = mapPoint -> {return mapPoint.x != 3;};

    MapPoint start = new MapPoint(4, 3);
    MapPoint end = new MapPoint(2,1);

    //    List<MapPoint> path = PathFinder.findPath(isPassable, start, end); // TODO so test doesnt hang
    //    assertEquals(1, path.size());

  }

  @Test
  public void test_minimalSteps() {
    // Horizontal
    MapPoint start = new MapPoint(50, 0);
    MapPoint end = new MapPoint(0,0);

    List<MapPoint> path = PathFinder.findPath(this.allwaysPassable, start, end);
    assertEquals(50, path.size());

    // Vertical
    start = new MapPoint(0, 0);
    end = new MapPoint(0,75);

    path = PathFinder.findPath(this.allwaysPassable, start, end);
    assertEquals(75, path.size());

    // Diagonal
    start = new MapPoint(100, 100);
    end = new MapPoint(0,0);

    path = PathFinder.findPath(this.allwaysPassable, start, end);
    assertEquals(100, path.size());

    // Some random angle
    int endX = 123;
    int endY = 282;
    start = new MapPoint(0, 0);
    end = new MapPoint(endX,endY);

    path = PathFinder.findPath(this.allwaysPassable, start, end);
    assertEquals((endX + endY) - Math.min(endX, endY), path.size());
  }


  @Test
  public void test_cannotGoThoughDiagonalWall() {
    Function<MapPoint, Boolean> isPassable = mapPoint -> {
      return mapPoint.x != mapPoint.y || mapPoint.x > 50;
    };

    MapPoint start = new MapPoint(0, 1);
    MapPoint end = new MapPoint(1, 0);

    List<MapPoint> path = PathFinder.findPath(isPassable, start, end);
    assertTrue(path.size() >= 50);
  }

  /**
   * This test will fail see #108
   */
  @Test
  @Ignore
  public void test_reversePathCloseToPath() {

    MapPoint start = new MapPoint(0, 0);
    MapPoint end = new MapPoint(20,10);

    List<MapPoint> path = PathFinder.findPath(this.allwaysPassable, start, end);
    List<MapPoint> reversePath = PathFinder.findPath(this.allwaysPassable, end, start);
    assertEquals(path.size(), reversePath.size());
    // Ignoree the first and last as they are different
    for (int i = 1; i < path.size() - 1; i++) {
            assertEquals(path.get(i).x, reversePath.get(reversePath.size() - (i + 1)).x, 1.001);
            assertEquals(path.get(i).y, reversePath.get(reversePath.size() - (i + 1)).y, 1.001);
    }
  }


  @Test
  public void test09_testImpossiblePathReturnsBestPath() {
    Function<MapPoint, Boolean> isPassable = mapPoint -> {
      return mapPoint.equals(mp(5, 2)) || !(mapPoint.x >= 4) || !(mapPoint.y >= 1);
    };

    //  123456
    //0 ++++++
    //1 S++xxx
    //2 +++xGx
    //3 +++xxx

    //S = start; G = goal; x = obstacle; + = free space

    List<MapPoint> actual = PathFinder.findPath(isPassable, mp(1, 1), mp(5, 2));

    assertEquals(mp(3,2), actual.get(actual.size() - 1));
  }

  private MapPoint mp(double x, double y) {
    return new MapPoint(x, y);
  }
}
