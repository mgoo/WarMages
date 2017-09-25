package main.game.model.entity;

import main.images.GameImage;
import main.util.MapPoint;
import main.util.MapSize;

/**
 * HealingSphere extends {@link Projectile}. It heals a target by 5 hp.
 */
public class HealingSphere extends Projectile {

  private final int healingAmount = 5;

  /**
   * The constructor takes the coordinates and size f the HealingSphere, and the Unit target to be
   * hit by the HealingSphere.
   */
  public HealingSphere(MapPoint coordinates, MapSize size, Unit target) {
    super(coordinates, size, target);
  }

  @Override
  public void hits(Unit unit) {
    assert unit != null;
    assert unit.equals(target);
    unit.gainHealth(healingAmount);
  }

  @Override
  public void setImage(GameImage image) {
    if (image == null) {
      throw new NullPointerException("Parameter image cannot be null!");
    }
    this.image = image;
  }
}
