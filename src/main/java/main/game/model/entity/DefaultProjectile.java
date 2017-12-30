package main.game.model.entity;

import static java.util.Objects.requireNonNull;

import java.util.List;
import main.common.World;
import main.common.entity.Direction;
import main.common.entity.Projectile;
import main.common.entity.Unit;
import main.common.images.GameImage;
import main.common.images.SpriteSheet;
import main.common.images.SpriteSheet.Sequence;
import main.common.util.MapPoint;
import main.common.util.MapSize;
import main.game.model.entity.unit.attack.Attack;

/**
 * Concrete implementation of Projectile.
 *
 * @author paladogabr
 */
public class DefaultProjectile extends DefaultEntity implements Projectile {

  private static final long serialVersionUID = 1L;

  private static final double IMPACT_DISTANCE = 0.01;
  private static final double ANIMATION_SPEED = 0.4;

  private final Unit target;
  private final Unit owner;
  private final Attack attack;
  private final Sequence impactSequence;
  private final double moveDistancePerTick;

  private Direction direction;
  private boolean hasHit = false;
  private SpriteSheet.Sheet spriteSheet;
  private List<GameImage> flingImages;
  private double currentImage = 0;

  /**
   * Constructor takes the starting coordinates of the projectile, the size,
   * and the target of the projectile.
   * @param coordinates at start of projectile path.
   * @param size of projectile.
   * @param target unit of projectile.
   * @param owner the unit that fired the projectile
   * @param spriteSheet the spritesheet for the projectile
   * @param flySequence the animation that happens when the projectile is flying
   * @param impactSequence the animation to play once when the projectile hits
   * @param moveDistancePerTick distance to be moved per tick.
   */
  public DefaultProjectile(
      MapPoint coordinates,
      MapSize size,
      Unit target,
      Unit owner,
      Attack attack,
      SpriteSheet.Sheet spriteSheet,
      Sequence flySequence,
      Sequence impactSequence,
      double moveDistancePerTick
  ) {
    super(coordinates, size);
    this.target = requireNonNull(target);
    this.owner = owner;
    this.attack = attack;
    this.impactSequence = impactSequence;
    this.moveDistancePerTick = moveDistancePerTick;
    this.spriteSheet = spriteSheet;
    this.direction = Direction.between(owner.getCentre(), target.getCentre());
    this.flingImages = spriteSheet.getImagesForSequence(flySequence, this.direction);
  }

  @Override
  public GameImage getImage() {
    return this.flingImages.get((int)this.currentImage);
  }

  @Override
  public void tick(long timeSinceLastTick, World world) {
    if (hasHit) {
      throw new IllegalStateException();
    }

    this.currentImage += ANIMATION_SPEED;
    if (this.currentImage >= this.flingImages.size()) {
      this.currentImage = 0;
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
  public void hitTarget(World world) {
    target.takeDamage(this.attack.getModifiedDamage(owner), world, owner);
    world.removeProjectile(this);
    StaticEntity hitMarker = new StaticEntity(this.getTopLeft(),
        this.getSize(),
        this.spriteSheet.getImagesForSequence(this.impactSequence, this.direction),
        false,
        2
    );
    hitMarker.setLayer(1); // Same Layer as the projectile
    world.addStaticEntity(hitMarker);
  }

  @Override
  public double getDistanceToTarget() {
    return getCentre().distanceTo(target.getCentre());
  }
}
