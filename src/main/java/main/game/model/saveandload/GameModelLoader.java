package main.game.model.saveandload;

import java.io.IOException;
import main.game.model.GameModel;
import main.game.model.world.World;

/**
 * Creates a new [@link GameModel} and it's required {@link World}, along with {@link Entity}
 * objects in the default positions in the {@link World}.
 */
public class GameModelLoader {

  public GameModelLoader(FileLoader fileLoader) {
    throw new Error("NYI");
  }

  public GameModel load(String filename) throws IOException {
    throw new Error("NYI");
  }

  public interface FileLoader {

    String load(String filename) throws IOException;
  }
}

