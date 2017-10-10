package main.game.model.entity;

import static java.util.Objects.requireNonNull;

import main.game.model.world.World;
import main.common.images.GameImage;
import main.common.util.MapPoint;
import main.common.util.MapSize;

/**
 * Projectile extends {@link Entity}. A projectile is fired by a unit at a target (another unit) and
 * affects it in some way upon impact.
 */
public class Projectile extends Entity {

  private static final long serialVersionUID = 1L;

  private static final double IMPACT_DISTANCE = 0.01;

  private final int damageAmount;
  private final Unit target;
  private final double moveDistancePerTick;

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
      int damageAmount,
      double moveDistancePerTick
  ) {
    super(coordinates, size);
    this.target = requireNonNull(target);
    this.image = requireNonNull(gameImage);
    this.damageAmount = damageAmount;
    this.moveDistancePerTick = moveDistancePerTick;
  }

  @Override
  public void tick(long timeSinceLastTick, World world) {
    if (hasHit) {
      throw new IllegalStateException();
    }

    double distToTarget = getDistanceToTarget();
    // 0.5 if we move halfway there, 1 or greater if we move all the way there, etc
    double percentage = moveDistancePerTick / distToTarget;

    if (percentage >= 1) {
      percentage = 1; // teleport there because we are close enough
    }

    translatePosition(
        percentage * (target.getCentre().x - getCentre().x),
        percentage * (target.getCentre().y - getCentre().y)
    );

    if (getDistanceToTarget() <= IMPACT_DISTANCE) {
      hasHit = true;
      hitTarget(world);
    }
  }

  public boolean hasHit() {
    return hasHit;
  }

  public int getDamageAmount() {
    return damageAmount;
  }

  /**
   * Applies actions to given unit when it is hit by the Projectile.
   */
  private void hitTarget(World world) {
    target.takeDamage(damageAmount, world);
    world.removeProjectile(this);
  }

  private double getDistanceToTarget() {
    return getCentre().distanceTo(target.getCentre());
  }
}

