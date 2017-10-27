package main.game.model.entity.unit;

import static main.game.model.entity.unit.UnitType.Constants.RAINY_MAGE_NUMBER_OF_PROJECTILES;
import static main.game.model.entity.unit.UnitType.Constants.LASER_POSITION_OFFSET;

import main.common.entity.Unit;
import main.common.images.GameImageResource;
import main.common.images.UnitSpriteSheet.Sequence;
import main.common.util.MapSize;
import main.common.entity.Projectile;
import main.game.model.entity.DefaultProjectile;

/**
 * This enum represents the type of a Unit. A unit can be an archer, a swordsman, a spearman, or a
 * magician.
 * @author paladogabr
 */
public enum UnitType {

  ARCHER(25, 100, 3, 0.1, 6, 5, Sequence.SHOOT) {
    @Override
    public boolean canShootProjectiles() {
      return true;
    }

    @Override
    protected Projectile doCreateProjectile(Unit creator, Unit target) {
      return new DefaultProjectile(
          creator.getCentre(),
          new MapSize(0.4, 0.4),
          target,
          creator,
          GameImageResource.ARROW_PROJECTILE.getGameImage(),
          0.5
      );
    }
  },

  SWORDSMAN(5, 300, 6, 0.1, 5, 0.05, Sequence.SLASH) {
    @Override
    public boolean canShootProjectiles() {
      return false;
    }
  },

  SPEARMAN(15, 200, 5, 0.1, 5, 0.2, Sequence.THRUST) {
    @Override
    public boolean canShootProjectiles() {
      return false;
    }
  },

  MAGE(20, 150, 8, 0.1, 5, 4, Sequence.SPELL_CAST) {
    @Override
    public boolean canShootProjectiles() {
      return true;
    }

    @Override
    protected Projectile doCreateProjectile(Unit creator, Unit target) {
      return new DefaultProjectile(
          creator.getCentre(),
          new MapSize(0.5, 0.5),
          target,
          creator,
          GameImageResource.FIREBALL_PROJECTILE.getGameImage(),
          0.3
      );
    }
  },

  RAINY_MAGE(25 / RAINY_MAGE_NUMBER_OF_PROJECTILES, 200, 8, 1.08, 5, 4, Sequence.SPELL_CAST) {
    @Override
    public boolean canShootProjectiles() {
      return true;
    }

    @Override
    protected Projectile doCreateProjectile(Unit creator, Unit target) {
      MapSize projectileSize = new MapSize(0.3, 0.3);
      double angle = Math.random() * Math.PI * 2;
      double radius = 10 + (Math.random() * 0.1);
      return new DefaultProjectile(
          target.getCentre()
              .translate(
                  radius * Math.cos(angle),
                  radius * Math.sin(angle)
              )
              .translate(
                  -projectileSize.width / 2,
                  -projectileSize.height / 2
              ),
          projectileSize,
          target,
          creator,
          Math.random() > 0.5
              ? GameImageResource.WHITE_PROJECTILE.getGameImage()
              : GameImageResource.FIREBALL_PROJECTILE.getGameImage(),
          0.5
      );
    }

    @Override
    public int getProjectilesPerAttack() {
      return RAINY_MAGE_NUMBER_OF_PROJECTILES;
    }
  },

  WHITE_LASER(25 / Sequence.SPELL_CAST.numberOfColumns, 200, 8, 0.08, 5, 3, Sequence.SPELL_CAST) {
    @Override
    public boolean canShootProjectiles() {
      return true;
    }

    @Override
    protected Projectile doCreateProjectile(Unit creator, Unit target) {
      MapSize projectileSize = new MapSize(0.3, 0.3);
      return new DefaultProjectile(
          creator.getCentre().translate(
              -projectileSize.width / 2 + (creator.getSize().width * LASER_POSITION_OFFSET),
              -projectileSize.height / 2 + (creator.getSize().height * LASER_POSITION_OFFSET)
          ),
          projectileSize,
          target,
          creator,
          GameImageResource.WHITE_PROJECTILE.getGameImage(),
          0.1
      );
    }

    @Override
    public boolean attacksEveryTick() {
      return true;
    }
  },

  LASER(25 / Sequence.SPELL_CAST.numberOfColumns, 200, 8, 0.08, 5, 3, Sequence.SPELL_CAST) {
    @Override
    public boolean canShootProjectiles() {
      return true;
    }

    @Override
    protected Projectile doCreateProjectile(Unit creator, Unit target) {
      MapSize projectileSize = new MapSize(0.3, 0.3);
      return new DefaultProjectile(
          creator.getCentre().translate(
              -projectileSize.width / 2 + (creator.getSize().width * LASER_POSITION_OFFSET),
              -projectileSize.height / 2 + (creator.getSize().height * LASER_POSITION_OFFSET)
          ),
          projectileSize,
          target,
          creator,
          GameImageResource.FIREBALL_PROJECTILE.getGameImage(),
          0.1
      );
    }

    @Override
    public boolean attacksEveryTick() {
      return true;
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

  public boolean attacksEveryTick() {
    return false;
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

  public int getProjectilesPerAttack() {
    if (!canShootProjectiles()) {
      throw new UnsupportedOperationException();
    }

    return 1;
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

  // Not private just to allow for static import.
  // Also required because you can't forward reference enum constants in the constructors.
  static class Constants {
    static final int RAINY_MAGE_NUMBER_OF_PROJECTILES = 8;
    static final double LASER_POSITION_OFFSET = 0.45;
  }
}
