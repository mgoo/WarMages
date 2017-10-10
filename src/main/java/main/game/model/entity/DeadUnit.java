package main.game.model.entity;

import main.common.images.GameImage;
import main.game.model.world.World;
import main.common.util.MapPoint;

public class DeadUnit extends MapEntity {

  /**
   * Constructor takes the coordinates of the MapEntity.
   */
  public DeadUnit(MapPoint coord, GameImage image) {
    super(coord);
    this.image = image;
  }

  @Override
  public void tick(long timeSinceLastTick, World world) {
    //does nothing
  }
}
