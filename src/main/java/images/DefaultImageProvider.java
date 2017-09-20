package images;

import java.awt.image.BufferedImage;
import java.io.File;
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

  @Override
  protected BufferedImage load(String filename) throws IOException {
    if (filename.contains("/") || filename.contains(File.separator)) {
      throw new IllegalArgumentException("Illegal filename: " + filename);
    }

    URL imageResource = getClass().getResource("/" + filename);
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
