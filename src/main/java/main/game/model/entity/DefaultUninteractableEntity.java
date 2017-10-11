package main.game.model.entity;

import main.common.images.GameImage;
import main.common.util.MapPoint;
import main.game.model.world.World;

public class DefaultUninteractableEntity extends DefaultMapEntity implements UninteractableEntity {
  private static final long serialVersionUID = 1L;

  /**
   * Constructor takes the coordinates of the MapEntity.
   */
  public DefaultUninteractableEntity(MapPoint coord, GameImage image) {
    super(coord);
    this.setImage(image);
  }

  @Override
  public void tick(long timeSinceLastTick, World world) {
    //no change
  }
}
