package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import main.game.model.saveandload.GameSaveModel.DefaultFilesystem;
import org.junit.Test;

public class GameSaveModelTest {

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
