package test.images;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import main.images.GameImage;
import main.images.ImageProvider;
import org.junit.Test;

/**
 * Test names here follow the test naming convention:
 * unitOfWorkUnderTest_typeOfInput_expectedResult.
 */
public class GameImageTest {

  private final BufferedImage testImage = new BufferedImage(10, 5, TYPE_INT_ARGB);

  {
    testImage.setRGB(2, 1, Color.BLUE.getRGB());
  }

  private final ImageProvider imageProvider = new ImageProvider() {
    @Override
    protected BufferedImage load(String filePath) throws IOException {
      return testImage;
    }

    @Override
    protected void storeInCache(GameImage gameImage, BufferedImage image) {
    }

    @Override
    protected BufferedImage getFromCache(GameImage gameImage) {
      return null;
    }
  };

  @Test
  public void load_noSizeSpecified_returnsEntireImage() throws IOException {
    BufferedImage image = GameImage._TEST_FULL_SIZE.load(imageProvider);

    assertEquals(Color.BLUE.getRGB(), testImage.getRGB(2, 1));
    assertEquals(image.getWidth(), testImage.getWidth());
    assertEquals(image.getHeight(), testImage.getHeight());
  }

  @Test
  public void load_sizeSpecified_returnsSubImage() throws IOException {
    GameImage gameImage = GameImage._TEST_PARTIAL_SIZE;
    BufferedImage image = gameImage.load(imageProvider);

    // Assume default colours are not blue
    assertNotEquals(Color.WHITE.getRGB(), testImage.getRGB(0, 0));

    assertEquals(Color.BLUE.getRGB(), image.getRGB(1, 0));
    assertEquals(gameImage.width, image.getWidth());
    assertEquals(gameImage.height, image.getHeight());
  }
}
