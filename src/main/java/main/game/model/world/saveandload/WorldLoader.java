package main.game.model.world.saveandload;

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
import main.images.GameImageResource;
import main.images.UnitSpriteSheet;
import main.util.MapPoint;
import main.util.MapRect;
import main.util.MapSize;

/**
 * Creates a new [@link {@link World} and it's required {@link Entity} objects in the default
 * positions in the {@link World}. <p> NOTE: We decided not to load the world from a file for now
 * because that does not provide any improvements to the game or requirements of the game. </p>
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

  public static MapEntity newBorderEntityAt(MapPoint point) {
    return new MapEntity(point, 0.5f);
  }

  /**
   * Creates the default {@link World}.
   */
  public World load() {
    return loadMultilevelWorld();
  }

  /**
   * Creates a new {@link GameModel} with the single level and example data. This level doesn't have
   * a wall of {@link MapEntity}s around the bounds.
   */
  public World loadSingleLevelTestWorld() {
    HeroUnit heroUnit = new HeroUnit(
        new MapPoint(0, 0),
        new MapSize(0.5, 0.5),
        new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER
    );

    MapRect bounds = new MapRect(new MapPoint(0, 0), new MapPoint(10, 8));
    Level level = new Level(
        bounds,
        Arrays.asList(
            new Unit(new MapPoint(3, 0), new MapSize(0.5, 0.5), Team.PLAYER, new UnitSpriteSheet(
                GameImageResource.MALE_MAGE_SPRITE_SHEET), UnitType.ARCHER),
            new Unit(new MapPoint(9, 7), new MapSize(0.5, 0.5), Team.ENEMY, new UnitSpriteSheet(
                GameImageResource.MALE_MAGE_SPRITE_SHEET), UnitType.ARCHER)
        ),
        Arrays.asList(
            new HealingItem(new MapPoint(2, 2)),
            new BuffItem(new MapPoint(3, 3))
        ),
        Arrays.asList(
            new UninteractableEntity(new MapPoint(2, 1),
                GameImageResource.TEST_IMAGE_FULL_SIZE.getGameImage()
            ),
            new UninteractableEntity(new MapPoint(5, 5),
                GameImageResource.TEST_IMAGE_FULL_SIZE.getGameImage()
            )
        ),
        generateBorderEntities(bounds, WorldLoader::newBorderEntityAt),
        new Goal.AllEnemiesKilled(),
        "Maybe kill all the enemies"
    );

    return new World(Arrays.asList(level), heroUnit);
  }

  /**
   * Create a complex enough level to play the game. For simplicity, the y axis has a
   * fixed width and the player moves along the x axis.
   */
  public World loadMultilevelWorld() {
    HeroUnit heroUnit = new HeroUnit(new MapPoint(3, 4), 1, Team.PLAYER);
    LinkedList<Level> levels = new LinkedList<>();

    {
      // Example level to allow the player to learn how to attack
      MapRect bounds = new MapRect(new MapPoint(0, 0), new MapPoint(15, 10));
      levels.add(new Level(
          bounds,
          Arrays.asList(
              new Unit(new MapPoint(2, 3), 0.5f, Team.PLAYER),
              new Unit(new MapPoint(2, 4), 0.5f, Team.PLAYER),
              new Unit(new MapPoint(2, 5), 0.5f, Team.PLAYER),
              new Unit(new MapPoint(2, 6), 0.5f, Team.PLAYER),
              new Unit(new MapPoint(2, 7), 0.5f, Team.PLAYER),
              new Unit(new MapPoint(2, 8), 0.5f, Team.PLAYER),
              new Unit(new MapPoint(8, 8), 0.5f, Team.ENEMY)
          ),
          Arrays.asList(),
          Arrays.asList(
              new MapEntity(bounds.getCenter().rounded(), 0.1f)
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
              new Unit(new MapPoint(23, 4), 0.5f, Team.ENEMY),
              new Unit(new MapPoint(23, 5), 0.5f, Team.ENEMY),
              new Unit(new MapPoint(23, 6), 0.5f, Team.ENEMY)
          ),
          Arrays.asList(
              new HealingItem(new MapPoint(21, 1), 0.2f),
              new BuffItem(new MapPoint(24, 5), 0.2f)
          ),
          Arrays.asList(
              new MapEntity(bounds.getCenter().rounded(), 0.1f)
          ),
          generateBorderEntities(bounds, WorldLoader::newBorderEntityAt),
          new Goal.AllEnemiesKilled(),
          "Kill all enemy soldiers!"
      ));
    }

    return new World(levels, heroUnit);
  }

}
