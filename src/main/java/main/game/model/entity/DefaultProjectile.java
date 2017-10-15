package main.game.model.entity;

import static java.util.Objects.requireNonNull;

import main.common.World;
import main.common.entity.Unit;
import main.common.images.GameImage;
import main.common.util.MapPoint;
import main.common.util.MapSize;

/**
 * Concrete implementation of Projectile.
 *
 * @author paladogabr
 */
public class DefaultProjectile extends DefaultEntity implements Projectile{

  private static final long serialVersionUID = 1L;
  private static final double IMPACT_DISTANCE = 0.01;

  private final double damageAmount;
  private final Unit target;
  private final Unit owner;
  private final double moveDistancePerTick;

  private boolean hasHit = false;
  private GameImage image;

  /**
   * Constructor takes the starting coordinates of the projectile, the size,
   * and the target of the projectile.
   * @param coordinates at start of projectile path.
   * @param size of projectile.
   * @param target unit of projectile.
   * @param owner the unit that fired the projectile
   * @param gameImage image of the projectile.
   * @param moveDistancePerTick distance to be moved per tick.
   */
  public DefaultProjectile(
      MapPoint coordinates,
      MapSize size,
      Unit target,
      Unit owner,
      GameImage gameImage,
      double moveDistancePerTick
  ) {
    super(coordinates, size);
    this.target = requireNonNull(target);
    this.owner = owner;
    // So level ups and buffs do not effect fired projectile damage
    this.damageAmount = owner.getDamageAmount();
    this.moveDistancePerTick = moveDistancePerTick;
    this.image = gameImage;
  }

  @Override
  public GameImage getImage() {
    return image;
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

  @Override
  public boolean contains(MapPoint point) {
    return getRect().contains(point);
  }

  @Override
  public boolean hasHit() {
    return hasHit;
  }

  @Override
  public double getDamageAmount() {
    return damageAmount;
  }

  @Override
  public void hitTarget(World world) {
    boolean killed = target.takeDamage(damageAmount, world);
    if (killed) {
      this.owner.nextLevel();
    }
    world.removeProjectile(this);
  }

  @Override
  public double getDistanceToTarget() {
    return getCentre().distanceTo(target.getCentre());
  }
}
