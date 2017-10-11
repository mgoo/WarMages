package main.game.model.entity;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import main.game.model.world.World;
import main.common.util.MapPoint;
import main.common.util.MapSize;

public abstract class MovableEntity extends Entity {

  private static final long serialVersionUID = 1L;

  protected Queue<MapPoint> path = new LinkedList<>();
  protected double speed;
  private static final double LEEWAY = 0.2;

  /**
   * Constructor takes the position of the entity, the size, and it's speed.
   *
   * @param position = position of Entity
   * @param size = size of Entity
   * @param speed = speed of MovableEntity
   */
  public MovableEntity(MapPoint position, MapSize size, double speed) {
    super(position, size);
    this.speed = speed;
  }

  /**
   * Sets the path to be followed by the unit to the given path.
   */
  public void setPath(List<MapPoint> path) {
    this.path = new ArrayDeque<>(path);
  }

  @Override
  public void tick(long timeSinceLastTick, World world) {
    if (path == null || path.isEmpty()) {
      return;
    }
    MapPoint target = this.path.peek();
    double distance = getCentre().distanceTo(target);
    if (distance < LEEWAY + Math.max(getSize().width / 2, getSize().height / 2)) {
      this.path.poll();
      if (this.path.size() == 0) {
        return;
      }
      target = this.path.peek();
    }

    double dx = target.x - getTopLeft().x;
    double dy = target.y - getTopLeft().y;
    double mx = (Math.min(speed / Math.hypot(dx, dy), 1)) * dx;
    double my = (Math.min(speed / Math.hypot(dx, dy), 1)) * dy;
    assert speed + 0.001 > Math.hypot(mx, my) : "the unit tried to move faster than its speed";
    translatePosition(mx, my);
  }
}
