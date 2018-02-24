package main.game.view;

import java.awt.Graphics2D;
import main.game.model.GameModel;
import main.game.model.data.dataobject.ImageData;
import main.util.MapPoint;
import main.util.MapSize;

/**
 * Represents the a drawable object. E.g: entities, projectiles, mapComponents, etc...
 */
public interface Renderable {

  /**
   * Get the position that the image for this should be drawn at.
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
  ImageData getImage();

  /**
   * Adds decorations to the image such as health bar and selection circle.
   * Draws Decorations beneth all entities
   */
  void drawDecorationsBeneth(Graphics2D g, int x, int y, int width, int height);

  /**
   * Adds decorations to the image such as health bar and selection circle.
   * Draws the decorations ontop of all entities
   */
  void drawDecorationsOntop(Graphics2D g, int x, int y, int width, int height);

  /**
   * Gets the layer to draw the entity on.
   */
  int getLayer();

  /**
   * Gets the approximate position based on the entities previous.
   * velocity and the current time
   */
  MapPoint getEffectiveEntityPosition(long currentTime);

  /**
   * Gets the position the entity should be on the screen.
   * Does not adujst for viewbox changes.
   */
  MapPoint getEntityScreenPosition(long currentTime);

  /**
   * Gets the size the entity should be on the screen.
   */
  MapSize getEntityScreenSize();

  /**
   * Updates the Renderable each tick.
   */
  void onTick(long tickTime, GameModel model);
}