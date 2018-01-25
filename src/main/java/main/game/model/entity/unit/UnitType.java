package main.game.model.entity.unit;

import main.game.model.entity.unit.attack.Attack;
import main.game.model.entity.unit.attack.AttackType;
import main.game.model.entity.unit.attack.FixedAttack;
import main.game.model.entity.unit.attack.FixedAttack.CanEffect;
import main.images.UnitSpriteSheet.Sequence;

/**
 * This enum represents the type of a Unit. A unit can be an archer, a swordsman, a spearman, or a
 * magician.
 * @author paladogabr
 */
public enum UnitType {

  ARCHER(100, 0.1, 6,
      new Attack(
          CanEffect.ENEMIES,
          "resources/scripts/basicArrow.js",
          5,
          20,
          0.7,
          Sequence.SHOOT,
          AttackType.MISSILE,
          18
      )
  ),

  SWORDSMAN(300, 0.1, 5,
      new Attack(
          CanEffect.ENEMIES,
          "resources/scripts/instantDamage.js",
          0.02,
          14,
          0.66,
          Sequence.SLASH,
          AttackType.SLASH,
          15
      )
  ),

  SPEARMAN(200, 0.1, 5,
      new Attack(
          CanEffect.ENEMIES,
          "resources/scripts/instantDamage.js",
          0.2,
          16,
          0.625,
          Sequence.THRUST,
          AttackType.STAB,
          10
      )
  ),

  MAGE_FIRE(150, 0.1, 5,
      new Attack(
          CanEffect.ENEMIES,
          "resources/scripts/fireball.js",
          4,
          30,
          0.85,
          Sequence.SPELL_CAST,
          AttackType.MAGIC,
          60
      )
  ),
  MAGE_ICE(150, 0.1, 8,
      new Attack(
          CanEffect.ENEMIES,
          "resources/scripts/iceBall.js",
          4,
          30,
          0.85,
          Sequence.SPELL_CAST,
          AttackType.MAGIC,
          60
      )
  ),

  LASER(200, 0.08, 5,
      new Attack(
          CanEffect.ENEMIES,
          "resources/scripts/laser.js",
          3,
          1,
          0,
          Sequence.SPELL_CAST,
          AttackType.MAGIC,
          1
      )
  ),
  WHITE_LASER(200, 0.08, 5,
      new Attack(
          CanEffect.ENEMIES,
          "resources/scripts/laserWhite.js",
          3,
          1,
          0,
          Sequence.SPELL_CAST,
          AttackType.MAGIC,
          1
      )
  );

  protected double startingHealth;
  protected double movementSpeed;
  protected double lineOfSight;
  protected FixedAttack baseAttack;

  public double getStartingHealth() {
    return startingHealth;
  }

  public double getMovementSpeed() {
    return movementSpeed;
  }

  public FixedAttack getBaseAttack() {
    return this.baseAttack;
  }

  /**
   * Distance at which the unit decides to automatically attack another unit in sight.
   * TODO this might not be good here because lineOfSight can change in game
   */
  public double getAutoAttackDistance() {
    return lineOfSight * 0.8;
  }

  UnitType(
      int startingHealth, double movementSpeed, double lineOfSight, FixedAttack baseAttack
  ) {
    assert startingHealth >= 0 : "Health should not be negative when starting";
    assert movementSpeed >= 0 : "Movement speed cannot be negative";
    assert lineOfSight >= 0 : "Line of Sight cannot be negative";

    this.startingHealth = startingHealth;
    this.movementSpeed = movementSpeed;
    this.lineOfSight = lineOfSight;
    this.baseAttack = baseAttack;
  }
}


