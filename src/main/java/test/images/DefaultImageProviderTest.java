package test.images;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import main.images.DefaultImageProvider;
import main.common.images.GameImageResource;
import org.junit.Test;

/**
 * Test names here follow the test naming convention:
 * unitOfWorkUnderTest_typeOfInput_expectedResult.
 *
 * @author chongdyla
 * @author Eric Diputado (External Tester)
 */
public class DefaultImageProviderTest {

  @Test
  public void load_usingTestImage_resultShouldLookCorrect() throws IOException {
    DefaultImageProvider imageProvider = new DefaultImageProvider();
    BufferedImage image = GameImageResource.TEST_IMAGE_FULL_SIZE
        .getGameImage()
        .load(imageProvider);

    assertEquals(20, image.getWidth());
    assertEquals(15, image.getHeight());

    assertEquals(Color.WHITE.getRGB(), image.getRGB(0, 0));
    assertEquals(Color.BLACK.getRGB(), image.getRGB(1, 1));
  }

  @Test(expected = IOException.class)
  public void load_nonExistentImage_throwsException() throws IOException {
    new DefaultImageProvider().load("some_file_that_doesnt_exist.png");
  }

  @Test(expected = IllegalArgumentException.class)
  public void load_ImageWithSlash_throwsException() throws IOException {
    new DefaultImageProvider().load("/fixtures/images/image_for_image_provider_tests.png.png");
  }

}
