package main.game.model.entity;

import main.images.GameImage;
import main.util.MapPoint;
import main.util.MapSize;

/**
 * HealingSphere extends {@link Projectile}. It heals a target by 5 hp.
 */
public class HealingSphere extends Projectile {

  private static final long serialVersionUID = 1L;

  /**
   * The constructor takes the coordinates and size f the HealingSphere, and the Unit target to be
   * hit by the HealingSphere.
   */
  public HealingSphere(MapPoint coordinates, MapSize size, Unit target, GameImage image, int damageAmount) {
    super(coordinates, size, target, image, damageAmount);
  }

  @Override
  public void hits(Unit unit) {
    assert unit != null;
    assert unit.equals(target);
    unit.gainHealth(damageAmount);
  }
}
