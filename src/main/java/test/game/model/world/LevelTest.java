package test.game.model.world;

import static test.game.model.world.WorldTestUtils.createHeroUnit;
import static test.game.model.world.WorldTestUtils.createLevelWith;
import static test.game.model.world.WorldTestUtils.createLevels;
import static test.game.model.world.WorldTestUtils.createStubItem;
import static test.game.model.world.WorldTestUtils.createStubMapEntity;
import static test.game.model.world.WorldTestUtils.createUnit;
import static test.game.model.world.WorldTestUtils.createWorld;

import main.game.model.EntityOutOfBoundsException;
import main.game.model.OverlappingMapEntitiesException;
import main.game.model.entity.HeroUnit;
import main.game.model.entity.MapEntity;
import main.game.model.entity.Unit;
import main.game.model.entity.usable.Item;
import main.common.util.MapPoint;
import org.junit.Test;

public class LevelTest {

  private HeroUnit heroUnit = createHeroUnit();

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
    Unit unit = createUnit(new MapPoint(-200, 0));
    createLevelWith(unit);
  }

  @Test(expected = EntityOutOfBoundsException.class)
  public void ensureNoUnitOutOfBoundsRight() {
    Unit unit = createUnit(new MapPoint(200, 0));
    createLevelWith(unit);
  }

  @Test(expected = EntityOutOfBoundsException.class)
  public void ensureNoUnitOutOfBoundsUp() {
    Unit unit = createUnit(new MapPoint(0, -200));
    createLevelWith(unit);
  }

  @Test(expected = EntityOutOfBoundsException.class)
  public void ensureNoUnitOutOfBoundsDown() {
    Unit unit = createUnit(new MapPoint(0, 200));
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
