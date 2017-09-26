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
  public void test01_testStraightPath(){
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

  private MapPoint mp(double x, double y){
    return new MapPoint(x,y);
  }
}
