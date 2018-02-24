package main.game.model.entity.unit.state;

import java.util.List;
import java.util.stream.Collectors;
import main.game.model.entity.Unit;
import main.game.model.world.World;
import main.util.MapPoint;

public class MapPointTarget implements Targetable {

  private final MapPoint mapPoint;

  public MapPointTarget(MapPoint mapPoint) {
    this.mapPoint = mapPoint;
  }

  @Override
  public MapPoint getLocation() {
    return this.mapPoint;
  }

  @Override
  public List<Unit> getEffectedUnits(World world, double radius) {
    return world.getAllUnits().stream()
        .filter(u -> u.getCentre().distanceTo(this.mapPoint) - u.getSize().width < radius)
        .collect(Collectors.toList());
  }

  @Override
  public boolean isValidTargetFor(Unit unit) {
    return true;
  }
}
