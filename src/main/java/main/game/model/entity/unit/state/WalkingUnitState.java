package main.game.model.entity.unit.state;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
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

  private final Target target;

  private MapPoint lastKnownDestination;
  private Queue<MapPoint> path;

  public WalkingUnitState(DefaultUnit unit, Target target) {
    super(Sequence.WALK, unit);

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
      return new IdleUnitState(unit);
    }

    if (unit.getCentre().distanceTo(lastKnownDestination) > target.getDestinationLeeway()) {
      // Haven't arrived yet
      if (path.isEmpty()) {
        // Can't get to destination
        return new IdleUnitState(unit);
      }

      return this;
    }

    // Arrived at destination
    return target.getNewDestinationState();
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
    MapPoint destination = target.getPossiblyChangingDestination();
    if (path != null && lastKnownDestination != null && destination.equals(lastKnownDestination)) {
      return;
    }

    List<MapPoint> pathList = world.findPath(unit.getCentre(), destination);
    path = new ArrayDeque<>(pathList);
    lastKnownDestination = destination;
  }

  public static abstract class Target implements Serializable {

    private static final long serialVersionUID = 1L;

    protected final DefaultUnit unit;

    protected Target(DefaultUnit unit) {
      this.unit = unit;
    }

    abstract MapPoint getPossiblyChangingDestination();
    abstract boolean isStillValid();
    abstract double getDestinationLeeway();
    abstract UnitState getNewDestinationState();
  }

  public static class MapPointTarget extends Target {

    private static final long serialVersionUID = 1L;

    private final MapPoint target;

    public MapPointTarget(DefaultUnit unit, MapPoint target) {
      super(unit);
      this.target = target;
    }

    @Override
    MapPoint getPossiblyChangingDestination() {
      return target;
    }

    @Override
    boolean isStillValid() {
      return true;
    }

    @Override
    double getDestinationLeeway() {
      return LEEWAY_FOR_PATH;
    }

    @Override
    UnitState getNewDestinationState() {
      return new IdleUnitState(unit);
    }
  }

  public static class EnemyUnitTarget extends Target {

    private static final long serialVersionUID = 1L;

    private final Unit enemyUnitTarget;

    public EnemyUnitTarget(DefaultUnit unit, Unit enemyUnitTarget) {
      super(unit);
      this.enemyUnitTarget = enemyUnitTarget;

      if (!isStillValid()) {
        throw new IllegalArgumentException();
      }
    }

    @Override
    MapPoint getPossiblyChangingDestination() {
      return enemyUnitTarget.getCentre();
    }

    @Override
    boolean isStillValid() {
      return enemyUnitTarget.getHealth() > 0
          && unit.getTeam().canAttack(enemyUnitTarget.getTeam());
    }

    @Override
    double getDestinationLeeway() {
      return unit.getUnitType().getAutoAttackDistance() * 0.99; // avoid floating point inaccuracy
    }

    @Override
    UnitState getNewDestinationState() {
      return new AttackingUnitState(unit, enemyUnitTarget);
    }
  }
}
