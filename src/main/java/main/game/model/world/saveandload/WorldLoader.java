package main.game.model.world.saveandload;

import java.util.Arrays;
import main.game.model.GameModel;
import main.game.model.Level;
import main.game.model.entity.BuffItem;
import main.game.model.entity.Entity;
import main.game.model.entity.HealingItem;
import main.game.model.entity.HeroUnit;
import main.game.model.entity.Team;
import main.game.model.entity.UninteractableEntity;
import main.game.model.entity.Unit;
import main.game.model.entity.UnitType;
import main.game.model.world.World;
import main.images.GameImageResource;
import main.images.UnitSpriteSheet;
import main.util.MapPoint;
import main.util.MapSize;

/**
 * Creates a new [@link {@link World} and it's required {@link Entity} objects in the default
 * positions in the {@link World}.
 * <p>
 * NOTE: We decided not to load the world from a file for now because that does not provide any
 * improvements to the game or requirements of the game.
 * </p>
 */
public class WorldLoader {

  /**
   * Creates a new {@link GameModel} with the single level and example data.
   */
  public static World newSingleLevelTestWorld() {
    // NOTE: All of these values, including the sizes, are just temporarily values.

    // NOTE: Does not currently load from the file.
    // This code is temporary so it is easier to test other parts of the system.

    // NOTE 2: All of these values, including the sizes, are just temporarily values.

    HeroUnit heroUnit = new HeroUnit(new MapPoint(0, 0), new MapSize(0.5, 0.5), new UnitSpriteSheet(
        GameImageResource.MALE_MAGE_SPRITE_SHEET), UnitType.ARCHER);

    Level level = new Level(
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
                GameImageResource.TEST_IMAGE_FULL_SIZE.getGameImage()),
            new UninteractableEntity(new MapPoint(5, 5),
                GameImageResource.TEST_IMAGE_FULL_SIZE.getGameImage())
        ),
        gameModel -> false, // Never complete this level for now
        "Maybe kill all the enemies or something I don't know"
    );

    return new World(Arrays.asList(level), heroUnit);
  }

  /**
   * Creates a new {@link World}.
   */
  public World load() {
    // TODO create a more complex world/levels
    return newSingleLevelTestWorld();
  }

}
