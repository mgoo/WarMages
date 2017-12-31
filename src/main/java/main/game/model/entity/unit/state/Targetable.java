package main.game.model.entity.unit.state;

import java.util.Collection;
import main.common.World;
import main.common.entity.Unit;
import main.common.util.MapPoint;

public interface Targetable {

  MapPoint getLocation();

  Collection<Unit> getEffectedUnits(World world);

  boolean isValidTargetFor(Unit unit);
}
