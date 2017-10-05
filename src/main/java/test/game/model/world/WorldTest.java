package test.game.model.world;

import static main.images.GameImageResource.GOLDEN_HERO_SPRITE_SHEET;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static test.game.model.world.WorldTestUtils.createDefaultEnemyOrc;
import static test.game.model.world.WorldTestUtils.createStubItem;
import static test.game.model.world.WorldTestUtils.createDefaultPlayerArcher;
import static test.game.model.world.WorldTestUtils.createDefaultPlayerKnight;
import static test.game.model.world.WorldTestUtils.createEmptyLevel;
import static test.game.model.world.WorldTestUtils.createLevelWith;
import static test.game.model.world.WorldTestUtils.createLevels;
import static test.game.model.world.WorldTestUtils.createStubMapEntity;
import static test.game.model.world.WorldTestUtils.createWorld;

import main.game.model.entity.HeroUnit;
import main.game.model.entity.Item;
import main.game.model.entity.MapEntity;
import main.game.model.entity.Unit;
import main.game.model.entity.UnitType;
import main.game.model.world.World;
import main.images.UnitSpriteSheet;
import main.util.MapPoint;
import main.util.MapSize;
import org.junit.Test;

/**
 * Tests all the methods and invariants of the WorldClass.
 */
public class WorldTest {

  private World world = null;
  private HeroUnit heroUnit = new HeroUnit(
      new MapPoint(1, 1),
      new MapSize(1, 1),
      new UnitSpriteSheet(GOLDEN_HERO_SPRITE_SHEET),
      UnitType.SWORDSMAN
  );

  @Test
  public void testGetAllEntitiesHasHeroUnit() {
    world = createWorld(createLevels(createEmptyLevel()), heroUnit);
    assertEquals(1, world.getAllEntities().size());
    assertTrue(world.getAllEntities().contains(heroUnit));
  }

  @Test
  public void testGetAllEntitiesHasOneUnitOneLevel() {
    Unit unit = createDefaultEnemyOrc();
    world = createWorld(createLevels(createLevelWith(unit)), heroUnit);
    assertEquals(2, world.getAllEntities().size());
    assertTrue(world.getAllEntities().contains(unit));
  }

  @Test
  public void testGetAllEntitiesHasManyUnitsOneLevel() {
    Unit unit = createDefaultEnemyOrc();
    Unit unit2 = createDefaultPlayerKnight();
    Unit unit3 = createDefaultPlayerArcher();
    world = createWorld(createLevels(createLevelWith(unit, unit2, unit3)), heroUnit);
    assertEquals(4, world.getAllEntities().size());
    assertTrue(world.getAllEntities().contains(unit));
    assertTrue(world.getAllEntities().contains(unit2));
    assertTrue(world.getAllEntities().contains(unit3));
  }

  @Test
  public void testGetAllEntitiesDoesNotHaveUnitInAnotherLevel() {
    Unit unit = createDefaultEnemyOrc();
    world = createWorld(createLevels(createEmptyLevel(), createLevelWith(unit)), heroUnit);
    assertEquals(world.getAllEntities().size(), 1);
    assertFalse(world.getAllEntities().contains(unit));
  }

  @Test
  public void testGetAllEntitiesDoesNotHaveManyUnitsInAnotherLevel() {
    Unit unit = createDefaultEnemyOrc();
    Unit unit2 = createDefaultPlayerKnight();
    Unit unit3 = createDefaultPlayerArcher();
    world = createWorld(
        createLevels(createEmptyLevel(), createLevelWith(unit, unit2, unit3)), heroUnit);
    assertEquals(world.getAllEntities().size(), 1);
    assertFalse(world.getAllEntities().contains(unit));
    assertFalse(world.getAllEntities().contains(unit2));
    assertFalse(world.getAllEntities().contains(unit3));
  }

  @Test
  public void testGetAllEntitiesDoesNotHaveSomeUnitsInAnotherLevel() {
    Unit unit = createDefaultEnemyOrc();
    Unit unit2 = createDefaultPlayerKnight();
    Unit unit3 = createDefaultPlayerArcher();
    world = createWorld(
        createLevels(createLevelWith(unit), createLevelWith(unit2, unit3)), heroUnit);
    assertEquals(2, world.getAllEntities().size());
    assertTrue(world.getAllEntities().contains(unit));
    assertFalse(world.getAllEntities().contains(unit2));
    assertFalse(world.getAllEntities().contains(unit3));
  }

  @Test
  public void testGetAllEntitiesHasOneItemOneLevel() {
    Item item = createStubItem(new MapPoint(0, 0));
    world = createWorld(createLevels(createLevelWith(item)), heroUnit);
    assertEquals(2, world.getAllEntities().size());
    assertTrue(world.getAllEntities().contains(item));
  }

  @Test
  public void testGetAllEntitiesHasManyItemOneLevel() {
    Item item = createStubItem(new MapPoint(0, 0));
    Item item2 = createStubItem(new MapPoint(20, 20));
    Item item3 = createStubItem(new MapPoint(40, 20));
    Item item4 = createStubItem(new MapPoint(20, 40));
    world = createWorld(createLevels(createLevelWith(item, item2, item3, item4)), heroUnit);
    assertEquals(5, world.getAllEntities().size());
    assertTrue(world.getAllEntities().contains(item));
    assertTrue(world.getAllEntities().contains(item2));
    assertTrue(world.getAllEntities().contains(item3));
    assertTrue(world.getAllEntities().contains(item4));
  }

  @Test
  public void testGetAllEntitiesDoesNotHaveOneItemInAnotherLevel() {
    Item item = createStubItem(new MapPoint(0, 0));
    world = createWorld(createLevels(createEmptyLevel(), createLevelWith(item)), heroUnit);
    assertEquals(1, world.getAllEntities().size());
    assertFalse(world.getAllEntities().contains(item));
  }

  @Test
  public void testGetAllEntitiesDoesNotHaveManyItemsAnotherLevel() {
    Item item = createStubItem(new MapPoint(0, 0));
    Item item2 = createStubItem(new MapPoint(20, 20));
    Item item3 = createStubItem(new MapPoint(40, 20));
    Item item4 = createStubItem(new MapPoint(20, 40));
    world = createWorld(
        createLevels(createEmptyLevel(), createLevelWith(item, item2, item3, item4)), heroUnit);
    assertEquals(1, world.getAllEntities().size());
    assertFalse(world.getAllEntities().contains(item));
    assertFalse(world.getAllEntities().contains(item2));
    assertFalse(world.getAllEntities().contains(item3));
    assertFalse(world.getAllEntities().contains(item4));
  }

  @Test
  public void testGetAllEntitiesDoesNotHaveSomeItemsAnotherLevel() {
    Item item = createStubItem(new MapPoint(0, 0));
    Item item2 = createStubItem(new MapPoint(20, 20));
    Item item3 = createStubItem(new MapPoint(40, 20));
    Item item4 = createStubItem(new MapPoint(20, 40));
    world = createWorld(
        createLevels(createLevelWith(item, item2), createLevelWith(item3, item4)), heroUnit);
    assertEquals(3, world.getAllEntities().size());
    assertTrue(world.getAllEntities().contains(item));
    assertTrue(world.getAllEntities().contains(item2));
    assertFalse(world.getAllEntities().contains(item3));
    assertFalse(world.getAllEntities().contains(item4));
  }

  @Test
  public void testGetAllEntitiesHasOneMapEntityOneLevel() {
    MapEntity mapEntity = createStubMapEntity(new MapPoint(0, 0));
    world = createWorld(createLevels(createLevelWith(mapEntity)), heroUnit);
    assertEquals(2, world.getAllEntities().size());
    assertTrue(world.getAllEntities().contains(mapEntity));
  }

  @Test
  public void testGetAllEntitiesHasManyMapEntityOneLevel() {
    MapEntity mapEntity = createStubItem(new MapPoint(0, 0));
    MapEntity mapEntity2 = createStubItem(new MapPoint(40, 0));
    MapEntity mapEntity3 = createStubItem(new MapPoint(0, 40));
    MapEntity mapEntity4 = createStubItem(new MapPoint(40, 40));
    world = createWorld(
        createLevels(createLevelWith(mapEntity, mapEntity2, mapEntity3, mapEntity4)), heroUnit);
    assertEquals(5, world.getAllEntities().size());
    assertTrue(world.getAllEntities().contains(mapEntity));
    assertTrue(world.getAllEntities().contains(mapEntity2));
    assertTrue(world.getAllEntities().contains(mapEntity3));
    assertTrue(world.getAllEntities().contains(mapEntity4));
  }

  @Test
  public void testGetAllEntitiesDoesNotHaveOneMapEntityInAnotherLevel() {
    MapEntity mapEntity = createStubItem(new MapPoint(0, 0));
    world = createWorld(createLevels(createEmptyLevel(), createLevelWith(mapEntity)), heroUnit);
    assertEquals(1, world.getAllEntities().size());
    assertFalse(world.getAllEntities().contains(mapEntity));
  }

  @Test
  public void testGetAllEntitiesDoesNotHaveManyMapEntityInAnotherLevel() {
    MapEntity mapEntity = createStubMapEntity(new MapPoint(0, 0));
    MapEntity mapEntity2 = createStubMapEntity(new MapPoint(20, 20));
    MapEntity mapEntity3 = createStubMapEntity(new MapPoint(40, 20));
    MapEntity mapEntity4 = createStubMapEntity(new MapPoint(20, 40));
    world = createWorld(
        createLevels(
            createEmptyLevel(), createLevelWith(mapEntity, mapEntity2, mapEntity3, mapEntity4)),
        heroUnit
    );
    assertEquals(1, world.getAllEntities().size());
    assertFalse(world.getAllEntities().contains(mapEntity));
    assertFalse(world.getAllEntities().contains(mapEntity2));
    assertFalse(world.getAllEntities().contains(mapEntity3));
    assertFalse(world.getAllEntities().contains(mapEntity4));
  }

  @Test
  public void testGetAllEntitiesDoesNotHaveSomeMapEntityAnotherLevel() {
    MapEntity mapEntity = createStubMapEntity(new MapPoint(0, 0));
    MapEntity mapEntity2 = createStubMapEntity(new MapPoint(20, 20));
    MapEntity mapEntity3 = createStubMapEntity(new MapPoint(40, 20));
    MapEntity mapEntity4 = createStubMapEntity(new MapPoint(20, 40));
    world = createWorld(
        createLevels(
            createLevelWith(mapEntity, mapEntity2), createLevelWith(mapEntity3, mapEntity4)),
        heroUnit
    );
    assertEquals(3, world.getAllEntities().size());
    assertTrue(world.getAllEntities().contains(mapEntity));
    assertTrue(world.getAllEntities().contains(mapEntity2));
    assertFalse(world.getAllEntities().contains(mapEntity3));
    assertFalse(world.getAllEntities().contains(mapEntity4));
  }


  @Test
  public void testGetAllUnitsHasHeroUnit() {
    world = createWorld(createLevels(createEmptyLevel()), heroUnit);
    assertEquals(1, world.getAllUnits().size());
    assertTrue(world.getAllUnits().contains(heroUnit));
  }

  @Test
  public void testGetAllUnitsHasOneUnitOneLevel() {
    Unit unit = createDefaultEnemyOrc();
    world = createWorld(createLevels(createLevelWith(unit)), heroUnit);
    assertEquals(2, world.getAllUnits().size());
    assertTrue(world.getAllUnits().contains(unit));
  }

  @Test
  public void testGetAllUnitsHasManyUnitsOneLevel() {
    Unit unit = createDefaultEnemyOrc();
    Unit unit2 = createDefaultPlayerKnight();
    Unit unit3 = createDefaultPlayerArcher();
    world = createWorld(createLevels(createLevelWith(unit, unit2, unit3)), heroUnit);
    assertEquals(4, world.getAllUnits().size());
    assertTrue(world.getAllUnits().contains(unit));
    assertTrue(world.getAllUnits().contains(unit2));
    assertTrue(world.getAllUnits().contains(unit3));
  }

  @Test
  public void testGetAllUnitsDoesNotHaveUnitInAnotherLevel() {
    Unit unit = createDefaultEnemyOrc();
    world = createWorld(createLevels(createEmptyLevel(), createLevelWith(unit)), heroUnit);
    assertEquals(1, world.getAllUnits().size());
    assertFalse(world.getAllUnits().contains(unit));
  }

  @Test
  public void testGetAllUnitsDoesNotHaveManyUnitsInAnotherLevel() {
    Unit unit = createDefaultEnemyOrc();
    Unit unit2 = createDefaultPlayerKnight();
    Unit unit3 = createDefaultPlayerArcher();
    world = createWorld(
        createLevels(createEmptyLevel(), createLevelWith(unit, unit2, unit3)), heroUnit);
    assertEquals(1, world.getAllUnits().size());
    assertFalse(world.getAllUnits().contains(unit));
    assertFalse(world.getAllUnits().contains(unit2));
    assertFalse(world.getAllUnits().contains(unit3));
  }

  @Test
  public void testGetAllUnitsDoesNotHaveSomeUnitsInAnotherLevel() {
    Unit unit = createDefaultEnemyOrc();
    Unit unit2 = createDefaultPlayerKnight();
    Unit unit3 = createDefaultPlayerArcher();
    world = createWorld(
        createLevels(createLevelWith(unit), createLevelWith(unit2, unit3)), heroUnit);
    assertEquals(2, world.getAllUnits().size());
    assertTrue(world.getAllUnits().contains(unit));
    assertFalse(world.getAllUnits().contains(unit2));
    assertFalse(world.getAllUnits().contains(unit3));
  }

  @Test
  public void testPointIsUnPassableBecauseOfMapEntity() {
    MapEntity mapEntity = createStubMapEntity(new MapPoint(0, 0));
    world = createWorld(createLevels(createLevelWith(mapEntity)), heroUnit);
    assertFalse(world.isPassable(new MapPoint(0,0)));
    assertFalse(world.isPassable(new MapPoint(0.5,0.5)));
    assertFalse(world.isPassable(new MapPoint(0.99, 0.99)));
  }

  @Test
  public void testPointIsUnpassableBecauseOutOfMap() {
    world = createWorld(createLevels(createEmptyLevel()), heroUnit);
    assertFalse(world.isPassable(new MapPoint(-200,0)));
    assertFalse(world.isPassable(new MapPoint(200,0)));
    assertFalse(world.isPassable(new MapPoint(0,-200)));
    assertFalse(world.isPassable(new MapPoint(0,200)));
  }
}
