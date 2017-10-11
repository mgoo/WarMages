package main.game.model.entity;

import static java.util.Objects.requireNonNull;

import main.common.Entity;
import main.common.images.GameImage;
import main.common.util.MapPoint;
import main.common.util.MapRect;
import main.common.util.MapSize;

public abstract class DefaultEntity implements Entity {

  private static final long serialVersionUID = 1L;

  private GameImage image;
  private MapPoint topLeft;
  private MapPoint previousTopLeft;
  private MapSize size;

  /**
   * Constructor takes the topLeft of the entity and the size.
   */
  public DefaultEntity(MapPoint topLeft, MapSize size) {
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
  @Override
  public MapPoint getTopLeft() {
    return topLeft;
  }

  /**
   * Returns the position at the centre of the Entity.
   *
   * @return the entity's central position.
   */
  @Override
  public MapPoint getCentre() {
    return new MapPoint(topLeft.x + size.width / 2, topLeft.y + size.height / 2);
  }

  /**
   * Returns the size/diameter of the Entity.
   *
   * @return the entity's size.
   */
  @Override
  public MapSize getSize() {
    return new MapSize(size.width, size.height);
  }

  /**
   * The bounding box of this entity.
   */
  @Override
  public MapRect getRect() {
    return new MapRect(getTopLeft(), getSize());
  }

  /**
   * Moves the entity.
   */
  @Override
  public void translatePosition(double dx, double dy) {
    previousTopLeft = topLeft;
    topLeft = topLeft.translate(dx, dy);
  }

  /**
   * Returns the image representing the Entity.
   *
   * @return GameImage of the Entity.
   */
  @Override
  public GameImage getImage() {
    if (image == null) {
      throw new NullPointerException("The Entity's image has not been set yet");
    }
    return image;
  }

  @Override
  public MapPoint getPreviousTopLeft() {
    return previousTopLeft;
  }

  @Override
  public void setImage(GameImage image) {
    this.image = requireNonNull(image);
  }

  @Override
  public boolean contains(MapPoint point) {
    return (point.x >= topLeft.x && point.x <= topLeft.x + size.width && point.y >= topLeft.y
        && point.y <= topLeft.y + size.height);
  }
}
