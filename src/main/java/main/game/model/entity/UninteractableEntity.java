package main.game.model.entity;

import main.images.GameImage;
import main.util.MapPoint;

public class UninteractableEntity extends MapEntity {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor takes the coordinates of the MapEntity.
   */
  public UninteractableEntity(MapPoint coord, GameImage image) {
    super(coord);
    this.image = image;
  }

  @Override
  public void tick(long timeSinceLastTick) {
    //no change
  }
}
