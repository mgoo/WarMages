package main.game.model.entity;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import main.game.model.world.World;
import main.util.MapPoint;
import main.util.MapSize;

public abstract class MovableEntity extends Entity {

  private static final long serialVersionUID = 1L;

  protected Queue<MapPoint> path;
  protected double speed;
  private int currentPathIdx;
  private static final double LEEWAY = 0.5;
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
    this.path = new LinkedList<>(path);
    currentPathIdx = 0;
  }

  @Override
  public void tick(long timeSinceLastTick, World world) {
    if (path == null || path.isEmpty()) {
      return;
    }
    MapPoint target = this.path.peek();
    double distance = getCentre().distanceTo(target);
    if (distance < LEEWAY + Math.max(this.size.width/2, this.size.height/2)) {
      this.path.poll();
      if (this.path.size() == 0) {
        return;
      }
      target = this.path.peek();
    }
    double distToBeTravelled = speed;
    double dx = 0, dy = 0;
    if (this.position.x < target.x) {
      dx = speed;
    }
    if (this.position.x > target.x) {
      dx = -speed;
    }
    if (this.position.y < target.y) {
      dy = speed;
    }
    if (this.position.y > target.y) {
      dy = -speed;
    }
    // @Hack Assumes 8 directional movement
    double multiplyer = dx != 0 && dy != 0 ? Math.sqrt(2)/2 : 1D;
    position = position.translate(multiplyer * dx, multiplyer * dy);
  }
}
