package main.game.model.entity;

import main.images.GameImage;
import main.util.MapPoint;
import main.util.MapSize;

/**
 * StaticImageProjectile extends {@link Projectile}. It deals damage to it’s target.
 * It's animating is a static image.
 */
public class StaticImageProjectile extends Projectile {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor takes the coordinates and size of the StaticImageProjectile, and the Unit to be
   * targeted by the StaticImageProjectile.
   */
  public StaticImageProjectile(
      MapPoint coordinates,
      MapSize size,
      Unit target,
      GameImage gameImage,
      int damageAmount
  ) {
    super(coordinates, size, target, gameImage, damageAmount);
  }

  @Override
  public void hits(Unit unit) {
    assert unit != null;
    assert unit.equals(target);
    unit.takeDamage(damageAmount);
  }

}
