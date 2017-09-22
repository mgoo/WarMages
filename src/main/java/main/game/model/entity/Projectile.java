package main.game.model.entity;

import main.util.MapPoint;
import main.util.MapSize;

/**
 * Projectile extends {@link Entity}. A projectile is fired by a unit at a target (another unit) and
 * affects it in some way upon impact.
 */
public abstract class Projectile extends Entity {
  private final Unit target;

  public Projectile(MapPoint coordinates, MapSize size, Unit target) {
    super(coordinates,size);
    this.target=target;
  }
  public abstract void hits(Unit unit);
}

