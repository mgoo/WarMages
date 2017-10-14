package test.game.model.world;

import static test.game.model.world.WorldTestUtils.createDefaultHeroUnit;
import static test.game.model.world.WorldTestUtils.createDefaultUnit;
import static test.game.model.world.WorldTestUtils.createLevelWith;
import static test.game.model.world.WorldTestUtils.createLevels;
import static test.game.model.world.WorldTestUtils.createStubItem;
import static test.game.model.world.WorldTestUtils.createStubMapEntity;
import static test.game.model.world.WorldTestUtils.createWorld;

import main.common.entity.HeroUnit;
import main.common.entity.MapEntity;
import main.common.entity.Unit;
import main.common.exceptions.EntityOutOfBoundsException;
import main.common.exceptions.OverlappingMapEntitiesException;
import main.common.entity.usable.Item;
import main.common.util.MapPoint;
import org.junit.Test;

/**
 * Some tests for {@link Unit}.
 * @author chongdyla (External Tester)
 */
public class LevelTest {

  private HeroUnit heroUnit = createDefaultHeroUnit();

  @Test(expected = OverlappingMapEntitiesException.class)
  public void ensureNoMapEntitiesCompletelyOverlap() {
    MapEntity mapEntity = createStubMapEntity(new MapPoint(0, 0));
    MapEntity mapEntity2 = createStubMapEntity(new MapPoint(0, 0));
    createLevelWith(mapEntity, mapEntity2);
  }

  @Test(expected = OverlappingMapEntitiesException.class)
  public void ensureNoMapEntitiesPartiallyOverlap() {
    MapEntity mapEntity = createStubMapEntity(new MapPoint(0, 0));
    MapEntity mapEntity2 = createStubMapEntity(new MapPoint(0.999, 0.999));
    createLevelWith(mapEntity, mapEntity2);
  }

  @Test(expected = EntityOutOfBoundsException.class)
  public void ensureNoUnitOutOfBoundsLeft() {
    Unit unit = createDefaultUnit(new MapPoint(-10001, 0));
    createLevelWith(unit);
  }

  @Test(expected = EntityOutOfBoundsException.class)
  public void ensureNoUnitOutOfBoundsRight() {
    Unit unit = createDefaultUnit(new MapPoint(10001, 0));
    createLevelWith(unit);
  }

  @Test(expected = EntityOutOfBoundsException.class)
  public void ensureNoUnitOutOfBoundsUp() {
    Unit unit = createDefaultUnit(new MapPoint(0, -10001));
    createLevelWith(unit);
  }

  @Test(expected = EntityOutOfBoundsException.class)
  public void ensureNoUnitOutOfBoundsDown() {
    Unit unit = createDefaultUnit(new MapPoint(0, 10001));
    createLevelWith(unit);
  }

  @Test(expected = EntityOutOfBoundsException.class)
  public void ensureNoItemOutOfBoundsLeft() {
    Item item = createStubItem(new MapPoint(-200, 0));
    createWorld(createLevels(createLevelWith(item)), heroUnit);
  }

  @Test(expected = EntityOutOfBoundsException.class)
  public void ensureNoItemOutOfBoundsUp() {
    Item item = createStubItem(new MapPoint(0, -200));
    createWorld(createLevels(createLevelWith(item)), heroUnit);
  }

  @Test(expected = EntityOutOfBoundsException.class)
  public void ensureNoItemOutOfBoundsDown() {
    Item item = createStubItem(new MapPoint(0, 200));
    createWorld(createLevels(createLevelWith(item)), heroUnit);
  }

  @Test(expected = EntityOutOfBoundsException.class)
  public void ensureNoMapEntityOutOfBoundsLeft() {
    MapEntity mapEntity = createStubMapEntity(new MapPoint(-200, 0));
    createWorld(createLevels(createLevelWith(mapEntity)), heroUnit);
  }

  @Test(expected = EntityOutOfBoundsException.class)
  public void ensureNoMapEntityOutOfBoundsRight() {
    MapEntity mapEntity = createStubMapEntity(new MapPoint(200, 0));
    createWorld(createLevels(createLevelWith(mapEntity)), heroUnit);
  }

  @Test(expected = EntityOutOfBoundsException.class)
  public void ensureNoMapEntityOutOfBoundsUp() {
    MapEntity mapEntity = createStubMapEntity(new MapPoint(0, -200));
    createWorld(createLevels(createLevelWith(mapEntity)), heroUnit);
  }

  @Test(expected = EntityOutOfBoundsException.class)
  public void ensureNoMapEntityOutOfBoundsDown() {
    MapEntity mapEntity = createStubMapEntity(new MapPoint(0, -200));
    createWorld(createLevels(createLevelWith(mapEntity)), heroUnit);
  }
}
