package main.game.model.entity.unit;

import main.common.World;
import main.common.entity.DeadUnit;
import main.common.images.GameImage;
import main.common.util.MapPoint;
import main.common.util.MapSize;
import main.game.model.entity.DefaultMapEntity;

/**
 * Concrete implementation of DeadUnit interface.
 *
 * @author paladogabr
 */
public class DefaultDeadUnit extends DefaultMapEntity implements DeadUnit {
  private static final long serialVersionUID = 1L;

  /**
   * Constructor takes the coordinates of the DeadUnit.
   */
  public DefaultDeadUnit(MapPoint coord, MapSize size, GameImage image) {
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
