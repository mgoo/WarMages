package main.game.model.entity;

import java.util.List;
import main.game.model.world.World;
import main.util.MapPoint;
import main.util.MapSize;

public abstract class MovableEntity extends Entity {

  private static final long serialVersionUID = 1L;

  protected List<MapPoint> path;
  protected double speed;
  private int currentPathIdx;

  /**
   * Constructor takes the position of the entity and the size.
   *
   * @param position = position of Entity
   * @param size = size of Entity
   */
  public MovableEntity(MapPoint position, MapSize size, double speed) {
    super(position, size);
    this.speed = speed;
  }

  /**
   * Sets the path to be followed by the unit to the given path.
   */
  public void setPath(List<MapPoint> path) {
    this.path = path;
    currentPathIdx = 0;
  }

  @Override
  public void tick(long timeSinceLastTick, World world) {
    double distToBeTravelled = speed * timeSinceLastTick;
    double leeway = 0.5;
    //update position
    if (path != null && !path.isEmpty()) {
      double minDist = getTopLeft().distanceTo(path.get(path.size() - 1));
      int nextIdx = path.size() - 1;
      for (int i = currentPathIdx; i < path.size(); i++) {
        MapPoint mp = path.get(i);
        double distFromCurrent = getTopLeft().distanceTo(mp);
        if (distFromCurrent < distToBeTravelled + leeway
            && distFromCurrent > distToBeTravelled - leeway) {
          //within +- leeway of distToBeTravelled
          if (distFromCurrent < minDist) {
            //closest to distToBeTravelled
            minDist = distFromCurrent;
            nextIdx = i;
          }
        }
      }
      currentPathIdx = nextIdx;
      position = path.get(nextIdx);
    }
  }
}
