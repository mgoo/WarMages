package main.game.model.entity;

import java.io.Serializable;
import main.game.model.data.dataObject.ImageData;
import main.game.model.world.World;
import main.util.MapPoint;
import main.util.MapSize;

/**
 * Default implementation of MapEntity.
 * @author paladogabr
 */
public class ImageMapEntity extends DefaultEntity implements MapEntity, Serializable {

  private static final long serialVersionUID = 1L;
  private ImageData image;

  public ImageMapEntity(MapPoint coord, MapSize size, ImageData image) {
    super(coord, size);
    this.image = image;
  }

  @Override
  public void translatePosition(double dx, double dy) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ImageData getImage() {
    return image;
  }

  @Override
  public void tick(long timeSinceLastTick, World world) {
    //do nothing
  }

  @Override
  public boolean contains(MapPoint point) {
    return getRect().contains(point);
  }

  @Override
  public boolean isPassable() {
    return false;
  }
}
