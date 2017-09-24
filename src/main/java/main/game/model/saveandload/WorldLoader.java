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
import main.game.model.entity.Unit;
import main.game.model.world.World;
import main.util.MapPoint;
import main.util.MapRect;

/**
 * Creates a new [@link {@link World} and it's required {@link Entity} objects in the default
 * positions in the {@link World}.
 */
public class WorldLoader {

  /**
   * Creates a new {@link GameModel} with the single level and example data.
   */
  public static World newSingleLevelTestGame() { // todo
    // NOTE: All of these values, including the sizes, are just temporarily values.

    // NOTE: Does not currently load from the file.
    // This code is temporary so it is easier to test other parts of the system.

    // NOTE 2: All of these values, including the sizes, are just temporarily values.

    HeroUnit heroUnit = new HeroUnit(new MapPoint(0, 0), 1, Team.PLAYER);

    Level level = new Level(
        Arrays.asList(
            new Unit(new MapPoint(3, 0), 0.5f, Team.PLAYER),
            new Unit(new MapPoint(9, 7), 0.5f, Team.ENEMY)
        ),
        Arrays.asList(
            new HealingItem(new MapPoint(2, 2), 0.2f),
            new BuffItem(new MapPoint(3, 3), 0.2f)
        ),
        Arrays.asList(
            new MapEntity(new MapPoint(2, 1), 0.1f),
            new MapEntity(new MapPoint(5, 5), 0.1f)
        ),
        gameModel -> false, // Never complete this level for now
        "Maybe kill all the enemies or something I don't know"
    );

    return new World(Arrays.asList(level), heroUnit);
  }

  private final FileLoader fileLoader;

  public WorldLoader(FileLoader fileLoader) {
    this.fileLoader = fileLoader;
  }

  /**
   * Creates a new {@link World} by loading from the file.
   */
  public World load(String filename) throws IOException {

    // NOTE: Does not currently load from the file.
    // This code is temporary so it is easier to test other parts of the system.

    return newSingleLevelTestGame();
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
