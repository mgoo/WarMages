package images;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * DON'T USE METHODS DIRECTLY! THESE METHODS ARE FOR USE IN THE PACKAGE ONLY!
 * Use {@link GameImage#load(ImageProvider)} instead.
 *
 * Provides an image file, potentially from the filesystem.
 *
 * It may cache images.
 */
public abstract class ImageProvider {

  /**
   * Loads a game image.
   *
   * @param filename Filename without slashes.
   * @return The loaded image.
   * @throws IOException When the file is unable to load
   */
  protected abstract BufferedImage load(String filename) throws IOException;

  /**
   * Maybe stores the (potentially preprocessed) image for fast reuse.
   * Implementations are not required to actually cache anything.
   */
  protected abstract void storeInCache(GameImage gameImage, BufferedImage image);

  /**
   * Maybe retrieves the image from the cache.
   *
   * @return The cached image or null
   */
  protected abstract BufferedImage getFromCache(GameImage gameImage);
}
