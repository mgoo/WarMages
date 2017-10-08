package test.game.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static test.game.model.world.WorldTestUtils.createEmptyLevel;
import static test.game.model.world.WorldTestUtils.createHeroUnit;
import static test.game.model.world.WorldTestUtils.createLevels;
import static test.game.model.world.WorldTestUtils.createWorld;

import java.util.Arrays;
import java.util.List;
import main.game.model.entity.MovableEntity;
import main.game.model.world.World;
import main.util.MapPoint;
import main.util.MapSize;
import org.junit.Test;

public class MovableEntityTest {

  private MovableEntity getMovableEntity() {
    return new MockMovable(new MapPoint(1, 1), new MapSize(1, 1));
  }

  private class MockMovable extends MovableEntity {

    /**
     * Constructor takes the position of the entity and the size.
     *
     * @param position = position of Entity
     * @param size = size of Entity
     */
    public MockMovable(MapPoint position, MapSize size) {
      super(position, size, 0.01);
    }
  }

  private List<MapPoint> getPath() {
    return Arrays.asList(
        new MapPoint(1, 2),
        new MapPoint(1, 3),
        new MapPoint(1, 4),
        new MapPoint(1, 5),
        new MapPoint(1, 6),
        new MapPoint(1, 7),
        new MapPoint(1, 8),
        new MapPoint(1, 9)
    );
  }

  @Test
  public void testMovingEntity() {
    MovableEntity me = getMovableEntity();
    //    MapPoint original = me.getTopLeft();
    List<MapPoint> path = getPath();
    me.setPath(path);
    World world = createWorld(createLevels(createEmptyLevel()), createHeroUnit());
    // with the given speed of 0.01, with each tick (time since last being 100)
    // the movable entity should move 100*0.01 = 1 map distance
    for (int i = 0; i < path.size(); i++) {
      me.tick(100, world);
      assertEquals(path.get(i), me.getTopLeft());
    }
    //    assertNotEquals(original, me.getTopLeft());
  }

}
