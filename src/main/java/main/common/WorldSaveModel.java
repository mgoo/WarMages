package main.common;

import java.io.IOException;
import java.util.Collection;
import main.game.model.GameModel;
import main.game.model.world.World;

/**
 * Deals with the saving and loading of {@link GameModel} to and from files.
 * @author chongdyla
 */
public interface WorldSaveModel {


  /**
   * This is public for testing only. File extension for saved files.
   */
  String SAVE_FILE_EXTENSION = "sav";
  /**
   * Place to save and load all the files. If you change this remember to update the .gitignore
   * file.
   */
  String SAVE_FILE_DIRECTORY = "./saves/";

  /**
   * Stores the {@link main.game.model.world.World} (through serialisation).
   *
   * @param filename Name with no slashes is it.
   */
  void save(World world, String filename)
      throws IOException;

  /**
   * Serialises is the game saved under filename.
   * @throws IOException E.g. when the file doesn't exist, or data is not deserialisable.
   */
  World load(String filename) throws IOException, ClassNotFoundException;

  /**
   * Finds the file names of all the existing game saves. Each name has a SAVE_FILE_EXTENSION and
   * does not contain any slashes.
   */
  Collection<String> getExistingGameSaves();
}
