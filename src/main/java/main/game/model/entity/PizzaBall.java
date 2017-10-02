package main.game.model.entity;

import main.images.GameImage;
import main.util.MapPoint;
import main.util.MapSize;

/**
 * PizzaBall extends {@link Projectile}. It deals 5 damage to it’s target
 */
public class PizzaBall extends Projectile {

  private static final long serialVersionUID = 1L;

  private final int damageAmount = 5;

  /**
   * Constructor takes the coordinates and size of the PizzaBall, and the Unit to be targeted by the
   * PizzaBall.
   */
  public PizzaBall(MapPoint coordinates, MapSize size, Unit target, GameImage gameImage) {
    super(coordinates, size, target, gameImage);
  }

  @Override
  public void hits(Unit unit) {
    assert unit != null;
    assert unit.equals(target);
    unit.takeDamage(damageAmount);
  }

}
