package main.game.view;

import java.awt.Graphics2D;
import main.game.model.GameModel;
import main.game.view.EntityView;
import main.game.view.ViewVisitor;
import main.game.model.GameModel;
import main.images.GameImage;
import main.images.ImageProvider;
import main.util.Config;
import main.util.MapPoint;
import main.util.MapSize;

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
  GameImage getImage();

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
   * Updates the Renderable each tick.
   */
  void onTick(long tickTime, GameModel model);
}