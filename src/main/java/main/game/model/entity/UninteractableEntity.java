package main.game.model.entity;

import main.images.GameImage;
import main.util.MapPoint;

public class UninteractableEntity extends MapEntity {

  /**
   * Constructor takes the coordinates of the MapEntity.
   */
  public UninteractableEntity(MapPoint coord, GameImage image) {
    super(coord);
    this.image=image;
  }

  @Override
  public void setImage(GameImage image) {
    //no change
  }

  @Override
  public void tick(long timeSinceLastTick) {
    //no change
  }
}
