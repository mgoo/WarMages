package main.game.model.entity;

import main.images.GameImage;
import main.util.MapPoint;
import main.util.MapSize;

/**
 * PizzaBall extends {@link Projectile}. It deals 5 damage to it’s target
 */
public class PizzaBall extends Projectile {

  private final int damageAmount = 5;

  /**
   * Constructor takes the coordinates and size of the PizzaBall, and the Unit to be targeted by the
   * PizzaBall.
   */
  public PizzaBall(MapPoint coordinates, MapSize size, Unit target) {
    super(coordinates, size, target);
  }

  @Override
  public void hits(Unit unit) {
    assert unit != null;
    assert unit.equals(target);
    unit.takeDamage(damageAmount);
  }

  @Override
  public void setImage(GameImage image) {
    assert image != null;
    this.image = image;
  }
}
