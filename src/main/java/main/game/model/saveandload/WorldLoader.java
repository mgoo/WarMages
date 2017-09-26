package main.game.model.saveandload;

import java.io.IOException;
import java.util.Arrays;
import main.game.model.GameModel;
import main.game.model.Level;
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
 * positions in the {@link World}.
 */
public class WorldLoader {

  private final FileLoader fileLoader;

  public WorldLoader(FileLoader fileLoader) {
    this.fileLoader = fileLoader;
  }

  /**
   * Creates a new {@link GameModel} by loading from the file.
   */
  public World load(String filename) throws IOException {

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
            new UninteractableEntity(new MapPoint(2, 1), GameImageResource.TEST_IMAGE_FULL_SIZE.getGameImage()),
            new UninteractableEntity(new MapPoint(5, 5), GameImageResource.TEST_IMAGE_FULL_SIZE.getGameImage())
        ),
        gameModel -> false, // Never complete this level for now
        "Maybe kill all the enemies or something I don't know"
    );

    return new World(Arrays.asList(level), heroUnit);
  }

  /**
   * Adapter around the filesystem for loading resource files.
   */
  public interface FileLoader {

    /**
     * Loads the file contents of the given name.
     *
     * @param filename Filename without any slashes.
     * @return The file contents
     */
    String load(String filename) throws IOException;

  }

  /**
   * The implementation of {@link FileLoader} that should be passed into {@link WorldLoader}'s
   * constructor in the app.
   */
  public class DefaultFileLoader implements FileLoader {

    @Override
    public String load(String filename) throws IOException {
      throw new Error("NYI");
    }

  }
}
