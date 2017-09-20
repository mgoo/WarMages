package test.images;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import images.DefaultImageProvider;
import images.GameImage;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.junit.Test;

/**
 * Test names here follow the test naming convention:
 * unitOfWorkUnderTest_typeOfInput_expectedResult.
 */
public class DefaultImageProviderTest {
  @Test
  public void loadImageFromFileSystem_usingTestImage_resultShouldLookCorrect() throws IOException {
    BufferedImage image = GameImage._TEST_FULL_SIZE.load(new DefaultImageProvider());

    assertEquals(20, image.getWidth());
    assertEquals(15, image.getHeight());

    assertNotEquals(Color.WHITE.getRGB(), image.getRGB(0, 0));
    assertEquals(Color.BLACK.getRGB(), image.getRGB(1, 1));
  }

  // TODO Test these things before merge request
  // - test on submit jar
  // - test on intellij run
  // - test on gradle run
  // - test on test resource
}
