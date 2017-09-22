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
   * Constructor takes the coordinates and size of the PizzaBall, and the Unit to be targeted by the PizzaBall
   * @param coordinates
   * @param size
   * @param target
   */
  public PizzaBall(MapPoint coordinates, MapSize size, Unit target) {
    super(coordinates, size, target);
  }

  @Override
  public void hits(Unit unit) {
        unit.takeDamage(damageAmount);
  }

  @Override
  public void setImage(GameImage image) {
    if (image == null) {
      throw new NullPointerException("Parameter image cannot be null!");
    }
    this.image=image;
  }
}
