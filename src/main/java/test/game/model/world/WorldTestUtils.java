package test.game.model.world;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import main.common.entity.HeroUnit;
import main.common.entity.MapEntity;
import main.common.entity.Team;
import main.common.entity.Unit;
import main.common.entity.usable.Ability;
import main.common.entity.usable.Effect;
import main.common.entity.usable.Item;
import main.common.images.GameImageResource;
import main.common.util.MapPoint;
import main.common.util.MapRect;
import main.common.util.MapSize;
import main.game.model.Level;
import main.game.model.entity.DefaultMapEntity;
import main.game.model.entity.unit.DefaultHeroUnit;
import main.game.model.entity.unit.DefaultUnit;
import main.game.model.entity.unit.UnitType;
import main.game.model.entity.usable.BaseAbility;
import main.game.model.entity.usable.BaseEffect;
import main.game.model.entity.usable.DefaultItem;
import main.common.World;
import main.game.model.world.DefaultWorld;
import main.game.model.world.pathfinder.DefaultPathFinder;
import test.game.model.entity.StubUnitSpriteSheet;

/**
 * Useful factory methods.
 * @author chongdyla (External Tester)
 */
public class WorldTestUtils {

  /**
   * Creates a unit outside the bounds (less than -100 more than 100 x and y).
   *
   * @return an out of bounds unit
   */
  public static DefaultUnit createDefaultUnit(MapPoint point) {
    return new DefaultUnit(
        point,
        new MapSize(1, 1),
        Team.ENEMY,
        new StubUnitSpriteSheet(),
        UnitType.SPEARMAN
    );
  }

  /**
   * Creates a level with unit args.
   *
   * @param units units in level
   * @return a new level
   */
  public static Level createLevelWith(Unit... units) {
    return new Level(
        new MapRect(new MapPoint(-10000, -10000), new MapPoint(10000, 10000)),
        Arrays.asList(units),
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList(),
        (e, world) -> false,
        ""
    );
  }

  /**
   * Creates a level with mapEntities.
   *
   * @param mapEntity mapEntities in a level
   * @return a new level with the map entities
   */
  public static Level createLevelWith(MapEntity... mapEntity) {
    return new Level(
        new MapRect(new MapPoint(-100, -100), new MapPoint(100, 100)),
        Collections.emptyList(),
        Collections.emptyList(),
        Arrays.asList(mapEntity),
        Collections.emptyList(),
        (e, world) -> false,
        ""
    );
  }

  /**
   * Creates a level based on items.
   *
   * @param item items in a level
   * @return a new level with the items args
   */
  public static Level createLevelWith(Item... item) {
    return new Level(
        new MapRect(new MapPoint(-100, -100), new MapPoint(100, 100)),
        Collections.emptyList(),
        Arrays.asList(item),
        Collections.emptyList(),
        Collections.emptyList(),
        (e, world) -> false,
        ""
    );
  }

  /**
   * Creates an item.
   *
   * @param point a position in the world
   * @return an Item at the position point
   */
  public static Item createStubItem(MapPoint point) {
    return new DefaultItem(
        point,
        createStubAbility(),
        GameImageResource.POTION_BLUE_ITEM.getGameImage()
    );
  }

  /**
   * Creates and ability that does nothing.
   */
  public static Ability createStubAbility() {
    return new BaseAbility("", GameImageResource.TEST_IMAGE_1_1.getGameImage(), 1, 2) {

      @Override
      public Collection<Unit> selectUnitsToApplyOn(
          World world, Collection<Unit> selectedDefaultUnits
      ) {
        return Collections.emptyList();
      }

      @Override
      public Effect createEffectForUnit(Unit unit) {
        return new BaseEffect(unit, 1) {
        };
      }
    };
  }

  /**
   * Creates a mapEntity with the correct MapPoint.
   *
   * @param mapPoint point in the world
   * @return a new MapEntity
   */
  public static MapEntity createStubMapEntity(MapPoint mapPoint) {
    return new DefaultMapEntity(mapPoint, GameImageResource.TEST_IMAGE_1_1.getGameImage()) {
      @Override
      public void tick(long timeSinceLastTick, World world) {
        //DO NOTHING
      }

      @Override
      public boolean isPassable() {
        return false;
      }
    };
  }

  /**
   * Creates a list of levels based on args.
   *
   * @param levels levels
   * @return a list of levels
   */
  public static List<Level> createLevels(Level... levels) {
    return Arrays.asList(levels);
  }

  /**
   * Creates an empty level.
   *
   * @return an empty level
   */
  public static Level createEmptyLevel() {
    return new Level(
        new MapRect(new MapPoint(-100, -100), new MapPoint(100, 100)),
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList(),
        (e, world) -> false,
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
  public static World createWorld(List<Level> levels, HeroUnit heroUnit) {
    return new DefaultWorld(levels, heroUnit, new DefaultPathFinder());
  }


  /**
   * Creates an enemy orc.
   *
   * @return an enemy orc unit
   */
  public static Unit createDefaultEnemyOrc() {
    return new DefaultUnit(
        new MapPoint(60, 60),
        new MapSize(1, 1),
        Team.ENEMY,
        new StubUnitSpriteSheet(),
        UnitType.SPEARMAN
    );
  }

  /**
   * Creates a player knight.
   *
   * @return a player knight unit
   */
  public static Unit createDefaultPlayerKnight() {
    return new DefaultUnit(
        new MapPoint(0, 0),
        new MapSize(1, 1),
        Team.PLAYER,
        new StubUnitSpriteSheet(),
        UnitType.SWORDSMAN
    );
  }

  /**
   * Creates a player knight.
   *
   * @return a player knight unit.
   */
  public static Unit createDefaultPlayerArcher() {
    return new DefaultUnit(
        new MapPoint(20, 20),
        new MapSize(1, 1),
        Team.PLAYER,
        new StubUnitSpriteSheet(),
        UnitType.ARCHER
    );
  }

  /**
   * Creates a hero unit at 1, 1.
   *
   * @return a new DefaultHeroUnit
   */
  public static HeroUnit createDefaultHeroUnit() {
    return new DefaultHeroUnit(
        new MapPoint(1, 1),
        new MapSize(1, 1),
        new StubUnitSpriteSheet(),
        UnitType.SWORDSMAN,
        Collections.emptyList(),
        0
    );
  }

  /**
   * Creates a hero unit based on mapPoint.
   *
   * @param mapPoint point on the map
   * @return a new DefaultHeroUnit
   */
  public static HeroUnit createDefaultHeroUnit(MapPoint mapPoint) {
    return new DefaultHeroUnit(
        mapPoint,
        new MapSize(1, 1),
        new StubUnitSpriteSheet(),
        UnitType.SWORDSMAN,
        Collections.emptyList(),
        0
    );
  }
}
