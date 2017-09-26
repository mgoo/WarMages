package test.game.model.world.saveandload;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import main.game.model.world.saveandload.SerialisationFormatException;
import main.game.model.world.saveandload.WorldLoader;
import main.game.model.world.saveandload.WorldSaveModel;
import main.game.model.world.saveandload.WorldSaveModel.DefaultFilesystem;
import main.game.model.world.saveandload.WorldSaveModel.Filesystem;
import main.game.model.world.World;
import org.junit.Test;

/**
 * Test names here follow the test naming convention:
 * unitOfWorkUnderTest_typeOfInput_expectedResult.
 */
public class WorldSaveModelTest {

  private Filesystem stubFileSystem = new Filesystem() {
    private final Map<String, String> datastore = new HashMap<>();

    @Override
    public String load(String filename) throws IOException {
      if (!datastore.containsKey(filename)) {
        throw new IOException();
      }

      return datastore.get(filename);
    }

    @Override
    public Collection<String> availableFilenames() throws IOException {
      return datastore.keySet();
    }

    @Override
    public void save(String filename, String fileData) throws IOException {
      datastore.put(
          Objects.requireNonNull(filename),
          Objects.requireNonNull(fileData)
      );
    }
  };

  @Test(expected = IllegalArgumentException.class)
  public void save_nameWithSlashes_exceptionThrown()
      throws IOException, SerialisationFormatException {
    WorldSaveModel worldSaveModel = new WorldSaveModel(stubFileSystem);
    World world = new WorldLoader().loadSingleLevelTestWorld();
    worldSaveModel.save(world, "some/file");
  }

  @Test
  public void save_withoutFileExtension_fileIsSavedWithExtension()
      throws IOException, SerialisationFormatException {
    WorldSaveModel worldSaveModel = new WorldSaveModel(stubFileSystem);
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
      throws IOException, SerialisationFormatException {
    WorldSaveModel worldSaveModel = new WorldSaveModel(stubFileSystem);
    worldSaveModel.load("some_nonexistent_file");
  }

  @Test
  public void saveAndThenLoad_singleLevelWorld_loadedCopyShouldEqualOriginal()
      throws IOException, SerialisationFormatException {
    saveAndThenLoad_someWorld_loadedCopyShouldEqualOriginal(
        new WorldLoader().loadSingleLevelTestWorld()
    );
  }

  @Test
  public void saveAndThenLoad_defaultWorld_loadedCopyShouldEqualOriginal()
      throws IOException, SerialisationFormatException {
    World world = new WorldLoader().load();
    saveAndThenLoad_someWorld_loadedCopyShouldEqualOriginal(world);
  }

  private void saveAndThenLoad_someWorld_loadedCopyShouldEqualOriginal(World originalWorld)
      throws IOException, SerialisationFormatException {
    // Given these objects
    WorldSaveModel worldSaveModel = new WorldSaveModel(stubFileSystem);
    String filename = "filename";

    // when the model is saved
    worldSaveModel.save(originalWorld, filename);
    // and then loaded
    World loadedModel = worldSaveModel.load(filename);

    // then the references should be different
    assertFalse(originalWorld == loadedModel);
    // and all the contents should be .equal
    assertEquals(originalWorld, loadedModel);
  }

  public static class DefaultFileSystemTest {

    @Test
    public void saveThenListAvailableFilenamesThenLoad() throws IOException {
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
      filesystem.save(filename, fileContents);

      // then it should show in the list of availableFilenames
      assertTrue(file.exists());
      assertTrue(filesystem.availableFilenames().contains(filename));

      // when the file of the same name is loaded
      String loadedFileContents = filesystem.load(filename);

      // then the loaded file contents should equal the original file contents.
      assertEquals(fileContents, loadedFileContents);

      // Cleanup
      file.delete();
    }
  }
}
