package main.game.model.entity;

import main.game.model.world.World;
import main.images.GameImage;
import main.util.MapPoint;
import main.util.MapSize;

/**
 * Projectile extends {@link Entity}. A projectile is fired by a unit at a target (another unit) and
 * affects it in some way upon impact.
 */
public abstract class Projectile extends Entity {

  private static final long serialVersionUID = 1L;

  protected final Unit target;
  protected final int speed = 6;
  protected int damageAmount;

  /**
   * Constructor takes the starting coordinates of the projectile, the size,
   * and the target of the projectile.
   * @param coordinates at start of projectile path.
   * @param size of projectile.
   * @param target unit of projectile.
   */
  public Projectile(
      MapPoint coordinates,
      MapSize size,
      Unit target,
      GameImage gameImage,
      int damageAmount
  ) {
    super(coordinates, size);
    this.target = target;
    this.image = gameImage;
    this.damageAmount = damageAmount;
  }

  /**
   * Applies actions to given unit when it is hit by the Projectile.
   * @param unit to be hit.
   */
  public abstract void hits(Unit unit);

  @Override
  public void tick(long timeSinceLastTick, World world) {
    double distToTarget = getCentre().distance(target.getCentre());
    double distToBeTravelled = speed * timeSinceLastTick; //todo finalize
    double percentage = distToBeTravelled / distToTarget;
    moveX(percentage * (target.getCentre().x - getCentre().x));
    moveY(percentage * (target.getCentre().y - getCentre().y));
    //todo check dist to target and if close enough, hit target.
    //projectile change image at which point?
  }
}

