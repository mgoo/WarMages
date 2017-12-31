package main.game.model.entity.unit.state;

import java.util.Collection;
import main.game.model.entity.Unit;
import main.game.model.world.World;
import main.util.MapPoint;

public interface Targetable {

  MapPoint getLocation();

  Collection<Unit> getEffectedUnits(World world);

  boolean isValidTargetFor(Unit unit);
}
