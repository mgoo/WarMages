package main.game.model.entity;

import main.util.MapPoint;
import main.util.MapSize;

/**
 * Projectile extends {@link Entity}. A projectile is fired by a unit at a target (another unit) and
 * affects it in some way upon impact.
 */
public abstract class Projectile extends Entity {

  protected final Unit target;
  protected final int speed = 6;

  /**
   * Constructor takes the starting coordinates of the projectile, the size,
   * and the target of the projectile.
   * @param coordinates at start of projectile path.
   * @param size of projectile.
   * @param target unit of projectile.
   */
  public Projectile(MapPoint coordinates, MapSize size, Unit target) {
    super(coordinates, size);
    this.target = target;
  }

  /**
   * Applies actions to given unit when it is hit by the Projectile.
   * @param unit to be hit.
   */
  public abstract void hits(Unit unit);

  @Override
  public void tick(long timeSinceLastTick) {
    double distToTarget = Math.sqrt((Math.pow(target.getPosition().x - position.x, 2) + Math
        .pow(target.getPosition().y - position.y, 2)));
    double distToBeTravelled = speed * timeSinceLastTick; //todo finalize
    double percentage = distToBeTravelled / distToTarget;
    moveX(percentage * (target.getPosition().x - position.x));
    moveY(percentage * (target.getPosition().y - position.y));
    //todo check dist to target and if close enough, hit target.
    //projectile change image at which point?
  }
}

