package main.game.model.entity;

import main.images.GameImage;
import main.util.MapPoint;
import main.util.MapSize;

/**
 * Entity class: entities have positions on the screen, images, and sizes.
 */
public abstract class Entity {

  protected MapPoint position;
  protected GameImage image;
  protected MapSize size;

  /**
   * Constructor takes the position of the entity and the size.
   * @param position = position of Entity
   * @param size = size of Entity
   */
  public Entity(MapPoint position, MapSize size) {
    this.position = position;
    this.size = size;
  }

  /**
   * Returns the position of the Entity.
   * @return the entity's position
   */
  public MapPoint getPosition() {
    return new MapPoint(position.x, position.y);
  }

  /**
   * Returns the size of the Entity
   * @return the entity's size
   */
  public MapSize getSize() {
    return new MapSize(size.width, size.height);
  }

  /**
   * Moves the position of the Entity by amount in the x direction.
   * @param amount to be moved by
   */
  public void moveX(float amount) {
    position = new MapPoint(position.x+amount, position.y);
  }

  /**
   * Moves the position of the Entity by amount in the y direction.
   * @param amount to be moved by
   */
  public void moveY(float amount) {
    position = new MapPoint(position.x, position.y+amount);
  }

  /**
   * Returns the image representing the Entity
   * @return GameImage of the Entity
   */
  public GameImage getImage(){
    if (image == null) {
      throw new NullPointerException("The Entity's image has not been set yet");
    }
    return image;
  }

  /**
   * Sets the Entity's image to the given image.
   * Requires that the image is not null
   * @param image
   */
  public abstract void setImage(GameImage image);

}
