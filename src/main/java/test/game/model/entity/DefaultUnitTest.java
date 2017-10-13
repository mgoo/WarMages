package test.game.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static test.game.model.world.WorldTestUtils.createDefaultHeroUnit;
import static test.game.model.world.WorldTestUtils.createDefaultUnit;
import static test.game.model.world.WorldTestUtils.createEmptyLevel;
import static test.game.model.world.WorldTestUtils.createLevels;
import static test.game.model.world.WorldTestUtils.createWorld;

import java.util.Arrays;
import java.util.List;
import main.common.entity.Unit;
import main.game.model.DefaultGameModel;
import main.common.util.MapPoint;
import main.game.model.world.World;
import org.junit.Test;

/**
 * Tests for DefaultUnits.
 * @author paladogabr
 */
public class DefaultUnitTest {
  //note that CreateDefaultUnit creates a Spearman who travels with 0.1 travel speed

  private Unit getUnit() {
    return createDefaultUnit(new MapPoint(1, 1));
  }

  private List<MapPoint> getPathDown() {
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

  private List<MapPoint> getPathAcross() {
    return Arrays.asList(
        new MapPoint(2, 1),
        new MapPoint(3, 1),
        new MapPoint(4, 1),
        new MapPoint(5, 1),
        new MapPoint(6, 1),
        new MapPoint(7, 1),
        new MapPoint(8, 1),
        new MapPoint(9, 1
        )
    );
  }

  private List<MapPoint> getPathDiagonal() {
    return Arrays.asList(
        new MapPoint(2, 2),
        new MapPoint(3, 3),
        new MapPoint(4, 4),
        new MapPoint(5, 5),
        new MapPoint(6, 6),
        new MapPoint(7, 7),
        new MapPoint(8, 8),
        new MapPoint(9, 9)
    );
  }

  @Test
  public void testMovingEntity_oneSpaceHorizontally() {
    Unit unit = getUnit();
    List<MapPoint> path = getPathAcross();
    unit.setPath(path);
    unit.translatePosition(-unit.getSize().width / 2, -unit.getSize().height / 2);
    World world = createWorld(createLevels(createEmptyLevel()), createDefaultHeroUnit());
    // speed = 0.1, delay = 50
    for (int i = 0; i < 10; i++) {
      MapPoint mapPoint = new MapPoint(1D + Math.min(0.1 * i, 1), 1);
      assertEquals(mapPoint.x, unit.getCentre().x, 0.001);
      assertEquals(mapPoint.y, unit.getCentre().y, 0.001);
      unit.tick(DefaultGameModel.DELAY, world);
    }
  }

  @Test
  public void testMovingEntity_twoSpacesHorizontally() {
    Unit unit = getUnit();
    List<MapPoint> path = getPathAcross();
    unit.setPath(path);
    World world = createWorld(createLevels(createEmptyLevel()), createDefaultHeroUnit());
    unit.translatePosition(-unit.getSize().width / 2, -unit.getSize().height / 2);
    for (int i = 0; i < 20; i++) {
      MapPoint mapPoint = new MapPoint(1D + 0.1 * i, 1);
      assertEquals(mapPoint.x, unit.getCentre().x, 0.001);
      assertEquals(mapPoint.y, unit.getCentre().y, 0.001);
      unit.tick(DefaultGameModel.DELAY, world);
    }
  }

  @Test
  public void testMovingEntity_oneSpaceVertically() {
    Unit unit = getUnit();
    List<MapPoint> path = getPathDown();
    unit.setPath(path);
    World world = createWorld(createLevels(createEmptyLevel()), createDefaultHeroUnit());
    unit.translatePosition(-unit.getSize().width / 2, -unit.getSize().height / 2);
    for (int i = 0; i < 10; i++) {
      MapPoint mapPoint = new MapPoint(1, 1D + 0.1 * i);
      assertEquals(mapPoint.x, unit.getCentre().x, 0.001);
      assertEquals(mapPoint.y, unit.getCentre().y, 0.001);
      unit.tick(DefaultGameModel.DELAY, world);
    }
  }

  @Test
  public void testMovingEntity_twoSpacesVertically() {
    Unit unit = getUnit();
    List<MapPoint> path = getPathDown();
    unit.setPath(path);
    World world = createWorld(createLevels(createEmptyLevel()), createDefaultHeroUnit());
    unit.translatePosition(-unit.getSize().width / 2, -unit.getSize().height / 2);
    for (int i = 0; i < 20; i++) {
      MapPoint mapPoint = new MapPoint(1, 1D + 0.1 * i);
      assertEquals(mapPoint.x, unit.getCentre().x, 0.001);
      assertEquals(mapPoint.y, unit.getCentre().y, 0.001);
      unit.tick(DefaultGameModel.DELAY, world);
    }
  }

  @Test
  public void testMovingEntity_oneSpaceDiagonally() {
    Unit unit = getUnit();
    List<MapPoint> path = getPathDiagonal();
    unit.setPath(path);
    World world = createWorld(createLevels(createEmptyLevel()), createDefaultHeroUnit());
    // with the given speed of 0.01, with each tick (time since last being 200)
    // the movable entity should move 100*0.01 = 1 map distance which is approx 1 diagonal
    for (int i = 0; i < 10; i++) {
      assertEquals(1D + 0.1 / Math.sqrt(2) * i, unit.getTopLeft().x, 0.001);
      assertEquals(1D + 0.1 / Math.sqrt(2) * i, unit.getTopLeft().y, 0.001);
      unit.tick(DefaultGameModel.DELAY, world);
    }
  }
}
