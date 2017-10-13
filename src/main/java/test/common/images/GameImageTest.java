package test.common.images;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import main.images.DefaultImageProvider;
import main.common.images.GameImage;
import main.common.images.GameImageResource;
import main.common.images.ImageProvider;
import org.junit.Test;

/**
 * Test names here follow the test naming convention:
 * unitOfWorkUnderTest_typeOfInput_expectedResult.
 * @author chongdyla
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
    GameImage gameImage = GameImageResource.TEST_IMAGE_FULL_SIZE.getGameImage();
    BufferedImage image = gameImage.load(imageProvider);

    assertEquals(Color.BLUE.getRGB(), testImage.getRGB(2, 1));
    assertEquals(image.getWidth(), testImage.getWidth());
    assertEquals(image.getHeight(), testImage.getHeight());
  }

  @Test
  public void load_sizeSpecified_returnsSubImage() throws IOException {
    GameImage gameImage = GameImageResource.TEST_IMAGE_PARTIAL_SIZE.getGameImage();
    BufferedImage image = gameImage.load(imageProvider);

    // Assume default colours are not blue
    assertNotEquals(Color.WHITE.getRGB(), testImage.getRGB(0, 0));

    assertEquals(Color.BLUE.getRGB(), image.getRGB(1, 0));
    assertEquals(gameImage.getWidth(), image.getWidth());
    assertEquals(gameImage.getHeight(), image.getHeight());
  }

  @Test
  public void loadImagesFromFileSystem_usingAllGameImages_allImagesExist() throws IOException {
    for (GameImageResource gameImageResource : GameImageResource.values()) {
      DefaultImageProvider imageProvider = new DefaultImageProvider();
      BufferedImage image = gameImageResource.getGameImage().load(imageProvider);
      assertNotNull(image);
    }
  }

}
