package main.game.model.entity.unit;

import main.common.Unit;
import main.common.images.GameImageResource;
import main.common.images.UnitSpriteSheet.Sequence;
import main.game.model.entity.Projectile;

/**
 * This enum represents the type of a Unit. A unit can be an archer, a swordsman, a spearman, or a
 * magician.
 */
public enum UnitType {

  //todo confirm attack and moving speeds
  ARCHER(5, 200, 5, 0.1, 6, Sequence.SHOOT) {
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

  SWORDSMAN(10, 250, 6, 0.1, 5, Sequence.SLASH) {
    @Override
    public boolean canShootProjectiles() {
      return false;
    }
  },

  SPEARMAN(7, 150, 5, 0.1, 5, Sequence.THRUST) {
    @Override
    public boolean canShootProjectiles() {
      return false;
    }
  },

  MAGICIAN(15, 300, 8, 0.1, 7, Sequence.SPELL_CAST) {
    @Override
    public boolean canShootProjectiles() {
      return true;
    }

    @Override
    protected Projectile doCreateProjectile(Unit creator, Unit target) {
      return new Projectile(
          creator.getCentre(),
          creator.getSize().scaledBy(0.3),
          target,
          GameImageResource.FIREBALL_PROJECTILE.getGameImage(),
          creator.getDamageAmount(),
          0.3
      );
    }
  };

  protected int baselineDamage;
  protected int startingHealth;
  protected double attackSpeed;
  protected double movingSpeed;
  protected double lineOfSight;
  protected Sequence attackSequence;

  public int getBaselineDamage() {
    return baselineDamage;
  }

  public int getStartingHealth() {
    return startingHealth;
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
      double lineOfSight,
      Sequence attackSequence
  ) {
    this.baselineDamage = baselineDamage;
    this.startingHealth = startingHealth;
    this.attackSpeed = attackSpeed;
    this.movingSpeed = movingSpeed;
    this.lineOfSight = lineOfSight;
    this.attackSequence = attackSequence;

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


