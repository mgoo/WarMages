package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import main.game.model.GameModel;
import main.game.model.saveandload.GameSaveModel;
import main.game.model.saveandload.GameSaveModel.DefaultFilesystem;
import main.game.model.saveandload.GameSaveModel.Filesystem;
import org.junit.Test;
import org.mockito.Mockito;

public class GameSaveModelTest {

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
  public void save_nameWithSlashes_exceptionThrown() throws IOException {
    GameSaveModel gameSaveModel = new GameSaveModel(stubFileSystem);
    GameModel gameModel = Mockito.mock(GameModel.class);
    gameSaveModel.save(gameModel, "some/file");
  }

  @Test
  public void save_withoutFileExtension_fileIsSavedWithExtension() throws IOException {
    GameSaveModel gameSaveModel = new GameSaveModel(stubFileSystem);
    GameModel gameModel = Mockito.mock(GameModel.class);
    gameSaveModel.save(gameModel, "filename");

    assertTrue(
        stubFileSystem
            .availableFilenames()
            .contains("filename." + GameSaveModel.SAVE_FILE_EXTENSION)
    );
  }

  @Test
  public void saveAndThenLoad_withBoringGameModel_loadedCopyShouldEqualOriginal() {

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

      // then the loaded file contents should equal the original file contents
      assertEquals(fileContents, loadedFileContents);

      // Cleanup
      file.delete();
    }
  }
}
