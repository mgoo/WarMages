package test.game.model.world.saveandload;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import main.game.model.world.World;
import main.game.model.world.saveandload.DefaultWorldSaveModel;
import main.game.model.world.saveandload.DefaultWorldSaveModel.DefaultFilesystem;
import main.game.model.world.saveandload.DefaultWorldSaveModel.Filesystem;
import main.game.model.world.saveandload.WorldLoader;
import main.game.model.world.saveandload.WorldSaveModel;
import org.junit.Test;

/**
 * Test names here follow the test naming convention:
 * unitOfWorkUnderTest_typeOfInput_expectedResult.
 */
public class WorldSaveModelTest {

  private Filesystem stubFileSystem = new Filesystem() {
    private final Map<String, byte[]> filenameToData = new HashMap<>();

    @Override
    public <T> T load(String filename, Loader<T> loader)
        throws IOException, ClassNotFoundException {
      if (!filenameToData.containsKey(filename)) {
        throw new IOException("Key not found");
      }

      try (InputStream inputStream = new ByteArrayInputStream(filenameToData.get(filename))) {
        return loader.loadFromStream(inputStream);
      }
    }

    @Override
    public Collection<String> availableFilenames() {
      return filenameToData.keySet();
    }

    @Override
    public void save(String filename, Saver saver)
        throws IOException {
      try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
        saver.saveToStream(stream);
        filenameToData.put(filename, stream.toByteArray());
      }
    }
  };

  @Test(expected = IllegalArgumentException.class)
  public void save_nameWithSlashes_exceptionThrown() throws IOException {
    DefaultWorldSaveModel worldSaveModel = new DefaultWorldSaveModel(stubFileSystem);
    World world = new WorldLoader().loadSingleLevelTestWorld();
    worldSaveModel.save(world, "some/file");
  }

  @Test
  public void save_withoutFileExtension_fileIsSavedWithExtension() throws IOException {
    DefaultWorldSaveModel worldSaveModel = new DefaultWorldSaveModel(stubFileSystem);
    World world = new WorldLoader().loadSingleLevelTestWorld();
    worldSaveModel.save(world, "filename");

    assertTrue(
        stubFileSystem
            .availableFilenames()
            .contains("filename." + WorldSaveModel.SAVE_FILE_EXTENSION)
    );
  }

  @Test(expected = IOException.class)
  public void load_nonexistentFile_exceptionThrown()
      throws IOException, ClassNotFoundException {
    DefaultWorldSaveModel worldSaveModel = new DefaultWorldSaveModel(stubFileSystem);
    worldSaveModel.load("some_nonexistent_file");
  }

  @Test
  public void saveAndThenLoad_singleLevelWorld_loadedCopyShouldEqualOriginal()
      throws IOException, ClassNotFoundException {
    saveAndThenLoad_someWorld_loadedCopyShouldEqualOriginal(
        new WorldLoader().loadSingleLevelTestWorld()
    );
  }

  @Test
  public void saveAndThenLoad_loadMultilevelWorld_loadedCopyShouldEqualOriginal()
      throws IOException, ClassNotFoundException {
    World world = new WorldLoader().loadMultilevelWorld();
    saveAndThenLoad_someWorld_loadedCopyShouldEqualOriginal(world);
  }

  @Test
  public void saveAndThenLoad_defaultWorld_loadedCopyShouldEqualOriginal()
      throws IOException, ClassNotFoundException {
    World world = new WorldLoader().load();
    saveAndThenLoad_someWorld_loadedCopyShouldEqualOriginal(world);
  }

  @Test
  public void saveAndThenLoad_saveWorlds_checkGameSavesAreInOrder() throws IOException {
    DefaultWorldSaveModel worldSaveModel = new DefaultWorldSaveModel(stubFileSystem);
    World world = new WorldLoader().loadSingleLevelTestWorld();
    worldSaveModel.save(world, "filenameOne");
    world = new WorldLoader().loadSingleLevelTestWorld();
    worldSaveModel.save(world, "filenameTwo");
    Collection<String> actualCollection = worldSaveModel.getExistingGameSaves();
    String[] expectedArray = new String[]{"filenameOne." + WorldSaveModel.SAVE_FILE_EXTENSION,
        "filenameTwo." + WorldSaveModel.SAVE_FILE_EXTENSION};
    assertArrayEquals(expectedArray, actualCollection.toArray());
  }

  private void saveAndThenLoad_someWorld_loadedCopyShouldEqualOriginal(World originalWorld)
      throws IOException, ClassNotFoundException {
    // Given these objects
    DefaultWorldSaveModel worldSaveModel = new DefaultWorldSaveModel(stubFileSystem);
    String filename = "filename." + WorldSaveModel.SAVE_FILE_EXTENSION;

    // when the model is saved
    worldSaveModel.save(originalWorld, filename);
    // and then loaded
    World loadedModel = worldSaveModel.load(filename);

    // then the references should be different
    assertFalse(originalWorld == loadedModel);
    // and all the contents should be the same (by checking that there are the same number of
    // each entity class
    assertEquals(
        getSortedClassNames(originalWorld.getAllEntities()),
        getSortedClassNames(loadedModel.getAllEntities())
    );
  }

  private List<String> getSortedClassNames(Collection<?> list) {
    return list.stream()
        .map(Object::getClass)
        .map(Object::toString)
        .sorted()
        .collect(Collectors.toList());
  }

  public static class DefaultFileSystemTest {

    @Test
    public void saveThenListAvailableFilenamesThenLoad()
        throws IOException, ClassNotFoundException {
      // Given this file data
      String filename = ".test-save.temp";
      String fileContents = Math.random() + "";
      // and a new file system
      DefaultFilesystem filesystem = new DefaultFilesystem();
      // and that the file doesn't already exist
      File file = filesystem.getFile(filename);
      if (file.exists()) {
        file.delete();
      }

      // when save is called
      filesystem.save(filename, outputStream -> {
        try (PrintWriter writer = new PrintWriter(outputStream)) {
          writer.write(fileContents);
        }
      });

      // then it should show in the list of availableFilenames
      assertTrue(file.exists());
      assertTrue(filesystem.availableFilenames().contains(filename));

      // when the file of the same name is loaded
      String loadedFileContents = filesystem.load(filename, inputStream -> {
        try (Scanner scanner = new Scanner(inputStream)) {
          StringBuilder result = new StringBuilder();
          scanner.useDelimiter("");
          while (scanner.hasNext()) {
            result.append(scanner.next());
          }
          return result.toString();
        }
      });

      // then the loaded file contents should equal the original file contents.
      assertEquals(fileContents, loadedFileContents);

      // Cleanup
      file.delete();
    }
  }
}
