package main.game.model.saveandload;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import main.game.model.GameModel;

/**
 * Deals with the saving and loading of {@link GameModel} to and from files.
 */
public class GameSaveModel {

  /**
   * This is public for testing only. File extension for saved files.
   */
  public static final String SAVE_FILE_EXTENSION = "sav";

  /**
   * Place to save and load all the files. If you change this remember to update the .gitignore
   * file.
   */
  private static final String SAVE_FILE_DIRECTORY = "./saves/";

  private static final Charset SAVE_FILE_CHARSET = Charset.defaultCharset();

  private final Filesystem filesystem;

  public GameSaveModel(Filesystem filesystem) {
    this.filesystem = filesystem;
  }

  /**
   * Stores the gameModel (through serialisation).
   *
   * @param filename Name with no slashes is it.
   */
  public void save(GameModel gameModel, String filename) throws IOException {
    Objects.requireNonNull(gameModel);

    if (filename.contains("/")) {
      throw new IllegalArgumentException("Filename should not contain slashes");
    }
    if (!filename.endsWith("." + SAVE_FILE_EXTENSION)) {
      filename += "." + SAVE_FILE_EXTENSION;
    }

    String fileData = ""; // TODO

    filesystem.save(filename, fileData);
  }

  public GameModel load(String filename) throws IOException {
    throw new Error("NYI");
  }

  /**
   * Finds the file names of all the existing game saves. Each name in a SAVE_FILE_EXTENSION and
   * does not contain any slashes.
   */
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
     * Loads the text contents of the file.
     *
     * @param filename Filename without any slashes.
     */
    String load(String filename) throws IOException;

    Collection<String> availableFilenames() throws IOException;

    void save(String filename, String fileData) throws IOException;
  }

  public static class DefaultFilesystem implements Filesystem {

    private final String saveDirectory;

    /**
     * The main constructor for this class.
     */
    public DefaultFilesystem() {
      this(SAVE_FILE_DIRECTORY);
    }

    /**
     * Constructor for saving and loading files in a different directory to the default one.
     */
    public DefaultFilesystem(String saveDirectory) {
      if (!saveDirectory.endsWith("/")) {
        throw new IllegalArgumentException();
      }

      this.saveDirectory = saveDirectory;
    }

    @Override
    public String load(String filename) throws IOException {
      File file = getFile(filename);

      return Files.readAllLines(file.toPath(), SAVE_FILE_CHARSET)
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

      try (PrintWriter writer = new PrintWriter(file, SAVE_FILE_CHARSET.name())) {
        writer.write(fileData);
      }
    }

    public File getFile(String filename) {
      return new File(saveDirectory + filename);
    }
  }
}
