package main.images;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * Load the images from the resources folder.
 */
public class DefaultImageProvider extends ImageProvider {

  private Map<GameImage, BufferedImage> imageCache = new HashMap<>();

  /**
   * Public for testing only.
   *
   * @param filePath File path relative to the resources directory.
   */
  @Override
  public BufferedImage load(String filePath) throws IOException {
    if (filePath.startsWith("/")) {
      throw new IllegalArgumentException("Illegal file path: " + filePath);
    }

    URL imageResource = getClass().getResource("/" + filePath);
    if (imageResource == null) {
      throw new IOException("Resource not found: " + filePath);
    }

    return ImageIO.read(imageResource);
  }

  @Override
  protected void storeInCache(GameImage gameImage, BufferedImage image) {
    imageCache.put(gameImage, image);
  }

  @Override
  protected BufferedImage getFromCache(GameImage gameImage) {
    return imageCache.get(gameImage);
  }
}
