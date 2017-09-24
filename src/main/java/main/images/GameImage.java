package main.images;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Reference all of the image files in the app here by adding a new enum value.
 *
 * @see {@link GameImage#load(ImageProvider)} To get a {@link BufferedImage}.
 */
public class GameImage {

  private static final int MAX_SIZE = Integer.MAX_VALUE;

  /**
   * File path relative to the resources directory.
   */
  private final String filePath;
  private final int startX;
  private final int startY;
  private final int width;
  private final int height;

  /**
   * For use only within the package.
   * @param filePath See {@link GameImage#filePath}
   */
  GameImage(String filePath) {
    this(filePath, 0, 0, MAX_SIZE, MAX_SIZE);
  }

  /**
   * For use only within the package.
   * @param filePath See {@link GameImage#filePath}
   */
  GameImage(String filePath, int startX, int startY, int width, int height) {
    if (filePath.startsWith("/")) {
      throw new IllegalArgumentException();
    }

    this.filePath = filePath;
    this.startX = startX;
    this.startY = startY;
    this.width = width;
    this.height = height;
  }

  public String getFilePath() {
    return filePath;
  }

  public int getStartX() {
    return startX;
  }

  public int getStartY() {
    return startY;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
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

    BufferedImage image = imageProvider.load(filePath);
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
      w = image.getWidth() - startX;
    }
    if (h == MAX_SIZE) {
      h = image.getHeight() - startY;
    }

    return image.getSubimage(startX, startY, w, h);
  }
}
