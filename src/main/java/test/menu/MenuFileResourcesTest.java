package test.menu;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import main.menu.MenuFileResources;
import org.junit.Test;

/**
 * Test names here follow the test naming convention:
 * unitOfWorkUnderTest_typeOfInput_expectedResult.
 * @author chongdyla (External Tester)
 */
public class MenuFileResourcesTest {

  @Test
  public void allResourcesShouldBeNonEmptyFiles() throws IOException {
    for (MenuFileResources resource : MenuFileResources.values()) {
      String failMessage = "Failed for: " + resource.name();

      File file = new File(resource.getPath());
      assertTrue(failMessage, file.exists());

      String data = String.join("\n", Files.readAllLines(file.toPath()));
      assertTrue(failMessage, data.length() > 0);
    }
  }
}
