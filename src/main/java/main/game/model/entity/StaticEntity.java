package main.game.model.entity;

import main.game.model.data.dataObject.ImageData;
import main.game.model.world.World;
import main.images.Animation;
import main.util.MapPoint;
import main.util.MapSize;

public class StaticEntity extends DefaultEntity {

  protected final Animation animation;
  private final double angle;
  private int layer = -1;

  public StaticEntity(MapPoint topLeft,
                      MapSize size,
                      Animation animation,
                      double angle
                      ) {
    super(topLeft, size);
    this.animation = animation;
    this.angle = angle;
  }

  /**
   * Should be set immediately if you want to set it.
   * If you dont the entity view will cache it and it wont have any effect
   */
  public void setLayer(int layer) {
    this.layer = layer;
  }

  @Override
  public ImageData getImage() {
    return this.animation.getImage(angle);
  }

  @Override
  public void tick(long timeSinceLastTick, World world) {
    this.animation.tick();
  }

  @Override
  public boolean contains(MapPoint point) {
    return false;
  }

  @Override
  public int getLayer() {
    return this.layer;
  }
}
