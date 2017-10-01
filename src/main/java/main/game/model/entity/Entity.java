package main.game.model.entity;

import java.io.Serializable;
import main.images.GameImage;
import main.util.MapPoint;
import main.util.MapRect;
import main.util.MapSize;

/**
 * Entity class: entities have positions on the screen, images, and sizes.
 */
public abstract class Entity implements Serializable {

  private static final long serialVersionUID = 1L;

  protected MapPoint position;
  protected GameImage image;
  protected MapSize size;

  /**
   * Constructor takes the position of the entity and the size.
   *
   * @param position position of Entity.
   * @param size size of Entity.
   */
  public Entity(MapPoint position, MapSize size) {
    this.position = position;
    this.size = size;
  }

  /**
   * Returns the position at the top left of the Entity.
   *
   * @return the entity's top left position.
   */
  public MapPoint getTopLeft() {
    return new MapPoint(position.x, position.y);
  }

  /**
   * Returns the position at the centre of the Entity.
   *
   * @return the entity's central position.
   */
  public MapPoint getCentre() {
    return new MapPoint(position.x + size.width / 2, position.y + size.height / 2);
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
   * Moves the position of the Entity by amount in the x direction.
   *
   * @param amount to be moved by.
   */
  public void moveX(double amount) {
    position = new MapPoint(position.x + amount, position.y);
  }

  /**
   * Moves the position of the Entity by amount in the y direction.
   *
   * @param amount to be moved by.
   */
  public void moveY(double amount) {
    position = new MapPoint(position.x, position.y + amount);
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
   * Sets the Entity's image to the given image. Requires that the image is not null.
   */
  public abstract void setImage(GameImage image);

  /**
   * Updates the Entity's position.
   */
  public abstract void tick(long timeSinceLastTick);
}
