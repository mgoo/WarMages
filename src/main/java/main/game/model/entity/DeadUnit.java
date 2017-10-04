package main.game.model.entity;

import main.game.model.world.World;
import main.util.MapPoint;

public class DeadUnit extends MapEntity {

  /**
   * Constructor takes the coordinates of the MapEntity.
   */
  public DeadUnit(MapPoint coord) {
    super(coord);
    //todo set image
  }

  @Override
  public void tick(long timeSinceLastTick, World world) {
    //does nothing
  }
}
