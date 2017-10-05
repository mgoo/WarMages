package test.game.model.world;

import static main.images.GameImageResource.ARCHER_SPRITE_SHEET;
import static main.images.GameImageResource.FOOT_KNIGHT_SPRITE_SHEET;
import static main.images.GameImageResource.ORC_SPEARMAN_SPRITE_SHEET;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import main.game.model.Level;
import main.game.model.entity.HeroUnit;
import main.game.model.entity.MapEntity;
import main.game.model.entity.Team;
import main.game.model.entity.Unit;
import main.game.model.entity.UnitType;
import main.game.model.entity.usables.Ability;
import main.game.model.entity.usables.Item;
import main.game.model.world.World;
import main.images.GameImageResource;
import main.images.UnitSpriteSheet;
import main.util.MapPoint;
import main.util.MapRect;
import main.util.MapSize;

public class WorldTestUtils {

  /**
   * Creates a unit outside the bounds (less than -100 more than 100 x and y).
   *
   * @return an out of bounds unit
   */
  public static Unit createUnit(MapPoint point) {
    return new Unit(
        point,
        new MapSize(30, 30),
        Team.ENEMY,
        new UnitSpriteSheet(ORC_SPEARMAN_SPRITE_SHEET),
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
  public static Level createLevelWith(MapEntity... mapEntity) {
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
  public static Level createLevelWith(Item... item) {
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
  public static Item createStubItem(MapPoint point) {
    return new Item(
        point,
        createStubAbility(),
        GameImageResource.POTION_BLUE_ITEM.getGameImage()
    );
  }

  public static Ability createStubAbility() {
    return new Ability("", GameImageResource.TEST_IMAGE_1_1.getGameImage(), 1, 2) {
      @Override
      public Effect _createEffectForUnit(Unit unit) {
        return new Effect(unit, 1) {
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
    return new MapEntity(mapPoint) {
      @Override
      public void tick(long timeSinceLastTick, World world) {
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
  public static World createWorld(List<Level> levels, HeroUnit heroUnit) {
    return new World(levels, heroUnit);
  }


  /**
   * Creates an enemy orc.
   *
   * @return an enemy orc unit
   */
  public static Unit createDefaultEnemyOrc() {
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
  public static Unit createDefaultPlayerKnight() {
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
  public static Unit createDefaultPlayerArcher() {
    return new Unit(
        new MapPoint(20, 20),
        new MapSize(30, 30),
        Team.PLAYER,
        new UnitSpriteSheet(ARCHER_SPRITE_SHEET),
        UnitType.ARCHER
    );
  }
}
