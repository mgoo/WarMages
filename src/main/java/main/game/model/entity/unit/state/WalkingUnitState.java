package main.game.model.entity.unit.state;

import static java.util.Objects.requireNonNull;

import java.util.List;
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
 */
public class WalkingUnitState extends UnitState {

  private static final long serialVersionUID = 1L;

  private static final double LEEWAY_FOR_PATH = 0.01;

  private final Unit targetUnitOrNull;
  private final Supplier<MapPoint> targetFinder;
  private MapPoint lastDestination;
  private List<MapPoint> path;

  public WalkingUnitState(DefaultUnit unit, Unit targetUnitOrNull) {
    super(Sequence.WALK, unit);
    this.targetFinder = targetUnitOrNull::getCentre;
    this.targetUnitOrNull = requireNonNull(targetUnitOrNull);
  }

  public WalkingUnitState(DefaultUnit unit, MapPoint target) {
    super(Sequence.WALK, unit);
    this.targetFinder = () -> target;
    this.targetUnitOrNull = null;
  }

  @Override
  public UnitState updateState() {
    // TODO if close to target
    // attack enemy if target is enemy
    // idle if no target
    return (requestedNextState == null) ? this : requestedNextState;
  }

  @Override
  public void tick(Long timeSinceLastTick, World world) {
    super.tick(timeSinceLastTick, world);
    tickPosition(timeSinceLastTick, world);
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

    MapPoint target = path.get(0);
    double distance = unit.getCentre().distanceTo(target);
    if (distance < LEEWAY_FOR_PATH) {
      path.remove(0);
      if (path.size() == 0) {
        return;
      }
      target = path.get(0);
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
    if (path != null && lastDestination != null && destination.equals(lastDestination)) {
      return;
    }

    path = world.findPath(unit.getCentre(), targetFinder.get());
    lastDestination = destination;
  }
}
