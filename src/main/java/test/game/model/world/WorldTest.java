package test.game.model.world;

import static main.images.GameImageResource.GOLDEN_HERO_SPRITE_SHEET;
import static main.images.GameImageResource.ORC_SPEARMAN_SPRITE_SHEET;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import main.game.model.Level;
import main.game.model.entity.HeroUnit;
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
   * Sets up class variables that are used in every test
   */
  @Before
  public void initialise(){
    world = null;
  }

  @Test
  public void testInitialisation(){
    assertNull(world);
    assertNotNull(heroUnit);
    assertNotNull(config);
  }

  @Test
  public void testGetAllEntitiesHasHeroUnit(){
    world = createWorld(createOneEmptyLevel(), heroUnit);
    assertEquals(1, world.getAllEntities().size());
    assertTrue(world.getAllEntities().contains(heroUnit));
  }

  @Test
  public void testGetAllEntitiesHasOneUnit(){
    Unit unit = createDefaultEnemyOrc();
    world = createWorld(createSingletonLevelListWith(unit), heroUnit);
    assertEquals(2, world.getAllEntities().size());
    assertTrue(world.getAllEntities().contains(unit));
  }

  public void testGetAlLEntitiesHasManyUnits(){
    Unit[] units = {createDefaultEnemyOrc(), createDefaultEnemyOrc(), createDefaultEnemyOrc()};
    world = createWorld(createSingleLevelListWith(units), heroUnit);
    assertEquals(world.getAllEntities().size(), 1 + units.length);
    assertTrue(world.getAllEntities().contains(units));
  }

  @Test
  public void testGetAllEntitiesHasUnitInAnotherLevel(){
    Unit unit = createDefaultEnemyOrc();
    world = createWorld(createLevels(createEmptyLevel(), createLevelWithUnit()), heroUnit);
    assertEquals(world.getAllEntities().size(), 1);
    assertFalse(world.getAllEntities().contains(unit));
  }

  @Test
  public void testGetAllEntitiesHasMapEntities(){
    MapEntity mapEntity = createMapEntity();
    world = createWorld(createLevels(createLevelWithMapEntities(mapEntity)), heroUnit);
    assertEquals(world.getAllEntities().size(), 2);
    assertTrue(world.getAllEntities().contains(mapEntity));

//    MapEntity[] mapEntities = {createMapEntity(), createMapEntity(), createMapEntity()};
//    world = createWorld(createLevels(createLevelWithMapEntities(mapEntities)), heroUnit);
//    assertEquals(world.getAllEntities().size(), 1 + mapEntities.length);
//    for (MapEntity me :
//        mapEntities) {
//      assertTrue(world.getAllEntities().contains(me));
//    }
  }

  @Test
  public void testGetAllEntitiesHasMapEntitiesInAnotherLevel(){
    MapEntity mapEntity = createMapEntity();
    world = createWorld(createLevels(createEmptyLevel(), createLevelWithMapEntities(mapEntity)), heroUnit);
    assertEquals(1, world.getAllEntities().size());
    assertFalse(world.getAllEntities().contains(mapEntity));
  }


  //PRIVATE METHODS

  private List<Level> createSingleLevelListWith(Unit[] units) {
    return new ArrayList<Level>(){{
      add(new Level(new MapRect(new MapPoint(-100,-100), new MapPoint(100,100)),
          Arrays.asList(units),
          Collections.emptyList(),
          Collections.emptyList(),
          Collections.emptyList(),
          e -> false,
          ""
      ));
    }};
  }


  private Level createLevelWithMapEntities(MapEntity... mapEntities) {
    return new Level(new MapRect(new MapPoint(-100,-100), new MapPoint(100,100)),
        Collections.emptyList(),
        Collections.emptyList(),
        Arrays.asList(mapEntities),
        Collections.emptyList(),
        e -> false,
        ""
    );
  }

  private MapEntity createMapEntity() {
    return new MapEntity(new MapPoint(0,0)) {
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

  private List<Level> createLevels(Level... levels) {
    return Arrays.asList(levels);
  }

  private List<Level> createSingletonLevelListWith(Unit unit) {
    return new ArrayList<Level>(){{
      add(new Level(new MapRect(new MapPoint(-100,-100), new MapPoint(100,100)),
          Collections.singletonList(unit),
          Collections.emptyList(),
          Collections.emptyList(),
          Collections.emptyList(),
          e -> false,
          ""
      ));
    }};
  }

  private Level createEmptyLevel(){
    return new Level(new MapRect(new MapPoint(-100,-100), new MapPoint(100,100)),
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList(),
        e -> false,
        ""
    );
  }

  private Level createLevelWithUnit(Unit... units){
    return new Level(new MapRect(new MapPoint(-100,-100), new MapPoint(100,100)),
        Arrays.asList(units),
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList(),
        e -> false,
        ""
    );
  }



  private List<Level> createOneEmptyLevel() {
    return new ArrayList<Level>(){{
      add(createEmptyLevel());
    }};
  }

  /**
   * Creates a world based on parameters.
   * @param levels levels in a world
   * @param heroUnit herounit in a world
   * @return a new world
   */
  private World createWorld(List<Level> levels, HeroUnit heroUnit) {
    return new World(levels, heroUnit);
  }


  /**
   * Creates an enemy orc
   * @return an enemy orc unit
   */
  private Unit createDefaultEnemyOrc(){
    return new Unit(
        new MapPoint(20,20),
        new MapSize(30,30),
        Team.ENEMY,
        new UnitSpriteSheet(ORC_SPEARMAN_SPRITE_SHEET),
        UnitType.SPEARMAN
    );
  }
}
