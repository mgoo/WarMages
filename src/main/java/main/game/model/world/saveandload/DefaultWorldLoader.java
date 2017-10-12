package main.game.model.world.saveandload;

import static main.common.images.GameImageResource.ARCHER_SPRITE_SHEET;
import static main.common.images.GameImageResource.DARK_ELF_SPRITE_SHEET;
import static main.common.images.GameImageResource.FOOT_KNIGHT_SPRITE_SHEET;
import static main.common.images.GameImageResource.GOLDEN_HERO_SPRITE_SHEET;
import static main.common.images.GameImageResource.MALE_MAGE_SPRITE_SHEET;
import static main.common.images.GameImageResource.ORC_SPEARMAN_SPRITE_SHEET;
import static main.common.images.GameImageResource.POTION_BLUE_ITEM;
import static main.common.images.GameImageResource.RING_GOLD_ITEM;
import static main.common.images.GameImageResource.SKELETON_ARCHER_SPRITE_SHEET;
import static main.common.images.GameImageResource.TREE_MAP_ENTITY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Function;
import main.common.WorldLoader;
import main.common.images.GameImageResource;
import main.common.util.MapPoint;
import main.common.util.MapRect;
import main.common.util.MapSize;
import main.game.model.GameModel;
import main.game.model.Level;
import main.game.model.Level.Goal;
import main.common.Entity;
import main.common.HeroUnit;
import main.common.MapEntity;
import main.game.model.entity.DefaultUninteractableEntity;
import main.game.model.entity.unit.state.DefaultHeroUnit;
import main.game.model.entity.unit.DefaultUnit;
import main.common.Team;
import main.game.model.entity.unit.UnitType;
import main.game.model.entity.usable.DamageBuffAbility;
import main.game.model.entity.usable.HealAbility;
import main.game.model.entity.usable.Item;
import main.game.model.world.World;
import main.game.model.world.pathfinder.DefaultPathFinder;
import main.images.DefaultUnitSpriteSheet;

public class DefaultWorldLoader implements WorldLoader {

  private static final MapSize HERO_SIZE = new MapSize(1, 1);
  private static final MapSize STANDARD_UNIT_SIZE = new MapSize(0.7, 0.7);

  /**
   * Generates the rectangle of of entities that are around the edge (but inside) bounds.
   */
  public static Collection<DefaultUninteractableEntity> generateBorderEntities(
      MapRect bounds,
      Function<MapPoint, DefaultUninteractableEntity> entityGenerator
  ) {
    int left = (int) bounds.topLeft.x;
    int top = (int) bounds.topLeft.y;
    int right = (int) bounds.bottomRight.x;
    int bottom = (int) bounds.bottomRight.y;

    Collection<DefaultUninteractableEntity> mapEntities = new ArrayList<>();

    // Generate top and bottom rows
    for (int x = left; x < right; x++) {
      mapEntities.add(entityGenerator.apply(new MapPoint(x, top)));
      mapEntities.add(entityGenerator.apply(new MapPoint(x, bottom - 1)));
    }

    // Generate left and right columns, excluding corners
    for (int y = top + 1; y < bottom - 1; y++) {
      mapEntities.add(entityGenerator.apply(new MapPoint(left, y)));
      mapEntities.add(entityGenerator.apply(new MapPoint(right - 1, y)));
    }

    return mapEntities;
  }

  /**
   * Factory method for creating a new entity to be put on the border of a {@link Level} to stop
   * the user from leaving the area.
   */
  public static DefaultUninteractableEntity newBorderEntityAt(MapPoint point) {
    return new DefaultUninteractableEntity(
        point,
        TREE_MAP_ENTITY.getGameImage()
    );
  }

  @Override
  public World load() {
    return loadMultilevelWorld();
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
        )
    );

    MapRect bounds = new MapRect(new MapPoint(0, 0), new MapPoint(10, 8));
    Level level = new Level(
        bounds,
        Arrays.asList(
            new DefaultUnit(new MapPoint(3, 0), STANDARD_UNIT_SIZE, Team.PLAYER,
                new DefaultUnitSpriteSheet(ARCHER_SPRITE_SHEET), UnitType.ARCHER
            ),
            new DefaultUnit(new MapPoint(9, 7), STANDARD_UNIT_SIZE, Team.ENEMY,
                new DefaultUnitSpriteSheet(SKELETON_ARCHER_SPRITE_SHEET), UnitType.ARCHER
            )
        ),
        Arrays.asList(
            new Item(
                new MapPoint(2, 2),
                new HealAbility(
                    GameImageResource.POTION_BLUE_ITEM.getGameImage(),
                    30,
                    30
                ),
                POTION_BLUE_ITEM.getGameImage()
            ),
            new Item(
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
            new DefaultUninteractableEntity(
                new MapPoint(2, 1), TREE_MAP_ENTITY.getGameImage()
            ),
            new DefaultUninteractableEntity(
                new MapPoint(5, 5), TREE_MAP_ENTITY.getGameImage()
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
        new MapPoint(2, 5),
        HERO_SIZE,
        new DefaultUnitSpriteSheet(GOLDEN_HERO_SPRITE_SHEET),
        UnitType.SWORDSMAN,
        Arrays.asList(
            new HealAbility(
                GameImageResource.WHITE_BALL_ITEM.getGameImage(),
                120, 90
            )
        )
    );
    LinkedList<Level> levels = new LinkedList<>();

    {
      // Example level to allow the player to learn how to attack
      MapRect bounds = new MapRect(new MapPoint(0, 0), new MapPoint(15, 11));
      // Bounds is 11 high, so y == 5 is center
      levels.add(new Level(
          bounds,
          Arrays.asList(
              new DefaultUnit(new MapPoint(2, 4), STANDARD_UNIT_SIZE, Team.PLAYER,
                  new DefaultUnitSpriteSheet(FOOT_KNIGHT_SPRITE_SHEET), UnitType.SWORDSMAN
              ),
              new DefaultUnit(new MapPoint(2, 6), STANDARD_UNIT_SIZE, Team.PLAYER,
                  new DefaultUnitSpriteSheet(FOOT_KNIGHT_SPRITE_SHEET), UnitType.SWORDSMAN
              ),
              new DefaultUnit(new MapPoint(12, 5), STANDARD_UNIT_SIZE, Team.ENEMY,
                  new DefaultUnitSpriteSheet(ORC_SPEARMAN_SPRITE_SHEET), UnitType.SPEARMAN
              )
          ),
          Arrays.asList(),
          Arrays.asList(
              new DefaultUninteractableEntity(
                  bounds.getCenter().floored(),
                  TREE_MAP_ENTITY.getGameImage()
              )
          ),
          generateBorderEntities(bounds, DefaultWorldLoader::newBorderEntityAt),
          new Goal.AllEnemiesKilled(),
          "Kill the enemy soldier with your hero and foot-knights"
      ));
    }

    {
      // A level with a few units, and items
      MapRect bounds = new MapRect(
          levels.getFirst().getBounds().topLeft,
          new MapPoint(30, levels.getFirst().getBounds().bottomRight.y)
      );
      levels.add(new Level(
          bounds,
          Arrays.asList(
              new DefaultUnit(new MapPoint(10, 2), STANDARD_UNIT_SIZE, Team.PLAYER,
                  new DefaultUnitSpriteSheet(ARCHER_SPRITE_SHEET), UnitType.ARCHER
              ),
              new DefaultUnit(new MapPoint(10, 8), STANDARD_UNIT_SIZE, Team.PLAYER,
                  new DefaultUnitSpriteSheet(ARCHER_SPRITE_SHEET), UnitType.ARCHER
              ),
              new DefaultUnit(new MapPoint(23, 4), STANDARD_UNIT_SIZE, Team.ENEMY,
                  new DefaultUnitSpriteSheet(ORC_SPEARMAN_SPRITE_SHEET), UnitType.SPEARMAN
              ),
              new DefaultUnit(new MapPoint(23, 5), STANDARD_UNIT_SIZE, Team.ENEMY,
                  new DefaultUnitSpriteSheet(SKELETON_ARCHER_SPRITE_SHEET), UnitType.ARCHER
              ),
              new DefaultUnit(new MapPoint(23, 6), STANDARD_UNIT_SIZE, Team.ENEMY,
                  new DefaultUnitSpriteSheet(ORC_SPEARMAN_SPRITE_SHEET), UnitType.SPEARMAN
              )
          ),
          Arrays.asList(
              new Item(
                  new MapPoint(21, 1),
                  new HealAbility(
                      GameImageResource.POTION_BLUE_ITEM.getGameImage(),
                      30, 30
                  ),
                  POTION_BLUE_ITEM.getGameImage()
              ),
              new Item(
                  new MapPoint(24, 5),
                  new DamageBuffAbility(
                      GameImageResource.RING_GOLD_ITEM.getGameImage(),
                      20, 15, 10
                  ),
                  RING_GOLD_ITEM.getGameImage()
              )
          ),
          Arrays.asList(
              new DefaultUninteractableEntity(
                  bounds.getCenter().floored(),
                  TREE_MAP_ENTITY.getGameImage()
              )
          ),
          generateBorderEntities(bounds, DefaultWorldLoader::newBorderEntityAt),
          new Goal.AllEnemiesKilled(),
          "Try out your new archers on the new enemies"
      ));
    }

    {
      // A level with more units
      MapRect bounds = new MapRect(
          levels.getFirst().getBounds().topLeft,
          new MapPoint(45, levels.getFirst().getBounds().bottomRight.y)
      );
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
              new DefaultUninteractableEntity(
                  new MapPoint(37, 1),
                  TREE_MAP_ENTITY.getGameImage()
              ),
              new DefaultUninteractableEntity(
                  new MapPoint(37, 2),
                  TREE_MAP_ENTITY.getGameImage()
              ),
              new DefaultUninteractableEntity(
                  new MapPoint(37, 3),
                  TREE_MAP_ENTITY.getGameImage()
              ),
              new DefaultUninteractableEntity(
                  new MapPoint(37, 7),
                  TREE_MAP_ENTITY.getGameImage()
              ),
              new DefaultUninteractableEntity(
                  new MapPoint(37, 8),
                  TREE_MAP_ENTITY.getGameImage()
              ),
              new DefaultUninteractableEntity(
                  new MapPoint(37, 9),
                  TREE_MAP_ENTITY.getGameImage()
              )
          ),
          generateBorderEntities(bounds, DefaultWorldLoader::newBorderEntityAt),
          new Goal.AllEnemiesKilled(),
          "Use the gold ring buff, and kill all enemies"
      ));
    }

    {
      // Surprise ambush level
      MapRect bounds = levels.getLast().getBounds();
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
          generateBorderEntities(bounds, DefaultWorldLoader::newBorderEntityAt),
          new Goal.AllEnemiesKilled(),
          levels.getLast().getGoalDescription()
      ));
    }

    return new World(levels, heroUnit, new DefaultPathFinder());
  }

}
