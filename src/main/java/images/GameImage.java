package images;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Reference all of the image files in the app here by adding a new enum value.
 * <p>
 * See {@link GameImage#load(ImageProvider)}
 */
public enum GameImage {

  // Values used in tests only
  _TEST_FULL_SIZE("image_for_image_provider_tests.png"),
  _TEST_PARTIAL_SIZE("", 1, 1, 3, 2);

  private static final int MAX_SIZE = Integer.MAX_VALUE;

  public final String filename;
  public final int startX;
  public final int startY;
  public final int width;
  public final int height;

  GameImage(String filename) {
    this(filename, 0, 0, MAX_SIZE, MAX_SIZE);
  }

  GameImage(String filename, int startX, int startY, int width, int height) {
    this.filename = filename;
    this.startX = startX;
    this.startY = startY;
    this.width = width;
    this.height = height;
  }

  /**
   * Use this method to get an image.
   *
   * @throws IOException For file IO errors
   */
  public BufferedImage load(ImageProvider imageProvider) throws IOException {
    BufferedImage fromCache = imageProvider.getFromCache(this);
    if (fromCache != null) {
      return fromCache;
    }

    BufferedImage image = imageProvider.load(filename);
    image = clipToBounds(image);

    imageProvider.storeInCache(this, image);
    return image;
  }

  private BufferedImage clipToBounds(BufferedImage image) {
    int w = width;
    int h = height;

    if (width == MAX_SIZE && height == MAX_SIZE) {
      return image;
    }

    if (w == MAX_SIZE) {
      w = image.getWidth();
    }
    if (h == MAX_SIZE) {
      h = image.getHeight();
    }

    return image.getSubimage(startX, startY, w, h);
  }
}
