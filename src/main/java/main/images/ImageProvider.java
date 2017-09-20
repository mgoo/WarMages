package main.images;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Provides an image file, potentially from the filesystem.
 *
 * It may cache images.
 */
public interface ImageProvider {

  BufferedImage load(GameImage gameImage) throws IOException;
}
