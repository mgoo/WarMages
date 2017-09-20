package main.gamemodel.loader;

import java.io.IOException;

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
