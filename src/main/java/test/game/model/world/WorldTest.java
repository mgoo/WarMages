package test.game.model.world;

import static main.images.GameImageResource.ARCHER_SPRITE_SHEET;
import static main.images.GameImageResource.FOOT_KNIGHT_SPRITE_SHEET;
import static main.images.GameImageResource.GOLDEN_HERO_SPRITE_SHEET;
import static main.images.GameImageResource.ORC_SPEARMAN_SPRITE_SHEET;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import main.game.model.Level;
import main.game.model.entity.Entity;
import main.game.model.entity.HeroUnit;
import main.game.model.entity.Item;
import main.game.model.entity.MapEntity;
import main.game.model.entity.Team;
import main.game.model.entity.Unit;
import main.game.model.entity.UnitType;
import main.game.model.world.World;
import main.images.GameImage;
import main.images.UnitSpriteSheet;
import main.util.Config;
import main.util.MapPoint;
import main.util.MapRect;
import main.util.MapSize;
import org.junit.Before;
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
  private Config config = new Config();

  /**
   * Sets up class variables that are used in every test.
   */
  @Before
  public void initialise() {
    world = null;
  }

  @Test
  public void testInitialisation() {
    assertNull(world);
    assertNotNull(heroUnit);
    assertNotNull(config);
  }

  @Test
  public void testGetAllEntitiesHasHeroUnit() {
    world = createWorld(createLevels(createEmptyLevel()), heroUnit);
    assertEquals(1, world.getAllEntities().size());
    assertTrue(world.getAllEntities().contains(heroUnit));
  }

  @Test
  public void testGetAllEntitiesIsCloned() {
    Unit unit = createDefaultEnemyOrc();
    world = createWorld(createLevels(createLevelWith(unit)), heroUnit);
    assertEquals(2, world.getAllEntities().size());
    assertTrue(world.getAllEntities().contains(unit));
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
  public void testGetAllEntitiesHasUnitInAnotherLevel() {
    Unit unit = createDefaultEnemyOrc();
    world = createWorld(createLevels(createEmptyLevel(), createLevelWith(unit)), heroUnit);
    assertEquals(world.getAllEntities().size(), 1);
    assertFalse(world.getAllEntities().contains(unit));
  }

  @Test
  public void testGetAllEntitiesHasManyUnitsInAnotherLevel() {
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
  public void testGetAllEntitiesHasSomeUnitsInAnotherLevel() {
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
    Item item = createDefaultItem(new MapPoint(0, 0));
    world = createWorld(createLevels(createLevelWith(item)), heroUnit);
    assertEquals(2, world.getAllEntities().size());
    assertTrue(world.getAllEntities().contains(item));
  }

  @Test
  public void testGetAllEntitiesHasManyItemOneLevel() {
    Item item = createDefaultItem(new MapPoint(0, 0));
    Item item2 = createDefaultItem(new MapPoint(20, 20));
    Item item3 = createDefaultItem(new MapPoint(40, 20));
    Item item4 = createDefaultItem(new MapPoint(20, 40));
    world = createWorld(createLevels(createLevelWith(item, item2, item3, item4)), heroUnit);
    assertEquals(5, world.getAllEntities().size());
    assertTrue(world.getAllEntities().contains(item));
    assertTrue(world.getAllEntities().contains(item2));
    assertTrue(world.getAllEntities().contains(item3));
    assertTrue(world.getAllEntities().contains(item4));
  }

  @Test
  public void testGetAllEntitiesHasOneItemInAnotherLevel() {
    Item item = createDefaultItem(new MapPoint(0, 0));
    world = createWorld(createLevels(createEmptyLevel(), createLevelWith(item)), heroUnit);
    assertEquals(1, world.getAllEntities().size());
    assertFalse(world.getAllEntities().contains(item));
  }

  @Test
  public void testGetAllEntitiesHasManyItemsAnotherLevel() {
    Item item = createDefaultItem(new MapPoint(0, 0));
    Item item2 = createDefaultItem(new MapPoint(20, 20));
    Item item3 = createDefaultItem(new MapPoint(40, 20));
    Item item4 = createDefaultItem(new MapPoint(20, 40));
    world = createWorld(
        createLevels(createEmptyLevel(), createLevelWith(item, item2, item3, item4)), heroUnit);
    assertEquals(1, world.getAllEntities().size());
    assertFalse(world.getAllEntities().contains(item));
    assertFalse(world.getAllEntities().contains(item2));
    assertFalse(world.getAllEntities().contains(item3));
    assertFalse(world.getAllEntities().contains(item4));
  }

  @Test
  public void testGetAllEntitiesHasSomeItemsAnotherLevel() {
    Item item = createDefaultItem(new MapPoint(0, 0));
    Item item2 = createDefaultItem(new MapPoint(20, 20));
    Item item3 = createDefaultItem(new MapPoint(40, 20));
    Item item4 = createDefaultItem(new MapPoint(20, 40));
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
    MapEntity mapEntity = createDefaultMapEntity(new MapPoint(0, 0));
    world = createWorld(createLevels(createLevelWith(mapEntity)), heroUnit);
    assertEquals(2, world.getAllEntities().size());
    assertTrue(world.getAllEntities().contains(mapEntity));
  }

  @Test
  public void testGetAllEntitiesHasManyMapEntityOneLevel() {
    MapEntity mapEntity = createDefaultItem(new MapPoint(0, 0));
    MapEntity mapEntity2 = createDefaultItem(new MapPoint(40, 0));
    MapEntity mapEntity3 = createDefaultItem(new MapPoint(0, 40));
    MapEntity mapEntity4 = createDefaultItem(new MapPoint(40, 40));
    world = createWorld(
        createLevels(createLevelWith(mapEntity, mapEntity2, mapEntity3, mapEntity4)), heroUnit);
    assertEquals(5, world.getAllEntities().size());
    assertTrue(world.getAllEntities().contains(mapEntity));
    assertTrue(world.getAllEntities().contains(mapEntity2));
    assertTrue(world.getAllEntities().contains(mapEntity3));
    assertTrue(world.getAllEntities().contains(mapEntity4));
  }

  @Test
  public void testGetAllEntitiesHasOneMapEntityInAnotherLevel() {
    MapEntity mapEntity = createDefaultItem(new MapPoint(0, 0));
    world = createWorld(createLevels(createEmptyLevel(), createLevelWith(mapEntity)), heroUnit);
    assertEquals(1, world.getAllEntities().size());
    assertFalse(world.getAllEntities().contains(mapEntity));
  }

  @Test
  public void testGetAllEntitiesHasManyMapEntityInAnotherLevel() {
    MapEntity mapEntity = createDefaultMapEntity(new MapPoint(0, 0));
    MapEntity mapEntity2 = createDefaultMapEntity(new MapPoint(20, 20));
    MapEntity mapEntity3 = createDefaultMapEntity(new MapPoint(40, 20));
    MapEntity mapEntity4 = createDefaultMapEntity(new MapPoint(20, 40));
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
  public void testGetAllEntitiesHasSomeMapEntityAnotherLevel() {
    MapEntity mapEntity = createDefaultMapEntity(new MapPoint(0, 0));
    MapEntity mapEntity2 = createDefaultMapEntity(new MapPoint(20, 20));
    MapEntity mapEntity3 = createDefaultMapEntity(new MapPoint(40, 20));
    MapEntity mapEntity4 = createDefaultMapEntity(new MapPoint(20, 40));
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
  public void testSetSelectionAndGetSelection() {
    Unit unit = createDefaultEnemyOrc();
    Unit unit2 = createDefaultPlayerKnight();
    Unit unit3 = createDefaultPlayerArcher();
    world = createWorld(createLevels(createLevelWith(unit, unit2, unit3)), heroUnit);
    world.setEntitySelection(Arrays.asList(unit, unit2, unit3));
    Collection<Entity> selection = world.getSelectedEntity();
    assertEquals(3, selection.size());
    assertTrue(selection.contains(unit));
    assertTrue(selection.contains(unit2));
    assertTrue(selection.contains(unit3));
  }

  @Test
  public void testSelectionIsCloned() {
    Unit unit = createDefaultEnemyOrc();
    Unit unit2 = createDefaultPlayerKnight();
    Unit unit3 = createDefaultPlayerArcher();
    List<Entity> units = new ArrayList<>(Arrays.asList(unit, unit2));
    world = createWorld(createLevels(createLevelWith(unit, unit2, unit3)), heroUnit);
    world.setEntitySelection(units);
    units.add(unit3);
    Collection<Entity> selection = world.getSelectedEntity();
    assertEquals(2, selection.size());
    assertFalse(selection.contains(unit3));
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
  public void testGetAllUnitsHasUnitInAnotherLevel() {
    Unit unit = createDefaultEnemyOrc();
    world = createWorld(createLevels(createEmptyLevel(), createLevelWith(unit)), heroUnit);
    assertEquals(1, world.getAllUnits().size());
    assertFalse(world.getAllUnits().contains(unit));
  }

  @Test
  public void testGetAllUnitsHasManyUnitsInAnotherLevel() {
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
  public void testGetAllUnitsHasSomeUnitsInAnotherLevel() {
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
  public void testIsPassableWithUnPassableEntity() {
    //CURRENTLY UNAVAILABLE TO BE DONE.
    //TODO DYLAN or GABIE - How should I know if it's passable?
  }
  //PRIVATE METHODS

  /**
   * Creates a level with unit args.
   *
   * @param units units in level
   * @return a new level
   */
  private Level createLevelWith(Unit... units) {
    return new Level(
        new MapRect(new MapPoint(-100, -100), new MapPoint(100, 100)),
        Arrays.asList(units),
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList(),
        e -> false,
        ""
    );
  }

  /**
   * Creates a level with mapEntities.
   *
   * @param mapEntity mapEntities in a level
   * @return a new level with the map entities
   */
  private Level createLevelWith(MapEntity... mapEntity) {
    return new Level(
        new MapRect(new MapPoint(-100, -100), new MapPoint(100, 100)),
        Collections.emptyList(),
        Collections.emptyList(),
        Arrays.asList(mapEntity),
        Collections.emptyList(),
        e -> false,
        ""
    );
  }

  /**
   * Creates a level based on items.
   *
   * @param item items in a level
   * @return a new level with the items args
   */
  private Level createLevelWith(Item... item) {
    return new Level(
        new MapRect(new MapPoint(-100, -100), new MapPoint(100, 100)),
        Collections.emptyList(),
        Arrays.asList(item),
        Collections.emptyList(),
        Collections.emptyList(),
        e -> false,
        ""
    );
  }

  /**
   * Creates an item.
   *
   * @param point a position in the world
   * @return an Item at the position point
   */
  private Item createDefaultItem(MapPoint point) {
    return new Item(point) {
      @Override
      public void applyTo(Unit unit) {
        //NOTHING
      }

      @Override
      public void tick(long timeSinceLastTick) {
        //NOTHING
      }
    };
  }

  /**
   * Creates a mapEntity with the correct MapPoint.
   *
   * @param mapPoint point in the world
   * @return a new MapEntity
   */
  private MapEntity createDefaultMapEntity(MapPoint mapPoint) {
    return new MapEntity(mapPoint) {
      @Override
      public void setImage(GameImage image) {
        //DO NOTHING
      }

      @Override
      public void tick(long timeSinceLastTick) {
        //DO NOTHING
      }
    };
  }

  /**
   * Creates a list of levels based on args.
   *
   * @param levels levels
   * @return a list of levels
   */
  private List<Level> createLevels(Level... levels) {
    return Arrays.asList(levels);
  }

  /**
   * Creates an empty level.
   *
   * @return an empty level
   */
  private Level createEmptyLevel() {
    return new Level(
        new MapRect(new MapPoint(-100, -100), new MapPoint(100, 100)),
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList(),
        e -> false,
        ""
    );
  }

  /**
   * Creates a world based on parameters.
   *
   * @param levels levels in a world
   * @param heroUnit herounit in a world
   * @return a new world
   */
  private World createWorld(List<Level> levels, HeroUnit heroUnit) {
    return new World(levels, heroUnit);
  }


  /**
   * Creates an enemy orc.
   *
   * @return an enemy orc unit
   */
  private Unit createDefaultEnemyOrc() {
    return new Unit(
        new MapPoint(20, 20),
        new MapSize(30, 30),
        Team.ENEMY,
        new UnitSpriteSheet(ORC_SPEARMAN_SPRITE_SHEET),
        UnitType.SPEARMAN
    );
  }

  /**
   * Creates a player knight.
   *
   * @return a player knight unit
   */
  private Unit createDefaultPlayerKnight() {
    return new Unit(
        new MapPoint(20, 20),
        new MapSize(30, 30),
        Team.PLAYER,
        new UnitSpriteSheet(FOOT_KNIGHT_SPRITE_SHEET),
        UnitType.SWORDSMAN
    );
  }

  /**
   * Creates a player knight.
   *
   * @return a player knight unit.
   */
  private Unit createDefaultPlayerArcher() {
    return new Unit(
        new MapPoint(20, 20),
        new MapSize(30, 30),
        Team.PLAYER,
        new UnitSpriteSheet(ARCHER_SPRITE_SHEET),
        UnitType.ARCHER
    );
  }
}
