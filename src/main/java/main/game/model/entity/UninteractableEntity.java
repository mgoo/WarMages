package main.game.model.entity;

import main.game.model.world.World;
import main.common.images.GameImage;
import main.common.util.MapPoint;

public class UninteractableEntity extends DefaultMapEntity {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor takes the coordinates of the MapEntity.
   */
  public UninteractableEntity(MapPoint coord, GameImage image) {
    super(coord);
    this.setImage(image);
  }

  @Override
  public void tick(long timeSinceLastTick, World world) {
    //no change
  }
}
