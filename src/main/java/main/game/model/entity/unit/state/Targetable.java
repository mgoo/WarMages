package main.game.model.entity.unit.state;

import java.util.List;
import main.game.model.entity.Unit;
import main.game.model.world.World;
import main.util.MapPoint;

public interface Targetable {

  MapPoint getLocation();

  /**
   * Returns a list of the units that are effected in the order of priority.
   */
  List<Unit> getEffectedUnits(World world);

  boolean isValidTargetFor(Unit unit);
}
