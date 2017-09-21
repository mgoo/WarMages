package main.game.model.saveandload;

import java.io.IOException;
import main.game.model.GameModel;

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
