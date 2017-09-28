package main.game.model.world.saveandload;

import static main.images.GameImageResource.ARCHER_SPRITE_SHEET;
import static main.images.GameImageResource.FOOT_KNIGHT_SPRITE_SHEET;
import static main.images.GameImageResource.GOLDEN_HERO_SPRITE_SHEET;
import static main.images.GameImageResource.MALE_MAGE_SPRITE_SHEET;
import static main.images.GameImageResource.ORC_SPEARMAN_SPRITE_SHEET;
import static main.images.GameImageResource.POTION_BLUE_ITEM;
import static main.images.GameImageResource.RING_BLUE_ITEM;
import static main.images.GameImageResource.SKELETON_ARCHER_SPRITE_SHEET;
import static main.images.GameImageResource.TEST_IMAGE_FULL_SIZE;
import static main.images.GameImageResource.TREE_MAP_ENTITY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Function;
import main.game.model.GameModel;
import main.game.model.Level;
import main.game.model.Level.Goal;
import main.game.model.entity.BuffItem;
import main.game.model.entity.Entity;
import main.game.model.entity.HealingItem;
import main.game.model.entity.HeroUnit;
import main.game.model.entity.MapEntity;
import main.game.model.entity.Team;
import main.game.model.entity.UninteractableEntity;
import main.game.model.entity.Unit;
import main.game.model.entity.UnitType;
import main.game.model.world.World;
import main.images.UnitSpriteSheet;
import main.util.MapPoint;
import main.util.MapRect;
import main.util.MapSize;

/**
 * Creates a new [@link {@link World} and it's required {@link Entity} objects in the default
 * positions in the {@link World}.
 * <p>
 * NOTE: We decided not to load the world from a file for now
 * because that does not provide any improvements to the game or requirements of the game.
 * </p>
 */
public class WorldLoader {

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
  public static MapEntity newBorderEntityAt(MapPoint point) {
    return new UninteractableEntity(
        point,
        TREE_MAP_ENTITY.getGameImage()
    );
  }

  /**
   * Creates the default {@link World}.
   */
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
    HeroUnit heroUnit = new HeroUnit(
        new MapPoint(1, 1),
        new MapSize(1, 1),
        new UnitSpriteSheet(GOLDEN_HERO_SPRITE_SHEET),
        UnitType.SWORDSMAN
    );

    MapRect bounds = new MapRect(new MapPoint(0, 0), new MapPoint(10, 8));
    Level level = new Level(
        bounds,
        Arrays.asList(
            new Unit(new MapPoint(3, 0), new MapSize(0.5, 0.5), Team.PLAYER,
                new UnitSpriteSheet(ARCHER_SPRITE_SHEET), UnitType.ARCHER
            ),
            new Unit(new MapPoint(9, 7), new MapSize(0.5, 0.5), Team.ENEMY,
                new UnitSpriteSheet(SKELETON_ARCHER_SPRITE_SHEET), UnitType.ARCHER
            )
        ),
        Arrays.asList(
            new HealingItem(new MapPoint(2, 2), POTION_BLUE_ITEM.getGameImage()),
            new BuffItem(new MapPoint(3, 3), RING_BLUE_ITEM.getGameImage())
        ),
        Arrays.asList(
            new UninteractableEntity(
                new MapPoint(2, 1), TREE_MAP_ENTITY.getGameImage()
            ),
            new UninteractableEntity(
                new MapPoint(5, 5), TREE_MAP_ENTITY.getGameImage()
            )
        ),
        generateBorderEntities(bounds, WorldLoader::newBorderEntityAt),
        new Goal.AllEnemiesKilled(),
        "Maybe kill all the enemies"
    );

    return new World(Arrays.asList(level), heroUnit);
  }

  /**
   * Create a complex enough level to play the game. For simplicity, the y axis has a fixed width
   * and the player moves along the x axis.
   */
  public World loadMultilevelWorld() {
    HeroUnit heroUnit = new HeroUnit(
        new MapPoint(3, 4),
        new MapSize(1, 1),
        new UnitSpriteSheet(GOLDEN_HERO_SPRITE_SHEET),
        UnitType.SWORDSMAN
    );
    LinkedList<Level> levels = new LinkedList<>();

    {
      // Example level to allow the player to learn how to attack
      MapRect bounds = new MapRect(new MapPoint(0, 0), new MapPoint(15, 10));
      levels.add(new Level(
          bounds,
          Arrays.asList(
              new Unit(new MapPoint(2, 3), new MapSize(0.5, 0.5), Team.PLAYER,
                  new UnitSpriteSheet(ARCHER_SPRITE_SHEET), UnitType.ARCHER
              ),
              new Unit(new MapPoint(2, 4), new MapSize(0.5, 0.5), Team.PLAYER,
                  new UnitSpriteSheet(ARCHER_SPRITE_SHEET), UnitType.ARCHER
              ),
              new Unit(new MapPoint(2, 5), new MapSize(0.5, 0.5), Team.PLAYER,
                  new UnitSpriteSheet(FOOT_KNIGHT_SPRITE_SHEET), UnitType.SWORDSMAN
              ),
              new Unit(new MapPoint(2, 6), new MapSize(0.5, 0.5), Team.PLAYER,
                  new UnitSpriteSheet(FOOT_KNIGHT_SPRITE_SHEET), UnitType.SWORDSMAN
              ),
              new Unit(new MapPoint(2, 7), new MapSize(0.5, 0.5), Team.PLAYER,
                  new UnitSpriteSheet(ARCHER_SPRITE_SHEET), UnitType.ARCHER
              ),
              new Unit(new MapPoint(2, 8), new MapSize(0.5, 0.5), Team.PLAYER,
                  new UnitSpriteSheet(ARCHER_SPRITE_SHEET), UnitType.ARCHER
              ),
              new Unit(new MapPoint(8, 8), new MapSize(0.5, 0.5), Team.ENEMY,
                  new UnitSpriteSheet(ORC_SPEARMAN_SPRITE_SHEET), UnitType.SPEARMAN
              )
          ),
          Arrays.asList(),
          Arrays.asList(
              new UninteractableEntity(
                  bounds.getCenter().rounded(),
                  TREE_MAP_ENTITY.getGameImage()
              )
          ),
          generateBorderEntities(bounds, WorldLoader::newBorderEntityAt),
          new Goal.AllEnemiesKilled(),
          "Kill the enemy soldier with your units"
      ));
    }

    {
      // A level with more units
      MapRect bounds = new MapRect(
          levels.getFirst().getBounds().topLeft,
          new MapPoint(30, 10)
      );
      levels.add(new Level(
          bounds,
          Arrays.asList(
              new Unit(new MapPoint(15, 3), new MapSize(0.5, 0.5), Team.PLAYER,
                  new UnitSpriteSheet(MALE_MAGE_SPRITE_SHEET), UnitType.MAGICIAN
              ),
              new Unit(new MapPoint(15, 3), new MapSize(0.5, 0.5), Team.PLAYER,
                  new UnitSpriteSheet(MALE_MAGE_SPRITE_SHEET), UnitType.MAGICIAN
              ),
              new Unit(new MapPoint(23, 4), new MapSize(0.5, 0.5), Team.ENEMY,
                  new UnitSpriteSheet(ORC_SPEARMAN_SPRITE_SHEET), UnitType.SWORDSMAN
              ),
              new Unit(new MapPoint(23, 5), new MapSize(0.5, 0.5), Team.ENEMY,
                  new UnitSpriteSheet(SKELETON_ARCHER_SPRITE_SHEET), UnitType.ARCHER
              ),
              new Unit(new MapPoint(23, 6), new MapSize(0.5, 0.5), Team.ENEMY,
                  new UnitSpriteSheet(ORC_SPEARMAN_SPRITE_SHEET), UnitType.SPEARMAN
              )
          ),
          Arrays.asList(
              new HealingItem(new MapPoint(21, 1), POTION_BLUE_ITEM.getGameImage()),
              new BuffItem(new MapPoint(24, 5), RING_BLUE_ITEM.getGameImage())
          ),
          Arrays.asList(
              new UninteractableEntity(
                  bounds.getCenter().rounded(),
                  TEST_IMAGE_FULL_SIZE.getGameImage()
              )
          ),
          generateBorderEntities(bounds, WorldLoader::newBorderEntityAt),
          new Goal.AllEnemiesKilled(),
          "Kill all enemy soldiers!"
      ));
    }

    return new World(levels, heroUnit);
  }

}
