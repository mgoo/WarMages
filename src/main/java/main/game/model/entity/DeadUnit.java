package main.game.model.entity;

import main.common.images.GameImage;
import main.common.util.MapSize;
import main.game.model.world.World;
import main.common.util.MapPoint;

public class DeadUnit extends DefaultMapEntity {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor takes the coordinates of the MapEntity.
   */
  public DeadUnit(MapPoint coord, MapSize size, GameImage image) {
    super(coord, size);
    this.setImage(image);
  }

  @Override
  public void tick(long timeSinceLastTick, World world) {
    //does nothing
  }
}
