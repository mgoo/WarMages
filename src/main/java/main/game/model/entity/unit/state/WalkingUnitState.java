package main.game.model.entity.unit.state;

import static java.util.Objects.requireNonNull;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.function.Supplier;
import main.common.entity.Unit;
import main.common.images.UnitSpriteSheet.Sequence;
import main.common.util.MapPoint;
import main.game.model.entity.unit.DefaultUnit;
import main.game.model.world.World;

/**
 * Walking state for a Unit.
 *
 * @author paladogabr
 * @author Dylan (Secondary Author)
 */
public class WalkingUnitState extends UnitState {

  private static final long serialVersionUID = 1L;

  private static final double LEEWAY_FOR_PATH = 0.01;

  /**
   * Note: Doesn't use {@link DefaultUnit#getTarget()} because if target changes, a new
   * {@link UnitState} will be created anyway.
   */
  private final Unit targetUnitOrNull;
  /**
   * Simplifies finding the next position.
   */
  private final Supplier<MapPoint> targetFinder;
  private MapPoint lastKnownDestination;
  private Queue<MapPoint> path;

  public WalkingUnitState(DefaultUnit unit, Unit targetUnit) {
    super(Sequence.WALK, unit);

    if (targetUnit.getHealth() == 0) {
      throw new IllegalArgumentException();
    }

    this.targetFinder = targetUnit::getCentre;
    this.targetUnitOrNull = requireNonNull(targetUnit);
  }

  public WalkingUnitState(DefaultUnit unit, MapPoint target) {
    super(Sequence.WALK, unit);
    this.targetFinder = () -> target;
    this.targetUnitOrNull = null;
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

    if (targetUnitOrNull != null && targetUnitOrNull.getHealth() == 0) {
      return new IdleUnitState(unit);
    }

    if (unit.getCentre().distanceTo(lastKnownDestination) > LEEWAY_FOR_PATH) {
      // Haven't arrived yet
      if (path.isEmpty()) {
        // Can't get to destination
        return new IdleUnitState(unit);
      }

      return this;
    }

    // Arrived at destination
    if (targetUnitOrNull == null
        || targetUnitOrNull.getHealth() == 0
        || !unit.getTeam().canAttack(targetUnitOrNull.getTeam())) {
      return new IdleUnitState(unit);
    } else {
      return new AttackingUnitState(unit, targetUnitOrNull);
    }
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
    MapPoint destination = targetFinder.get();
    if (path != null && lastKnownDestination != null && destination.equals(lastKnownDestination)) {
      return;
    }

    List<MapPoint> pathList = world.findPath(unit.getCentre(), targetFinder.get());
    path = new ArrayDeque<>(pathList);
    lastKnownDestination = destination;
  }
}
