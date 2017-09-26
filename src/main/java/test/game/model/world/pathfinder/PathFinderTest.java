package test.game.model.world.pathfinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import main.game.model.entity.HeroUnit;
import main.game.model.world.World;
import main.game.model.world.pathfinder.PathFinder;
import main.util.MapPoint;
import org.junit.Test;
import static org.junit.Assert.*;

public class PathFinderTest {

  @Test
  public void test01_testHorizontalPath(){
    World world = new World(null, null) {
      @Override
      public boolean isPassable(MapPoint point) {
        return true;
      }
    };

    List<MapPoint> actual = PathFinder.findPath(world, mp(1,1), mp(3,1));
    List<MapPoint> expected = new ArrayList<>(Arrays.asList(mp(1,1), mp(2,1), mp(3,1)));

    assertEquals(expected, actual);
  }

  @Test
  public void test02_testVerticalPath(){
    World world = new World(null, null) {
      @Override
      public boolean isPassable(MapPoint point) {
        return true;
      }
    };

    List<MapPoint> actual = PathFinder.findPath(world, mp(1,1), mp(1,3));
    List<MapPoint> expected = new ArrayList<>(Arrays.asList(mp(1,1), mp(1,2), mp(1,3)));

    assertEquals(expected, actual);
  }

  @Test
  public void test03_testDiagonalPath(){
    World world = new World(null, null) {
      @Override
      public boolean isPassable(MapPoint point) {
        return true;
      }
    };

    List<MapPoint> actual = PathFinder.findPath(world, mp(1,1), mp(3,3));
    List<MapPoint> expected = new ArrayList<>(Arrays.asList(mp(1,1), mp(2,2), mp(3,3)));

    assertEquals(expected, actual);
  }

  @Test
  public void test04_testDiagonalPath(){
    World world = new World(null, null) {
      @Override
      public boolean isPassable(MapPoint point) {
        return true;
      }
    };

    List<MapPoint> actual = PathFinder.findPath(world, mp(1,1), mp(5,5));
    List<MapPoint> expected = new ArrayList<>(Arrays.asList(mp(1,1), mp(2,2), mp(3,3), mp(4,4), mp(5,5)));

    assertEquals(expected, actual);
  }

  @Test
  public void test05_testPathWithObstacle(){
    World world = new World(null, null) {
      @Override
      public boolean isPassable(MapPoint point) {
        if(point.y == 2 && (point.x == 2 || point.x == 3 || point.x == 4)) {
          return false;
        }
        return true;
      }
    };

    //  12345
    //1 S++++
    //2 +xxxG

    //S = start; G = goal; x = obstacle; + = free space

    List<MapPoint> actual = PathFinder.findPath(world, mp(1,1), mp(5,2));
    List<MapPoint> expected = new ArrayList<>(Arrays.asList(mp(1,1), mp(2,1), mp(3,1), mp(4,1), mp(5,1), mp(5,2)));

    assertEquals(expected, actual);
  }

  private MapPoint mp(double x, double y){
    return new MapPoint(x,y);
  }
}
