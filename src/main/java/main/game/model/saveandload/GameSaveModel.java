package main.game.model.saveandload;

import java.io.IOException;
import java.util.Collection;

public class GameSaveModel {
  public GameSaveModel(Filesystem filesystem) {
  }
  public void save(GameModel gameModel, String filename) throws IOException {
    throw new Error("NYI");
  }

  public GameModel load(String filename) throws IOException {
    throw new Error("NYI");
  }

  public Collection<String> getExistingGameSaves() throws IOException {
    throw new Error("NYI");
  }

  public interface Filesystem {
    String load(String filename) throws IOException;
    Collection<String> availableFilenames() throws IOException;
    void save(String filename, String fileData) throws IOException;
  }
}

