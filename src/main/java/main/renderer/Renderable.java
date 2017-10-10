package main.renderer;

import java.awt.image.BufferedImage;
import main.common.util.MapPoint;
import main.common.util.MapSize;

/**
 * Represents the a drawable object. E.g: entities, projectiles, mapComponents, etc...
 */
public interface Renderable {

  /**
   * Getter method to get the position of this object.
   *
   * @return the point on map which this Renderable object is on
   */
  MapPoint getImagePosition(long currentTime);

  /**
   * Gets the size of the image to draw.
   *
   * @return the size of the image to display
   */
  MapSize getImageSize();

  /**
   * Simple getter method to get the image file of this object.
   *
   * @return the image representation of this object
   */
  BufferedImage getImage();
}