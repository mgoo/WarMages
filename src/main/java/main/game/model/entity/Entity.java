package main.game.model.entity;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import main.game.model.world.World;
import main.common.images.GameImage;
import main.common.util.MapPoint;
import main.common.util.MapRect;
import main.common.util.MapSize;

/**
 * Entity class: entities have positions on the screen, images, and sizes.
 */
public abstract class Entity implements Serializable {

  private static final long serialVersionUID = 1L;

  private GameImage image;
  private MapPoint topLeft;
  private MapPoint previousTopLeft;
  private MapSize size;

  /**
   * Constructor takes the topLeft of the entity and the size.
   */
  public Entity(MapPoint topLeft, MapSize size) {
    this.topLeft = requireNonNull(topLeft);
    // look down by default (on diagonal map)
    this.previousTopLeft = topLeft.translate(-1e-3, -1e-3); // tiny numbers
    this.size = requireNonNull(size);
  }

  /**
   * Returns the position at the top left of the Entity.
   *
   * @return the entity's top left position.
   */
  public MapPoint getTopLeft() {
    return topLeft;
  }

  /**
   * Returns the position at the centre of the Entity.
   *
   * @return the entity's central position.
   */
  public MapPoint getCentre() {
    return new MapPoint(topLeft.x + size.width / 2, topLeft.y + size.height / 2);
  }

  /**
   * Returns the size/diameter of the Entity.
   *
   * @return the entity's size.
   */
  public MapSize getSize() {
    return new MapSize(size.width, size.height);
  }

  /**
   * The bounding box of this entity.
   */
  public MapRect getRect() {
    return new MapRect(getTopLeft(), getSize());
  }

  /**
   * Moves the entity.
   */
  public void translatePosition(double dx, double dy) {
    topLeft = topLeft.translate(dx, dy);
  }

  /**
   * Returns the image representing the Entity.
   *
   * @return GameImage of the Entity.
   */
  public GameImage getImage() {
    if (image == null) {
      throw new NullPointerException("The Entity's image has not been set yet");
    }
    return image;
  }

  /**
   * Updates the Entity's position.
   */
  public abstract void tick(long timeSinceLastTick, World world);

  public MapPoint getPreviousTopLeft() {
    return previousTopLeft;
  }

  protected void setImage(GameImage image) {
    this.image = requireNonNull(image);
  }
}
