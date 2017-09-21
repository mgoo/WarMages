package main.game.model.saveandload;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import main.game.model.GameModel;

/**
 * Deals with the saving and loading of {@link GameModel} to and from files.
 */
public class GameSaveModel {

  /**
   * File extension for saved files.
   */
  public static final String SAVE_FILE_EXTENSION = "sav";

  /**
   * Place to save and load all the files.
   * If you change this remember to update the .gitignore file.
   */
  public static final String SAVE_FILE_DIRECTORY = "./saves/";

  private final Filesystem filesystem;

  public GameSaveModel(Filesystem filesystem) {
    this.filesystem = filesystem;
  }

  public void save(GameModel gameModel, String filename) throws IOException {
    throw new Error("NYI");
  }

  public GameModel load(String filename) throws IOException {
    throw new Error("NYI");
  }

  public Collection<String> getExistingGameSaves() throws IOException {
    return filesystem.availableFilenames()
        .stream()
        .filter(filename -> filename.endsWith(SAVE_FILE_EXTENSION))
        .sorted()
        .collect(Collectors.toList());
  }

  /**
   * An adapter around the computer's file system.
   */
  public interface Filesystem {

    /**
     * @param filename Filename without any slashes.
     * @return The text contents of the file.
     */
    String load(String filename) throws IOException;

    Collection<String> availableFilenames() throws IOException;

    void save(String filename, String fileData) throws IOException;
  }

  public static class DefaultFilesystem implements Filesystem {

    private final String saveDirectory;

    public DefaultFilesystem() {
      this(SAVE_FILE_DIRECTORY);
    }

    public DefaultFilesystem(String saveDirectory) {
      if (!saveDirectory.endsWith("/")) {
        throw new IllegalArgumentException();
      }

      this.saveDirectory = saveDirectory;
    }

    @Override
    public String load(String filename) throws IOException {
      File file = getFile(filename);

      return Files.readAllLines(file.toPath())
          .stream()
          .reduce(String::concat)
          .orElse("");
    }

    @Override
    public Collection<String> availableFilenames() throws IOException {
      File[] files = new File(saveDirectory)
          .listFiles(File::isFile);

      if (files == null) {
        throw new IOException(
            "There was some error loading the files in the current directory"
        );
      }

      return Arrays.stream(files)
          .map(File::getName)
          .collect(Collectors.toList());
    }

    @Override
    public void save(String filename, String fileData) throws IOException {
      File file = getFile(filename);
      file.getParentFile().mkdirs();

      try (PrintWriter writer = new PrintWriter(file)) {
        writer.write(fileData);
      }
    }

    public File getFile(String filename) {
      return new File(saveDirectory + filename);
    }
  }
}

