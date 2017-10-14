package main.game.model.entity.unit;

import main.common.entity.Unit;
import main.common.images.GameImageResource;
import main.common.images.UnitSpriteSheet.Sequence;
import main.common.util.MapSize;
import main.game.model.entity.Projectile;

/**
 * This enum represents the type of a Unit. A unit can be an archer, a swordsman, a spearman, or a
 * magician.
 * @author paladogabr
 */
public enum UnitType {

  ARCHER(30, 150, 3, 0.1, 6, 5, Sequence.SHOOT) {
    @Override
    public boolean canShootProjectiles() {
      return true;
    }

    @Override
    protected Projectile doCreateProjectile(Unit creator, Unit target) {
      return new Projectile(
          creator.getCentre(),
          creator.getSize().scaledBy(0.5),
          target,
          creator,
          GameImageResource.ARROW_PROJECTILE.getGameImage(),
          0.5
      );
    }
  },

  SWORDSMAN(8, 300, 6, 0.1, 5, 0.7, Sequence.SLASH) {
    @Override
    public boolean canShootProjectiles() {
      return false;
    }
  },

  SPEARMAN(8, 200, 5, 0.1, 5, 1, Sequence.THRUST) {
    @Override
    public boolean canShootProjectiles() {
      return false;
    }
  },

  MAGICIAN(20, 250, 8, 0.1, 5, 4, Sequence.SPELL_CAST) {
    @Override
    public boolean canShootProjectiles() {
      return true;
    }

    @Override
    protected Projectile doCreateProjectile(Unit creator, Unit target) {
      return new Projectile(
          creator.getCentre(),
          creator.getSize().scaledBy(0.6),
          target,
          creator,
          GameImageResource.FIREBALL_PROJECTILE.getGameImage(),
          0.3
      );
    }
  },
  LASER(20  * 0.06, 100, 8, 0.08, 5, 2, Sequence.SPELL_CAST) {
    @Override
    public boolean canShootProjectiles() {
      return true;
    }

    @Override
    protected Projectile doCreateProjectile(Unit creator, Unit target) {
      MapSize creatorSize = creator.getSize();
      return new Projectile(
          creator.getCentre().translate(creatorSize.width * 0.2, creatorSize.height * 0.2),
          creatorSize.scaledBy(0.4),
          target,
          creator,
          GameImageResource.FIREBALL_PROJECTILE.getGameImage(),
          0.1
      );
    }
  };

  protected double baselineDamage;
  protected double startingHealth;
  protected double attackSpeed;
  protected double movingSpeed;
  protected double lineOfSight;
  protected double attackDistance;
  protected Sequence attackSequence;

  public double getBaselineDamage() {
    return baselineDamage;
  }

  public double getStartingHealth() {
    return startingHealth;
  }

  public double getAttackDistance() {
    return attackDistance;
  }

  public double getAttackSpeed() {
    return attackSpeed;
  }

  public double getMovingSpeed() {
    return movingSpeed;
  }

  public Sequence getAttackSequence() {
    return attackSequence;
  }

  /**
   * Distance at which the unit decides to automatically attack another unit in sight.
   */
  public double getAutoAttackDistance() {
    return lineOfSight * 0.8;
  }

  /**
   * Creates an appropriate projectile for this {@link UnitType}.
   */
  public final Projectile createProjectile(DefaultUnit creator, Unit target) {
    if (!canShootProjectiles()) {
      throw new UnsupportedOperationException();
    }
    if (creator.getUnitType() != this) {
      throw new IllegalArgumentException();
    }

    return doCreateProjectile(creator, target);
  }

  protected Projectile doCreateProjectile(Unit creator, Unit target) {
    throw new UnsupportedOperationException("Override me to create projectiles");
  }

  public abstract boolean canShootProjectiles();

  UnitType(
      double baselineDamage, int startingHealth, double attackSpeed, double movingSpeed,
      double lineOfSight, double attackDistance, Sequence attackSequence
  ) {
    this.baselineDamage = baselineDamage;
    this.startingHealth = startingHealth;
    this.attackSpeed = attackSpeed;
    this.movingSpeed = movingSpeed;
    this.lineOfSight = lineOfSight;
    this.attackSequence = attackSequence;
    this.attackDistance = attackDistance;

    if (lineOfSight < attackDistance) {
      throw new IllegalArgumentException();
    }

    if (canShootProjectiles()) {
      try {
        attackSequence.getAttackFrame();
      } catch (IllegalStateException e) {
        throw new Error(String.format(
            "%s maps to %s that cannot attack",
            this,
            attackSequence
        ));
      }
    }
  }
}


