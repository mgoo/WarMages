package main.game.model.entity;

import main.common.MapEntity;
import main.common.util.MapPoint;
import main.common.util.MapSize;
import main.game.model.world.World;

public class DefaultMapEntity extends DefaultEntity implements MapEntity {

  private static final long serialVersionUID = 1L;

  public DefaultMapEntity(MapPoint coord) {
    this(coord, new MapSize(1, 1));
  }

  public DefaultMapEntity(MapPoint coord, MapSize size) {
    super(coord, size);
  }

  @Override
  public void translatePosition(double dx, double dy) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void tick(long timeSinceLastTick, World world) {

  }

  /**
   * Returns boolean representing whether the given point lies inside the MapEntity.
   */
  public boolean contains(MapPoint point) {
    return getRect().contains(point);
  }

}
