package images;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Reference all of the image files in the app here by adding a new enum value
 *
 * @see ImageProvider for loading the images
 */
public enum GameImage {

  // Values used in tests only
  _TEST_FULL_SIZE("one.png"),
  _TEST_PARTIAL_SIZE("two.png", 1, 1, 3, 2);

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
    return image.getSubimage(startX, startY, width, height);
  }
}
