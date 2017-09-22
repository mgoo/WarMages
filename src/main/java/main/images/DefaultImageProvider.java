package main.images;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;

/**
 * Load the images from the resources folder.
 */
public class DefaultImageProvider extends ImageProvider {

  private static final String RESOURCES_DIRECTORY = "./resources/";

  private final String resourcesDirectory;

  private Map<GameImage, BufferedImage> imageCache = new HashMap<>();

  /**
   * Default constructor for app.
   */
  public DefaultImageProvider() {
    this(RESOURCES_DIRECTORY);
  }

  /**
   * Customisable directory constructor (mainly for tests).
   */
  public DefaultImageProvider(String resourcesDirectory) {
    if (!resourcesDirectory.endsWith("/")) {
      resourcesDirectory += "/";
    }

    this.resourcesDirectory = resourcesDirectory;
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

    File imageFile = new File(resourcesDirectory + filePath);
    try {
      return ImageIO.read(imageFile);
    } catch (IIOException e) {
      throw new IOException(e);
    }
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
