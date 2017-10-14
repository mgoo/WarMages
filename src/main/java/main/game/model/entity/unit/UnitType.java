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

  ARCHER(15, 200, 5, 0.1, 6, 5, Sequence.SHOOT) {
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
          GameImageResource.ARROW_PROJECTILE.getGameImage(),
          creator.getDamageAmount(),
          0.5
      );
    }
  },

  SWORDSMAN(10, 250, 6, 0.1, 5, 0.05, Sequence.SLASH) {
    @Override
    public boolean canShootProjectiles() {
      return false;
    }
  },

  SPEARMAN(8, 200, 5, 0.1, 5, 0.05, Sequence.THRUST) {
    @Override
    public boolean canShootProjectiles() {
      return false;
    }
  },

  MAGICIAN(20, 250, 8, 0.1, 7, 4, Sequence.SPELL_CAST) {
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
          GameImageResource.FIREBALL_PROJECTILE.getGameImage(),
          creator.getDamageAmount(),
          0.3
      );
    }
  },
  LASER(20, 100, 8, 0.08, 10, 2, Sequence.SPELL_CAST) {
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
          GameImageResource.FIREBALL_PROJECTILE.getGameImage(),
          (int) (creator.getDamageAmount() * 0.06),
          0.1
      );
    }
  };

  protected int baselineDamage;
  protected int startingHealth;
  protected double attackSpeed;
  protected double movingSpeed;
  protected double lineOfSight;
  protected double attackDistance;
  protected Sequence attackSequence;

  public int getBaselineDamage() {
    return baselineDamage;
  }

  public int getStartingHealth() {
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
      int baselineDamage, int startingHealth, double attackSpeed, double movingSpeed,
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


