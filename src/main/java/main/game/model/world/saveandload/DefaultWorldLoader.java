package main.game.model.world.saveandload;

import static main.common.images.GameImageResource.ARCHER_SPRITE_SHEET;
import static main.common.images.GameImageResource.BARRLE_1;
import static main.common.images.GameImageResource.BARRLE_2;
import static main.common.images.GameImageResource.BARRLE_3;
import static main.common.images.GameImageResource.BARRLE_4;
import static main.common.images.GameImageResource.BUILDING_1;
import static main.common.images.GameImageResource.BUILDING_10;
import static main.common.images.GameImageResource.BUILDING_11;
import static main.common.images.GameImageResource.BUILDING_12;
import static main.common.images.GameImageResource.BUILDING_13;
import static main.common.images.GameImageResource.BUILDING_14;
import static main.common.images.GameImageResource.BUILDING_15;
import static main.common.images.GameImageResource.BUILDING_16;
import static main.common.images.GameImageResource.BUILDING_17;
import static main.common.images.GameImageResource.BUILDING_18;
import static main.common.images.GameImageResource.BUILDING_19;
import static main.common.images.GameImageResource.BUILDING_2;
import static main.common.images.GameImageResource.BUILDING_20;
import static main.common.images.GameImageResource.BUILDING_21;
import static main.common.images.GameImageResource.BUILDING_3;
import static main.common.images.GameImageResource.BUILDING_4;
import static main.common.images.GameImageResource.BUILDING_5;
import static main.common.images.GameImageResource.BUILDING_6;
import static main.common.images.GameImageResource.BUILDING_7;
import static main.common.images.GameImageResource.BUILDING_8;
import static main.common.images.GameImageResource.BUILDING_9;
import static main.common.images.GameImageResource.DARK_ELF_SPRITE_SHEET;
import static main.common.images.GameImageResource.FOOT_KNIGHT_SPRITE_SHEET;
import static main.common.images.GameImageResource.FOUNTAIN;
import static main.common.images.GameImageResource.GOLDEN_HERO_SPRITE_SHEET;
import static main.common.images.GameImageResource.MAGE_CAPE_SPRITE_SHEET;
import static main.common.images.GameImageResource.MALE_MAGE_SPRITE_SHEET;
import static main.common.images.GameImageResource.ORC_SPEARMAN_SPRITE_SHEET;
import static main.common.images.GameImageResource.POTION_BLUE_ITEM;
import static main.common.images.GameImageResource.RING_GOLD_ITEM;
import static main.common.images.GameImageResource.SKELETON_ARCHER_SPRITE_SHEET;
import static main.common.images.GameImageResource.SPEARMAN_SPRITE_SHEET;
import static main.common.images.GameImageResource.TREE_1;
import static main.common.images.GameImageResource.TREE_2;
import static main.common.images.GameImageResource.TREE_3;
import static main.common.images.GameImageResource.TREE_4;
import static main.common.images.GameImageResource.TREE_5;
import static main.common.images.GameImageResource.TREE_6;
import static main.common.images.GameImageResource.TREE_7;
import static main.common.images.GameImageResource.TREE_8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Function;
import main.common.WorldLoader;
import main.common.entity.Entity;
import main.common.entity.HeroUnit;
import main.common.entity.MapEntity;
import main.common.entity.Team;
import main.common.images.GameImage;
import main.common.images.GameImageResource;
import main.common.util.MapPoint;
import main.common.util.MapRect;
import main.common.util.MapSize;
import main.game.model.GameModel;
import main.game.model.Level;
import main.game.model.Level.Goal;
import main.game.model.Level.Goal.AllEnemiesKilled;
import main.game.model.entity.DefaultMapEntity;
import main.game.model.entity.unit.DefaultUnit;
import main.game.model.entity.unit.UnitType;
import main.game.model.entity.unit.DefaultHeroUnit;
import main.game.model.entity.usable.DamageBuffAbility;
import main.game.model.entity.usable.DefaultItem;
import main.game.model.entity.usable.HealAbility;
import main.game.model.world.World;
import main.game.model.world.pathfinder.DefaultPathFinder;
import main.images.DefaultUnitSpriteSheet;

/**
 * Loads a complex enough world to be played enjoyably.
 * @author chongdyla
 */
public class DefaultWorldLoader implements WorldLoader {

  private static final MapSize HERO_SIZE = new MapSize(0.9, 0.9);
  private static final MapSize STANDARD_UNIT_SIZE = new MapSize(0.5, 0.5);

  private static GameImage[] trees = new GameImage[]{
      //      TREE_1.getGameImage(),
      TREE_2.getGameImage(),
      TREE_3.getGameImage(),
      TREE_4.getGameImage(),
      TREE_5.getGameImage(),
      TREE_6.getGameImage(),
      TREE_7.getGameImage(),
      TREE_8.getGameImage()};

  private static GameImage[] barrles = new GameImage[]{
      BARRLE_1.getGameImage(),
      BARRLE_2.getGameImage(),
      BARRLE_3.getGameImage(),
      BARRLE_4.getGameImage()};

  private static GameImage[] buildings = new GameImage[]{
      BUILDING_1.getGameImage(),
      BUILDING_2.getGameImage(),
      BUILDING_3.getGameImage(),
      BUILDING_4.getGameImage(),
      BUILDING_5.getGameImage(),
      BUILDING_6.getGameImage(),
      BUILDING_7.getGameImage(),
      BUILDING_8.getGameImage(),
      BUILDING_9.getGameImage(),
      BUILDING_10.getGameImage(),
      BUILDING_11.getGameImage(),
      BUILDING_12.getGameImage(),
      BUILDING_13.getGameImage(),
      BUILDING_14.getGameImage(),
      BUILDING_15.getGameImage(),
      BUILDING_16.getGameImage(),
      BUILDING_17.getGameImage(),
      BUILDING_18.getGameImage(),
      BUILDING_19.getGameImage(),
      BUILDING_20.getGameImage(),
      BUILDING_21.getGameImage()};

  private static MapEntity makeBarrel(MapPoint position) {
    GameImage img = barrles[(int)(Math.random() * barrles.length)];
    return new DefaultMapEntity(position, new MapSize(0.3, 0.3), img);
  }

  private static MapEntity makeBarrel(double x, double y) {
    return makeBarrel(new MapPoint(x, y));
  }

  private static MapEntity makeBuilding(MapPoint position) {
    GameImage img = buildings[(int)(Math.random() * buildings.length)];
    return new DefaultMapEntity(position, new MapSize(2, 2), img);
  }

  private static MapEntity makeBuilding(int x, int y) {
    return makeBuilding(new MapPoint(x, y));
  }

  private static MapEntity makeTree(MapPoint position) {
    GameImage img = trees[(int)(Math.random() * trees.length)];
    return new DefaultMapEntity(position, new MapSize(1, 1), img);
  }

  private static MapEntity makeTree(double x, double y) {
    return makeTree(new MapPoint(x, y));
  }

  private static DefaultUnit makeFootNight(MapPoint position) {
    return new DefaultUnit(position, STANDARD_UNIT_SIZE, Team.PLAYER,
        new DefaultUnitSpriteSheet(FOOT_KNIGHT_SPRITE_SHEET), UnitType.SWORDSMAN
    );
  }

  private static DefaultUnit makeOrc(MapPoint position) {
    return new DefaultUnit(position, STANDARD_UNIT_SIZE, Team.ENEMY,
        new DefaultUnitSpriteSheet(ORC_SPEARMAN_SPRITE_SHEET), UnitType.SPEARMAN
    );
  }

  /**
   * Generates the rectangle of of entities that are around the edge (but inside) bounds.
   */
  public static Collection<MapEntity> generateBorderEntities(
      MapRect bounds,
      Function<MapPoint, MapEntity> entityGenerator
  ) {
    int left = (int) bounds.topLeft.x;
    int top = (int) bounds.topLeft.y;
    int right = (int) bounds.bottomRight.x;
    int bottom = (int) bounds.bottomRight.y;

    Collection<MapEntity> mapEntities = new ArrayList<>();

    // Generate top row
    for (int x = left; x < right; x++) {
      MapEntity newEntity = entityGenerator.apply(new MapPoint(x , top + (int)(Math.random() + 0.5)));
      mapEntities.add(newEntity);
      if (newEntity.getSize().width > 1) {
        x += newEntity.getSize().width - 1;
      }
    }
    // Generatebottom row
    for (int x = left; x < right; x++) {
      MapEntity newEntity = entityGenerator.apply(new MapPoint(x , bottom - 1  + (int)(Math.random() + 0.5)));
      mapEntities.add(newEntity);
      if (newEntity.getSize().width > 1) {
        x += newEntity.getSize().width - 1;
      }
    }

    // Generate right column
    for (int y = top + 1; y < bottom - 1; y++) {
      MapEntity newEntity = entityGenerator.apply(new MapPoint(right - 1  + (int)(Math.random() + 0.5), y ));
      mapEntities.add(newEntity);
      if (newEntity.getSize().width > 1) {
        y += newEntity.getSize().width - 1;
      }
    }
    // Generate left column excluding columns
    for (int y = top + 1; y < bottom - 1; y++) {
      MapEntity newEntity = entityGenerator.apply(new MapPoint(left +  (int)(Math.random() + 0.5), y));
      mapEntities.add(newEntity);
      if (newEntity.getSize().width > 1) {
        y += newEntity.getSize().width - 1;
      }
    }

    return mapEntities;
  }

  /**
   * Factory method for creating a new entity to be put on the border of a {@link Level} to stop
   * the user from leaving the area.
   */
  public static MapEntity newBorderEntityAt(MapPoint point) {
    return Math.random() > 0.5 ? makeTree(point) : makeBuilding(point);
  }

  @Override
  public World load() {
    return loadMultilevelWorld();
    //return loadProductionWorld();
  }

  /**
   * Creates a new {@link GameModel} with the single level and example data. This level doesn't have
   * a wall of {@link MapEntity}s around the bounds. This should have every non {@link
   * main.game.model.entity.Projectile} {@link Entity} in the {@link main.game.model.entity} package
   * for maximum coverage in tests.
   */
  public World loadSingleLevelTestWorld() {
    HeroUnit heroUnit = new DefaultHeroUnit(
        new MapPoint(1, 1),
        new MapSize(1, 1),
        new DefaultUnitSpriteSheet(GOLDEN_HERO_SPRITE_SHEET),
        UnitType.SWORDSMAN,
        Arrays.asList(
            new HealAbility(
                GameImageResource.WHITE_BALL_ITEM.getGameImage(),
                3,
                30
            )
        ),
        3
    );

    MapRect bounds = new MapRect(new MapPoint(0, 0), new MapPoint(10, 8));
    Level level = new Level(
        bounds,
        Arrays.asList(
            new DefaultUnit(new MapPoint(3, 2), STANDARD_UNIT_SIZE, Team.PLAYER,
                new DefaultUnitSpriteSheet(ARCHER_SPRITE_SHEET), UnitType.ARCHER
            ),
            new DefaultUnit(new MapPoint(8, 7), STANDARD_UNIT_SIZE, Team.ENEMY,
                new DefaultUnitSpriteSheet(SKELETON_ARCHER_SPRITE_SHEET), UnitType.ARCHER
            )
        ),
        Arrays.asList(
            new DefaultItem(
                new MapPoint(2, 2),
                new HealAbility(
                    GameImageResource.POTION_BLUE_ITEM.getGameImage(),
                    30,
                    30
                ),
                POTION_BLUE_ITEM.getGameImage()
            ),
            new DefaultItem(
                new MapPoint(3, 3),
                new DamageBuffAbility(
                    GameImageResource.RING_GOLD_ITEM.getGameImage(),
                    50,
                    15,
                    20
                ),
                RING_GOLD_ITEM.getGameImage()
            )
        ),
        Arrays.asList(
            new DefaultMapEntity(
                new MapPoint(2, 1), TREE_1.getGameImage()
            ),
            new DefaultMapEntity(
                new MapPoint(5, 5), TREE_1.getGameImage()
            )
        ),
        generateBorderEntities(bounds, DefaultWorldLoader::newBorderEntityAt),
        new Goal.AllEnemiesKilled(),
        "Maybe kill all the enemies"
    );

    return new World(Arrays.asList(level), heroUnit, new DefaultPathFinder());
  }

  /**
   * Create a complex enough level to play the game. For simplicity, the y axis has a fixed width
   * and the player moves along the x axis.
   */
  public World loadMultilevelWorld() {
    final HeroUnit heroUnit = new DefaultHeroUnit(
        new MapPoint(3, 5),
        STANDARD_UNIT_SIZE,
        new DefaultUnitSpriteSheet(GOLDEN_HERO_SPRITE_SHEET),
        UnitType.LASER,
        Arrays.asList(
            new HealAbility(
                GameImageResource.WHITE_BALL_ITEM.getGameImage(),
                120, 90
            )
        ),
        3
    );

    LinkedList<Level> levels = new LinkedList<>();

    MapRect bounds = new MapRect(new MapPoint(0, 0), new MapPoint(45, 11));
    {
      // Example level to allow the player to learn how to attack

      // Bounds is 11 high, so y == 5 is center
      levels.add(new Level(
          bounds,
          Arrays.asList(
              new DefaultUnit(new MapPoint(4, 4), STANDARD_UNIT_SIZE, Team.PLAYER,
                  new DefaultUnitSpriteSheet(FOOT_KNIGHT_SPRITE_SHEET), UnitType.SWORDSMAN,
                  1
              ),
              new DefaultUnit(new MapPoint(4, 6), STANDARD_UNIT_SIZE, Team.PLAYER,
                  new DefaultUnitSpriteSheet(FOOT_KNIGHT_SPRITE_SHEET), UnitType.SWORDSMAN,
                  1
              ),
              new DefaultUnit(new MapPoint(3, 4), STANDARD_UNIT_SIZE, Team.PLAYER,
                  new DefaultUnitSpriteSheet(SPEARMAN_SPRITE_SHEET), UnitType.SPEARMAN,
                  1
              ),
              new DefaultUnit(new MapPoint(3, 6), STANDARD_UNIT_SIZE, Team.PLAYER,
                  new DefaultUnitSpriteSheet(SPEARMAN_SPRITE_SHEET), UnitType.SPEARMAN,
                  1
              ),
              new DefaultUnit(new MapPoint(12, 5), STANDARD_UNIT_SIZE, Team.ENEMY,
                  new DefaultUnitSpriteSheet(ORC_SPEARMAN_SPRITE_SHEET), UnitType.SPEARMAN,
                  0
              ),
              new DefaultUnit(new MapPoint(11, 4), STANDARD_UNIT_SIZE, Team.ENEMY,
                  new DefaultUnitSpriteSheet(ORC_SPEARMAN_SPRITE_SHEET), UnitType.SPEARMAN,
                  0
              ),
              new DefaultUnit(new MapPoint(11, 6), STANDARD_UNIT_SIZE, Team.ENEMY,
                  new DefaultUnitSpriteSheet(ORC_SPEARMAN_SPRITE_SHEET), UnitType.SPEARMAN,
                  0
              )
          ),
          Arrays.asList(),
          generateBorderEntities(bounds, DefaultWorldLoader::newBorderEntityAt),
          Arrays.asList(
              makeBuilding(16, 3),
              makeBuilding(16, 5),
              makeBuilding(16, 7)
          ),
          new Goal.AllEnemiesKilled(),
          "Kill the enemy soldier with your hero and foot-knights"
      ));
    }

    {
      // A level with a few units, and items
      levels.add(new Level(
          bounds,
          Arrays.asList(
              new DefaultUnit(new MapPoint(8, 4), STANDARD_UNIT_SIZE, Team.PLAYER,
                  new DefaultUnitSpriteSheet(ARCHER_SPRITE_SHEET), UnitType.ARCHER,
                  1
              ),
              new DefaultUnit(new MapPoint(8, 5), STANDARD_UNIT_SIZE, Team.PLAYER,
                  new DefaultUnitSpriteSheet(ARCHER_SPRITE_SHEET), UnitType.ARCHER,
                  1
              ),
              new DefaultUnit(new MapPoint(10, 8), STANDARD_UNIT_SIZE, Team.PLAYER,
                  new DefaultUnitSpriteSheet(ARCHER_SPRITE_SHEET), UnitType.ARCHER
              ),
              new DefaultUnit(new MapPoint(22, 4), STANDARD_UNIT_SIZE, Team.ENEMY,
                  new DefaultUnitSpriteSheet(ORC_SPEARMAN_SPRITE_SHEET), UnitType.SPEARMAN,
                  1
              ),
              new DefaultUnit(new MapPoint(22, 5), STANDARD_UNIT_SIZE, Team.ENEMY,
                  new DefaultUnitSpriteSheet(ORC_SPEARMAN_SPRITE_SHEET), UnitType.SPEARMAN,
                  1
              ),
              new DefaultUnit(new MapPoint(22, 6), STANDARD_UNIT_SIZE, Team.ENEMY,
                  new DefaultUnitSpriteSheet(ORC_SPEARMAN_SPRITE_SHEET), UnitType.SPEARMAN,
                  1
              ),
              new DefaultUnit(new MapPoint(23, 4.5), STANDARD_UNIT_SIZE, Team.ENEMY,
                  new DefaultUnitSpriteSheet(SKELETON_ARCHER_SPRITE_SHEET), UnitType.ARCHER
              ),
              new DefaultUnit(new MapPoint(23, 5), STANDARD_UNIT_SIZE, Team.ENEMY,
                  new DefaultUnitSpriteSheet(SKELETON_ARCHER_SPRITE_SHEET), UnitType.ARCHER
              ),
              new DefaultUnit(new MapPoint(23, 5.5), STANDARD_UNIT_SIZE, Team.ENEMY,
                  new DefaultUnitSpriteSheet(SKELETON_ARCHER_SPRITE_SHEET), UnitType.ARCHER
              ),
              new DefaultUnit(new MapPoint(24, 4), STANDARD_UNIT_SIZE, Team.ENEMY,
                  new DefaultUnitSpriteSheet(SKELETON_ARCHER_SPRITE_SHEET), UnitType.ARCHER
              ),
              new DefaultUnit(new MapPoint(24, 5), STANDARD_UNIT_SIZE, Team.ENEMY,
                  new DefaultUnitSpriteSheet(SKELETON_ARCHER_SPRITE_SHEET), UnitType.ARCHER
              ),
              new DefaultUnit(new MapPoint(24, 6), STANDARD_UNIT_SIZE, Team.ENEMY,
                  new DefaultUnitSpriteSheet(SKELETON_ARCHER_SPRITE_SHEET), UnitType.ARCHER
              )

          ),
          Arrays.asList(
              new DefaultItem(
                  new MapPoint(21, 1),
                  new HealAbility(
                      GameImageResource.POTION_BLUE_ITEM.getGameImage(),
                      30, 30
                  ),
                  POTION_BLUE_ITEM.getGameImage()
              ),
              new DefaultItem(
                  new MapPoint(24, 5),
                  new DamageBuffAbility(
                      GameImageResource.RING_GOLD_ITEM.getGameImage(),
                      20, 15, 10
                  ),
                  RING_GOLD_ITEM.getGameImage()
              )
          ),
          Arrays.asList(
              makeBarrel(16, 7),
              makeBarrel(16.4, 7),
              makeBarrel(16, 7.5),
              makeBarrel(16, 6.4),
              makeBarrel(16.4, 6.2),
              makeBarrel(16.5, 7.5),
              makeBarrel(17, 8),
              makeBarrel(16.4, 8.5),
              makeBarrel(16.5, 9),
              makeBarrel(17, 9),
              makeBarrel(16, 3.5),
              makeTree(17, 3),
              makeTree(18, 4)


          ),
//          generateBorderEntities(bounds, DefaultWorldLoader::newBorderEntityAt),
          Arrays.asList(),
          new Goal.AllEnemiesKilled(),
          "Some Archer have joined your cause<br>Try out your new archers on the new enemies"
      ));
    }

    {
      // A level with more units
      levels.add(new Level(
          bounds,
          Arrays.asList(
              new DefaultUnit(new MapPoint(25, 4), STANDARD_UNIT_SIZE, Team.PLAYER,
                  new DefaultUnitSpriteSheet(MALE_MAGE_SPRITE_SHEET), UnitType.MAGICIAN
              ),
              new DefaultUnit(new MapPoint(25, 5), STANDARD_UNIT_SIZE, Team.PLAYER,
                  new DefaultUnitSpriteSheet(ARCHER_SPRITE_SHEET), UnitType.ARCHER
              ),
              new DefaultUnit(new MapPoint(26, 4), STANDARD_UNIT_SIZE, Team.PLAYER,
                  new DefaultUnitSpriteSheet(FOOT_KNIGHT_SPRITE_SHEET), UnitType.SWORDSMAN
              ),
              new DefaultUnit(new MapPoint(26, 6), STANDARD_UNIT_SIZE, Team.PLAYER,
                  new DefaultUnitSpriteSheet(FOOT_KNIGHT_SPRITE_SHEET), UnitType.SWORDSMAN
              ),
              new DefaultUnit(new MapPoint(25, 6), STANDARD_UNIT_SIZE, Team.PLAYER,
                  new DefaultUnitSpriteSheet(MALE_MAGE_SPRITE_SHEET), UnitType.MAGICIAN
              ),
              new DefaultUnit(new MapPoint(41, 3), STANDARD_UNIT_SIZE, Team.ENEMY,
                  new DefaultUnitSpriteSheet(SKELETON_ARCHER_SPRITE_SHEET), UnitType.ARCHER
              ),
              new DefaultUnit(new MapPoint(40, 4), STANDARD_UNIT_SIZE, Team.ENEMY,
                  new DefaultUnitSpriteSheet(ORC_SPEARMAN_SPRITE_SHEET), UnitType.SPEARMAN
              ),
              new DefaultUnit(new MapPoint(39, 5), HERO_SIZE, Team.ENEMY,
                  new DefaultUnitSpriteSheet(DARK_ELF_SPRITE_SHEET), UnitType.MAGICIAN
              ),
              new DefaultUnit(new MapPoint(40, 6), STANDARD_UNIT_SIZE, Team.ENEMY,
                  new DefaultUnitSpriteSheet(ORC_SPEARMAN_SPRITE_SHEET), UnitType.SPEARMAN
              ),
              new DefaultUnit(new MapPoint(41, 7), STANDARD_UNIT_SIZE, Team.ENEMY,
                  new DefaultUnitSpriteSheet(SKELETON_ARCHER_SPRITE_SHEET), UnitType.ARCHER
              )
          ),
          Arrays.asList(),
          Arrays.asList(
              new DefaultMapEntity(
                  new MapPoint(37, 1),
                  TREE_1.getGameImage()
              ),
              new DefaultMapEntity(
                  new MapPoint(37, 2),
                  TREE_1.getGameImage()
              ),
              new DefaultMapEntity(
                  new MapPoint(37, 3),
                  TREE_1.getGameImage()
              ),
              new DefaultMapEntity(
                  new MapPoint(37, 7),
                  TREE_1.getGameImage()
              ),
              new DefaultMapEntity(
                  new MapPoint(37, 8),
                  TREE_1.getGameImage()
              ),
              new DefaultMapEntity(
                  new MapPoint(37, 9),
                  TREE_1.getGameImage()
              )
          ),
          Arrays.asList(),
          new Goal.AllEnemiesKilled(),
          "Use the gold ring buff, and kill all enemies"
      ));
    }

    {
      // Surprise ambush level
      levels.add(new Level(
          bounds,
          Arrays.asList(
              new DefaultUnit(new MapPoint(39, 2), HERO_SIZE, Team.ENEMY,
                  new DefaultUnitSpriteSheet(DARK_ELF_SPRITE_SHEET), UnitType.MAGICIAN
              ),
              new DefaultUnit(new MapPoint(39, 8), HERO_SIZE, Team.ENEMY,
                  new DefaultUnitSpriteSheet(DARK_ELF_SPRITE_SHEET), UnitType.MAGICIAN
              ),
              new DefaultUnit(new MapPoint(43, 2), STANDARD_UNIT_SIZE, Team.ENEMY,
                  new DefaultUnitSpriteSheet(SKELETON_ARCHER_SPRITE_SHEET), UnitType.ARCHER
              ),
              new DefaultUnit(new MapPoint(43, 8), STANDARD_UNIT_SIZE, Team.ENEMY,
                  new DefaultUnitSpriteSheet(SKELETON_ARCHER_SPRITE_SHEET), UnitType.ARCHER
              ),
              new DefaultUnit(new MapPoint(43, 4), STANDARD_UNIT_SIZE, Team.ENEMY,
                  new DefaultUnitSpriteSheet(ORC_SPEARMAN_SPRITE_SHEET), UnitType.SPEARMAN
              ),
              new DefaultUnit(new MapPoint(43, 6), STANDARD_UNIT_SIZE, Team.ENEMY,
                  new DefaultUnitSpriteSheet(ORC_SPEARMAN_SPRITE_SHEET), UnitType.SPEARMAN
              )
          ),
          Arrays.asList(),
          Arrays.asList(),
          Arrays.asList(),
          new Goal.AllEnemiesKilled(),
          levels.getLast().getGoalDescription()
      ));
    }
    return new World(levels, heroUnit, new DefaultPathFinder());
  }

  /**
   * Loads a world with different assets.
   */
  public World loadProductionWorld() {
    final HeroUnit heroUnit = new DefaultHeroUnit(
        new MapPoint(38, 10),
        HERO_SIZE,
        new DefaultUnitSpriteSheet(MAGE_CAPE_SPRITE_SHEET),
        UnitType.MAGICIAN,
        Arrays.asList(
            new HealAbility(
                GameImageResource.WHITE_BALL_ITEM.getGameImage(),
                120, 90
            )
        ),
        3
    );
    LinkedList<Level> levels = new LinkedList<>();

    Level startingLevel = new Level(
        new MapRect(0,0,50,50),
        /* units */
        Arrays.asList(
            makeFootNight(new MapPoint(42, 10)),
            makeFootNight(new MapPoint(42, 12)),
            makeFootNight(new MapPoint(42, 13)),
            // Enemies
            makeOrc(new MapPoint(30, 20))
        ),
        /* items */
        Arrays.asList(),
        /* mapentities that stop next level */
        Arrays.asList(),
        /* mapentities that go arround border */
        Arrays.asList(
            this.makeBuilding(new MapPoint(40, 7)),
            this.makeBuilding(new MapPoint(36, 7)),
            this.makeBuilding(new MapPoint(34, 7)),
            this.makeBuilding(new MapPoint(30, 9)),
            this.makeBuilding(new MapPoint(27, 9)),
            this.makeBuilding(new MapPoint(34, 10)),
            this.makeBuilding(new MapPoint(30, 13)),
            this.makeBuilding(new MapPoint(27, 12)),
            this.makeBuilding(new MapPoint(30, 9)),
            this.makeBuilding(new MapPoint(25, 16)),
            this.makeBuilding(new MapPoint(24, 21)),
            this.makeBuilding(new MapPoint(30, 9)),
            this.makeBuilding(new MapPoint(30, 9)),
            this.makeBuilding(new MapPoint(25, 20)),
            this.makeBuilding(new MapPoint(18, 27)),
            this.makeBuilding(new MapPoint(19, 31)),
            this.makeBuilding(new MapPoint(19, 34)),
            this.makeBuilding(new MapPoint(19, 47)),
            this.makeBuilding(new MapPoint(21, 40)),
            this.makeBuilding(new MapPoint(23, 42)),
            this.makeBuilding(new MapPoint(26, 43)),
            this.makeBuilding(new MapPoint(30, 43)),
            this.makeBuilding(new MapPoint(33, 42)),
            this.makeBuilding(new MapPoint(37, 41)),
            this.makeBuilding(new MapPoint(39, 40)),
            this.makeBuilding(new MapPoint(42, 40)),
            this.makeBuilding(new MapPoint(42, 36)),
            this.makeBuilding(new MapPoint(44, 34)),
            this.makeBuilding(new MapPoint(43, 32)),
            this.makeBuilding(new MapPoint(45, 28)),
            this.makeBuilding(new MapPoint(43, 26)),
            this.makeBuilding(new MapPoint(43, 23)),
            this.makeBuilding(new MapPoint(41, 20)),
            this.makeBuilding(new MapPoint(39, 18)),
            this.makeBuilding(new MapPoint(37, 18)),
            this.makeBuilding(new MapPoint(33, 18)),
            this.makeBuilding(new MapPoint(37, 15)),
            this.makeBuilding(new MapPoint(41, 13)),
            // Center
            this.makeBuilding(new MapPoint(32, 24)),
            this.makeBuilding(new MapPoint(32, 28)),
            this.makeBuilding(new MapPoint(36, 27)),
            this.makeBuilding(new MapPoint(37, 31)),
            this.makeBuilding(new MapPoint(34, 31)),
            this.makeBuilding(new MapPoint(36, 34)),
            this.makeBuilding(new MapPoint(31, 34)),
            new DefaultMapEntity(new MapPoint(43, 9), BARRLE_1.getGameImage()),
            new DefaultMapEntity(new MapPoint(43, 10), BARRLE_2.getGameImage()),
            new DefaultMapEntity(new MapPoint(43, 11), BARRLE_3.getGameImage()),
            new DefaultMapEntity(new MapPoint(43, 12), BARRLE_4.getGameImage()),

            new DefaultMapEntity(new MapPoint(28, 30), new MapSize(2, 2), FOUNTAIN.getGameImage()),
            makeTree(new MapPoint(26, 30)),
            makeTree(new MapPoint(26, 33)),
            makeTree(new MapPoint(26, 36)),
            makeTree(new MapPoint(26, 39)),
            makeTree(new MapPoint(28, 34))
        ),
        /* Goal */
        new AllEnemiesKilled(),
        "Find and Destroy all the enemies"
    );

    levels.add(startingLevel);

    return new World(levels, heroUnit, new DefaultPathFinder());
  }
}
