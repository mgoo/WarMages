package main.common.entity;

import java.io.Serializable;
import main.common.images.GameImage;
import main.common.util.MapPoint;
import main.common.util.MapRect;
import main.common.util.MapSize;
import main.game.model.world.World;

/**
 * Entity class: entities have positions on the screen, images, and sizes.
 */
public interface Entity extends Serializable {

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
   * Returns the image representing the Entity.
   *
   * @return GameImage of the Entity.
   */
  GameImage getImage();

  /**
   * Updates the Entity's position.
   */
  void tick(long timeSinceLastTick, World world);

  /**
   * Returns the Entity's previous top left position.
   *
   * @return MapPoint prev position.
   */
  MapPoint getPreviousTopLeft();

  /**
   * Returns a boolean representing whether the given MapPoint is contained within the Entity.
   *
   * @param point to be checked is in Entity.
   * @return boolean true if contained, false otherwise.
   */
  boolean contains(MapPoint point);
}
