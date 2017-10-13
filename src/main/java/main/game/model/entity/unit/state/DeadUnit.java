package main.game.model.entity.unit.state;

import main.common.images.GameImage;
import main.common.util.MapSize;
import main.game.model.entity.DefaultMapEntity;
import main.common.World;
import main.common.util.MapPoint;

/**
 * DeadUnit MapEntity.
 *
 * @author paladogabr
 */
public class DeadUnit extends DefaultMapEntity {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor takes the coordinates of the DeadUnit.
   */
  public DeadUnit(MapPoint coord, MapSize size, GameImage image) {
    super(coord, size, image);
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
