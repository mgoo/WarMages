package test.game.model.world.pathfinder;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import main.game.model.world.pathfinder.PathFinder;
import main.util.MapPoint;
import org.junit.Test;

public class PathFinderTest {

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

  private MapPoint mp(double x, double y) {
    return new MapPoint(x, y);
  }
}
