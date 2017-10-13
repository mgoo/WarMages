package main.game.model.entity;

import static java.util.Objects.requireNonNull;

import main.common.entity.Entity;
import main.common.util.MapPoint;
import main.common.util.MapRect;
import main.common.util.MapSize;

/**
 * Default implementation of {@link Entity}.
 * @author chongdyla (Secondary Author)
 */
public abstract class DefaultEntity implements Entity {

  private static final long serialVersionUID = 1L;

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

  @Override
  public MapPoint getTopLeft() {
    return topLeft;
  }

  @Override
  public MapPoint getCentre() {
    return new MapPoint(topLeft.x + size.width / 2, topLeft.y + size.height / 2);
  }

  @Override
  public MapSize getSize() {
    return new MapSize(size.width, size.height);
  }

  @Override
  public MapRect getRect() {
    return new MapRect(getTopLeft(), getSize());
  }

  @Override
  public void translatePosition(double dx, double dy) {
    previousTopLeft = topLeft;
    topLeft = topLeft.translate(dx, dy);
  }

  @Override
  public MapPoint getPreviousTopLeft() {
    return previousTopLeft;
  }
}
