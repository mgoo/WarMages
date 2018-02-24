package main.game.model.entity;

import java.io.Serializable;
import main.game.model.data.dataObject.ImageData;
import main.game.model.world.World;
import main.game.view.Renderable;
import main.game.view.ViewVisitor;
import main.util.Config;
import main.util.Event;
import main.util.MapPoint;
import main.util.MapRect;
import main.util.MapSize;

/**
 * Entity class: entities have positions on the screen, images, and sizes.
 * @author paladogabr
 */
public interface Entity extends Serializable {

  /**
   * Gets the layer that the entity wants to render on.
   * Used to force the layers in the renderer.
   * the smaller the number the close to the front
   *
   * @return -1 for not set
   */
  default int getLayer() {
    return -1;
  }

  /**
   * Returns the position at the top left of the Entity.
   *
   * @return the entity's top left position.
   */
  MapPoint getTopLeft();

  /**
   * Returns the position at the centre of the Entity.
   *
   * @return the entity's central position.
   */
  MapPoint getCentre();

  /**
   * Returns the size/diameter of the Entity.
   *
   * @return the entity's size.
   */
  MapSize getSize();

  /**
   * The bounding box of this entity.
   */
  MapRect getRect();

  /**
   * Moves the entity.
   */
  void translatePosition(double dx, double dy);

  /**
   * Moves both the position and the previous position.
   * This is to get arround jittery units when they repel each other
   */
  void slidePosition(double dx, double dy);

  /**
   * Returns the image representing the Entity.
   *
   * @return GameImage of the Entity.
   */
  ImageData getImage();

  /**
   * Updates the Entity's position.
   */
  void tick(long timeSinceLastTick, World world);

  /**
   * Returns a boolean representing whether the given MapPoint is contained within the Entity.
   *
   * @param point to be checked is in Entity.
   * @return boolean true if contained, false otherwise.
   */
  boolean contains(MapPoint point);

  /**
   * Calls the method on the viewVisitor to create the Renderable for this entity.
   */
  Renderable accept(Config config, ViewVisitor viewVisitor);

  /**
   * Gets the event that is triggered when the enitity is removed from the world.
   */
  Event<Void> getRemovedEvent();
}
