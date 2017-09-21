package main.renderer;

import java.awt.image.BufferedImage;
import main.util.MapPoint;

/**
 * Represents the a drawable object. E.g: entities, projectiles, mapComponents, etc...
 */
public interface Renderable {

  /**
   * Simple getter method to get the position of this object.
   *
   * @return the point on map which this Renderable object is on
   */
  MapPoint getImagePosition();

  /**
   * Simple getter method to get the image file of this object.
   *
   * @return the image representation of this object
   */
  BufferedImage getImage();

  /**
   * Calculates the actual MapPoint of this object based on the animation state.
   *
   * @return the MapPoint of the object considering the animation state
   */
  MapPoint getEffectiveEntityPosition();
}