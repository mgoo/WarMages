package main.game.model.entity.unit;

import main.game.model.entity.unit.attack.Attack;
import main.game.model.entity.unit.attack.AttackCache;
import main.game.model.entity.unit.attack.AttackType;
import main.images.UnitSpriteSheet.Sequence;

/**
 * This enum represents the type of a Unit. A unit can be an archer, a swordsman, a spearman, or a
 * magician.
 * @author paladogabr
 */
public enum UnitType {

  ARCHER(100, 0.1, 6, AttackCache.BASIC_ARROW),

  SWORDSMAN(300, 0.1, 5, AttackCache.DAGGER),

  SPEARMAN(200, 0.1, 5, AttackCache.SPEAR),

  MAGE_FIRE(150, 0.1, 5, AttackCache.FIREBALL),
  MAGE_ICE(150, 0.1, 8, AttackCache.ICEBALL),

  LASER(200, 0.08, 5, AttackCache.LASER),
  WHITE_LASER(200, 0.08, 5, AttackCache.ICE_LASER),

  TURRET(50, 0, 4, AttackCache.FIREBALL);

  protected double startingHealth;
  protected double movementSpeed;
  protected double lineOfSight;
  protected Attack baseAttack;

  public double getStartingHealth() {
    return startingHealth;
  }

  public double getMovementSpeed() {
    return movementSpeed;
  }

  public Attack getBaseAttack() {
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
      int startingHealth, double movementSpeed, double lineOfSight, Attack baseAttack
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


