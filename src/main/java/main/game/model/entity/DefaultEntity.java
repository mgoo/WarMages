package main.game.model.entity;

import static java.util.Objects.requireNonNull;

import main.game.view.Renderable;
import main.game.view.ViewVisitor;
import main.util.Config;
import main.util.Event;
import main.util.MapPoint;
import main.util.MapRect;
import main.util.MapSize;

/**
 * Default implementation of {@link Entity}.
 * @author chongdyla (Secondary Author)
 * @author paladogabr
 */
public abstract class DefaultEntity implements Entity {

  private static final long serialVersionUID = 1L;

  private MapPoint topLeft;
  private MapPoint previousTopLeft;
  private MapSize size;

  private final Event<Void> removedEvent = new Event<>();

  /**
   * Constructor takes the topLeft of the entity and the size.
   */
  public DefaultEntity(MapPoint topLeft, MapSize size) {
    this.topLeft = requireNonNull(topLeft);
    this.size = requireNonNull(size);
  }

  public Event<Void> getRemovedEvent() {
    return this.removedEvent;
  }

  protected void setSize(MapSize size) {
    this.size = size;
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
    topLeft = topLeft.translate(dx, dy);
  }

  @Override
  public void slidePosition(double dx, double dy) {
    topLeft = topLeft.translate(dx, dy);
  }

  public Renderable accept(Config config, ViewVisitor viewVisitor) {
    return viewVisitor.makeDefaultView(config, this);
  }
}
