package main.common;

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

  boolean contains(MapPoint point);

  MapPoint getTopLeft();

  MapPoint getCentre();

  MapSize getSize();

  MapRect getRect();

  void translatePosition(double dx, double dy);

  GameImage getImage();

  /**
   * Updates the Entity's position.
   */
  void tick(long timeSinceLastTick, World world);

  MapPoint getPreviousTopLeft();

  void setImage(GameImage image);
}
