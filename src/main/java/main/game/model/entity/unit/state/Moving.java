package main.game.model.entity.unit.state;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import main.common.World;
import main.common.entity.Unit;
import main.common.images.UnitSpriteSheet.Sequence;
import main.common.util.MapPoint;
import main.game.model.entity.unit.DefaultUnit;
import main.game.model.entity.unit.UnitAnimation;

/**
 * Walking state for a Unit.
 *
 * @author paladogabr
 * @author Dylan (Secondary Author)
 */
public class Moving extends UnitState {

  private static final long serialVersionUID = 1L;
  private static final double LEEWAY_FOR_PATH = 0.5;

  private final Target target;
  private final UnitState nextState;

  private MapPoint lastKnownDestination;
  private Queue<MapPoint> path;

  public Moving(Unit unit, Target target, UnitState nextState) {
    super(new UnitAnimation(unit, Sequence.WALK, Sequence.WALK.frames * 2), unit);
    this.nextState = nextState;

    if (target.unit != unit) {
      throw new IllegalArgumentException();
    }

    this.target = target;
  }

  @Override
  public void tick(Long timeSinceLastTick, World world) {
    super.tick(timeSinceLastTick, world);
    tickPosition(timeSinceLastTick, world);
  }

  @Override
  public UnitState updateState() {
    if (requestedNextState != null) {
      return requestedNextState;
    }

    if (!target.isStillValid()) {
      return new Idle(unit);
    }

    if (!this.target.hasArrived()) {
      // Haven't arrived yet
      if (path.isEmpty() && unit.getCentre().distanceTo(target.getDestination()) > 1.0) {
        // Can't get to destination
        return new Idle(unit);
      }

      return this;
    }
    // Arrived at destination
    return nextState;
  }

  /**
   * Updates the Unit's position depending on it's path.
   *
   * @param timeSinceLastTick time passed since last tick.
   * @param world that this Unit is in.
   */
  private void tickPosition(Long timeSinceLastTick, World world) {
    updatePath(world);

    if (path == null || path.isEmpty()) {
      return;
    }

    MapPoint target = path.peek();
    double distance = unit.getCentre().distanceTo(target);
    if (distance <= LEEWAY_FOR_PATH) {
      path.poll();
      if (path.size() == 0) {
        // Arrived at destination
        return;
      }
      target = path.peek();
    }

    double dx = target.x - unit.getCentre().x;
    double dy = target.y - unit.getCentre().y;
    double mx = (Math.min(unit.getSpeed() / Math.hypot(dx, dy), 1)) * dx;
    double my = (Math.min(unit.getSpeed() / Math.hypot(dx, dy), 1)) * dy;

    assert unit.getSpeed() + 0.001 > Math.hypot(mx, my)
        : "the unit tried to move faster than its speed";
    assert Math.abs(mx) > 0 || Math.abs(my) > 0;

    unit.translatePosition(mx, my);
  }

  /**
   * Updates path if target destination changed or no path was calculated yet.
   */
  private void updatePath(World world) {
    MapPoint destination = target.getDestination();
    if (path != null && lastKnownDestination != null && destination.equals(lastKnownDestination)) {
      return;
    }

    List<MapPoint> pathList = world.findPath(unit.getCentre(), destination);
    path = new ArrayDeque<>(pathList);
    lastKnownDestination = destination;
  }
}
