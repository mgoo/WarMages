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

  private static final double IMPACT_DISTANCE = 0.01;
  private static final double DISTANCE_PER_TICK = 0.1;

  protected final Unit target; // todo private?
  protected final int damageAmount; // todo final

  private boolean hasHit = false;

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
   * @param target to be hit.
   */
  protected abstract void hitTarget(Unit target, World world);

  @Override
  public void tick(long timeSinceLastTick, World world) {
    if (hasHit) {
      throw new IllegalStateException();
    }

    double distToTarget = getDistanceToTarget();
    // 0.5 if we move halfway there, 1 or greater if we move all the way there, etc
    double percentage = DISTANCE_PER_TICK / distToTarget;

    if (percentage >= 1) {
      percentage = 1; // teleport there because we are close enough
    }

    MapPoint centre = getCentre();
    moveTo(new MapPoint(
        getTopLeft().x + (percentage * (target.getCentre().x - centre.x)),
        getTopLeft().y + (percentage * (target.getCentre().y - centre.y))
    ));

    if (getDistanceToTarget() <= IMPACT_DISTANCE) {
      hasHit = true;
      hitTarget(target, world);
    }
  }

  public boolean hasHit() {
    return hasHit;
  }

  public int getDamageAmount() {
    return damageAmount;
  }

  private double getDistanceToTarget() {
    return getCentre().distanceTo(target.getCentre());
  }
}

