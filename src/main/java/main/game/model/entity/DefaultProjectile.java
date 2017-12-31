package main.game.model.entity;

import java.util.List;
import main.game.model.entity.unit.attack.Attack;
import main.game.model.entity.unit.state.Targetable;
import main.game.model.world.World;
import main.images.GameImage;
import main.util.MapPoint;
import main.util.MapSize;

/**
 * Concrete implementation of Projectile.
 *
 * @author paladogabr
 */
public class DefaultProjectile extends DefaultEntity implements Projectile {

  private static final long serialVersionUID = 1L;

  private static final double IMPACT_DISTANCE = 0.01;
  private static final double ANIMATION_SPEED = 0.4;

  private final Unit owner;
  private final Targetable target;
  private final Attack attack;
  private final List<GameImage> flyImages;
  private final List<GameImage> impactImages;
  private final MapSize impactSize;
  private final double moveDistancePerTick;

  private boolean hasHit = false;


  private double currentImage = 0;

  /**
   * Constructor takes the starting coordinates of the projectile, the size,
   * and the target of the projectile.
   * @param coordinates at start of projectile path.
   * @param size of projectile.
   * @param owner the unit that fired the projectile
   * @param flyImages images to show when the prjectile is flying
   * @param impactImages images to show when the projectile hits
   * @param moveDistancePerTick distance to be moved per tick.
   */
  public DefaultProjectile(
      MapPoint coordinates,
      MapSize size,
      Unit owner,
      Targetable target,
      Attack attack,
      List<GameImage> flyImages,
      List<GameImage> impactImages,
      MapSize impactSize,
      double moveDistancePerTick
  ) {
    super(coordinates, size);
    this.owner = owner;
    this.target = target;
    this.attack = attack;
    this.flyImages = flyImages;
    this.impactImages = impactImages;
    this.impactSize = impactSize;
    this.moveDistancePerTick = moveDistancePerTick;
  }

  @Override
  public GameImage getImage() {
    return this.flyImages.get((int)this.currentImage);
  }

  @Override
  public void tick(long timeSinceLastTick, World world) {
    if (hasHit) {
      throw new IllegalStateException();
    }

    this.currentImage += ANIMATION_SPEED;
    if (this.currentImage >= this.flyImages.size()) {
      this.currentImage = 0;
    }

    double distToTarget = getDistanceToTarget();
    // 0.5 if we move halfway there, 1 or greater if we move all the way there, etc
    double percentage = moveDistancePerTick / distToTarget;

    if (percentage >= 1) {
      percentage = 1; // teleport there because we are close enough
    }

    translatePosition(
        percentage * (this.target.getLocation().x - getCentre().x),
        percentage * (this.target.getLocation().y - getCentre().y)
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
  public void hitTarget(World world) {
    this.attack.getEffectedUnits(this.owner, world, target)
        .forEach(u -> u.takeDamage(this.attack.getModifiedDamage(owner), world, owner));
    world.removeProjectile(this);
    StaticEntity hitMarker = new StaticEntity(this.getTopLeft(),
        this.impactSize,
        this.impactImages,
        false,
        2
    );
    hitMarker.setLayer(1); // Same Layer as the projectile
    world.addStaticEntity(hitMarker);
  }

  @Override
  public double getDistanceToTarget() {
    return getCentre().distanceTo(target.getLocation());
  }
}
