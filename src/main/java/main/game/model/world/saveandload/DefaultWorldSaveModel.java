package main.game.model.world.saveandload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;
import main.game.model.world.World;

/**
 * Default implementation.
 * @author chongdyla
 */
public class DefaultWorldSaveModel implements WorldSaveModel {

  private final Filesystem filesystem;

  /**
   * Default constructor.
   */
  public DefaultWorldSaveModel(Filesystem filesystem) {
    this.filesystem = filesystem;
  }

  @Override
  public void save(World world, String filename)
      throws IOException {

    Objects.requireNonNull(world);

    if (filename.contains("/")) {
      throw new IllegalArgumentException("Filename should not contain slashes");
    }
    if (!filename.endsWith("." + SAVE_FILE_EXTENSION)) {
      filename += "." + SAVE_FILE_EXTENSION;
    }

    filesystem.save(filename, outputStream -> {
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
      objectOutputStream.writeObject(world);
    });
  }

  @Override
  public World load(String filename) throws IOException, ClassNotFoundException {
    return filesystem.load(filename, inputStream -> {
      try (ObjectInputStream objectIn = new ObjectInputStream(inputStream)) {
        Object world = objectIn.readObject();
        return World.class.cast(world);
      }
    });
  }

  @Override
  public Collection<String> getExistingGameSaves() {
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
     * @param <T> The type to cast to.
     * @param loader A function that loads the object from the stream and casts. The implementer of
     *     this method should catch any {@link ClassCastException}.
     * @param filename Filename without any slashes.
     */
    <T> T load(String filename, Loader<T> loader) throws IOException, ClassNotFoundException;

    Collection<String> availableFilenames();

    void save(String filename, Saver saver) throws IOException;

    interface Loader<T> {
      T loadFromStream(InputStream inputStream) throws IOException, ClassNotFoundException;
    }

    interface Saver {
      void saveToStream(OutputStream outputStream) throws IOException;
    }
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
    public <T> T load(String filename, Loader<T> loader)
        throws IOException, ClassNotFoundException {
      File file = getFile(filename);

      try (FileInputStream inputStream = new FileInputStream(file)) {
        return loader.loadFromStream(inputStream);
      } catch (ClassNotFoundException e) {
        throw new IOException(e);
      }
    }

    @Override
    public Collection<String> availableFilenames() {
      File[] files = new File(saveDirectory)
          .listFiles(File::isFile);

      if (files == null) {
        return Collections.emptyList();
      }

      return Arrays.stream(files)
          .map(File::getName)
          .collect(Collectors.toList());
    }

    @Override
    public void save(String filename, Saver saver) throws IOException {
      File file = getFile(filename);
      file.getParentFile().mkdirs();

      try (FileOutputStream outputStream = new FileOutputStream(file)) {
        saver.saveToStream(outputStream);
      }
    }

    public File getFile(String filename) {
      return new File(saveDirectory + filename);
    }
  }
}
