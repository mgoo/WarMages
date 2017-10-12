package main.game.model.entity.unit.state;

import main.common.images.GameImage;
import main.common.util.MapSize;
import main.game.model.entity.DefaultMapEntity;
import main.game.model.world.World;
import main.common.util.MapPoint;

public class DeadUnit extends DefaultMapEntity {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor takes the coordinates of the DeadUnit.
   */
  public DeadUnit(MapPoint coord, MapSize size, GameImage image) {
    super(coord, image);
  }

  @Override
  public void translatePosition(double dx, double dy) {
    //do nothing
  }

  @Override
  public void tick(long timeSinceLastTick, World world) {
    //does nothing
  }

  @Override
  public boolean isPassable() {
    return true;
  }
}
