package main.game.model.entity;

import main.images.GameImageResource;
import main.images.UnitSpriteSheet.Sequence;

/**
 * This enum represents the type of a Unit. A unit can be an archer, a swordsman, a spearman, or a
 * magician.
 */
public enum UnitType {

  //todo confirm attack and moving speeds
  ARCHER(5, 200, 5, 5, Sequence.SHOOT) {
    @Override
    public boolean canShootProjectiles() {
      return true;
    }

    @Override
    protected Projectile doCreateProjectile(Unit creator, Unit target) {
      return new PizzaBall(
          creator.getCentre(),
          creator.getSize().scaledBy(0.5),
          target,
          GameImageResource.ARROW_PROJECTILE.getGameImage()
      );
    }
  },

  SWORDSMAN(10, 250, 6, 5, Sequence.SLASH) {
    @Override
    public boolean canShootProjectiles() {
      return false;
    }
  },

  SPEARMAN(7, 150, 5, 5, Sequence.THRUST) {
    @Override
    public boolean canShootProjectiles() {
      return false;
    }
  },

  MAGICIAN(15, 300, 8, 7, Sequence.SPELL_CAST) {
    @Override
    public boolean canShootProjectiles() {
      return true;
    }

    @Override
    protected Projectile doCreateProjectile(Unit creator, Unit target) {
      return new PizzaBall(
          creator.getCentre(),
          creator.getSize().scaledBy(0.3),
          target,
          GameImageResource.FIREBALL_PROJECTILE.getGameImage()
      );
    }
  };

  protected int baselineDamage;
  protected int startingHealth;
  protected int attackSpeed;
  protected int movingSpeed;
  protected Sequence attackSequence;

  public int getBaselineDamage() {
    return baselineDamage;
  }

  public int getStartingHealth() {
    return startingHealth;
  }

  public int getAttackSpeed() {
    return attackSpeed;
  }

  public int getMovingSpeed() {
    return movingSpeed;
  }

  public Sequence getAttackSequence() {
    return attackSequence;
  }

  /**
   * Creates an appropriate projectile for this {@link UnitType}.
   */
  public final Projectile createProjectile(Unit creator, Unit target) {
    if (!canShootProjectiles()) {
      throw new IllegalStateException();
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
      int baselineDamage, int startingHealth, int attackSpeed, int movingSpeed,
      Sequence attackSequence
  ) {
    this.baselineDamage = baselineDamage;
    this.startingHealth = startingHealth;
    this.attackSpeed = attackSpeed;
    this.movingSpeed = movingSpeed;
    this.attackSequence = attackSequence;
  }
}


