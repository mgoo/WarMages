package main.images;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;

/**
 * Load the images from the resources folder.
 */
public class DefaultImageProvider extends ImageProvider {

  private static final String RESOURCES_DIRECTORY = "./resources/";

  private final String resourcesDirectory;

  /**
   * Cache the image for a given file path. Contents can be garbage collected if memory is needed.
   */
  private Map<String, BufferedImage> filePathCache = new WeakHashMap<>();
  private Map<GameImage, BufferedImage> gameImageCache = new HashMap<>();

  /**
   * Default constructor for app.
   */
  public DefaultImageProvider() {
    this.resourcesDirectory = RESOURCES_DIRECTORY;
  }

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

    BufferedImage cachedImage = filePathCache.get(filePath);
    if (cachedImage != null) {
      return cachedImage;
    }

    File imageFile = new File(resourcesDirectory + filePath);
    try {
      BufferedImage image = ImageIO.read(imageFile);
      filePathCache.put(filePath, image);
      return image;
    } catch (IIOException e) {
      throw new IOException("Can't read image file: " + filePath, e);
    }
  }

  @Override
  protected void storeInCache(GameImage gameImage, BufferedImage image) {
    gameImageCache.put(gameImage, image);
  }

  @Override
  protected BufferedImage getFromCache(GameImage gameImage) {
    return gameImageCache.get(gameImage);
  }
}
