package main.game.model.entity;

import java.io.Serializable;
import main.images.GameImage;
import main.util.MapPoint;

public class UninteractableEntity extends MapEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor takes the coordinates of the MapEntity.
   */
  public UninteractableEntity(MapPoint coord, GameImage image) {
    super(coord);
    this.image = image;
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
