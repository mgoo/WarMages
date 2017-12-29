package main.game.model.entity.unit;

import main.common.entity.Unit;
import main.common.images.GameImageResource;
import main.common.images.UnitSpriteSheet.Sequence;
import main.common.util.MapSize;
import main.common.entity.Projectile;
import main.game.model.entity.DefaultProjectile;
import main.game.model.entity.unit.attack.Attack;
import main.game.model.entity.unit.attack.BasicArrow;
import main.game.model.entity.unit.attack.Dagger;
import main.game.model.entity.unit.attack.FireBall;
import main.game.model.entity.unit.attack.IceBall;
import main.game.model.entity.unit.attack.Laser;
import main.game.model.entity.unit.attack.LaserWhite;
import main.game.model.entity.unit.attack.Spear;

/**
 * This enum represents the type of a Unit. A unit can be an archer, a swordsman, a spearman, or a
 * magician.
 * @author paladogabr
 */
public enum UnitType {

  ARCHER(100, 0.1, 6, Sequence.SHOOT, new BasicArrow()),

  SWORDSMAN(300, 0.1, 5,  Sequence.SLASH, new Dagger()),

  SPEARMAN(200, 0.1, 5, Sequence.THRUST, new Spear()),

  MAGE_FIRE(150, 0.1, 5, Sequence.SPELL_CAST, new FireBall()),
  MAGE_ICE(150, 0.1, 5, Sequence.SPELL_CAST, new IceBall()),

  WHITE_LASER(200, 0.08, 5,
      Sequence.SPELL_CAST, new LaserWhite()),
  LASER(200, 0.08, 5, Sequence.SPELL_CAST, new Laser());

  protected double startingHealth;
  protected double movingSpeed;
  protected double lineOfSight;
  protected Sequence attackSequence;
  protected Attack baseAttack;

  public double getStartingHealth() {
    return startingHealth;
  }

  public double getMovingSpeed() {
    return movingSpeed;
  }

  public Sequence getAttackSequence() {
    return attackSequence;
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
      int startingHealth, double movingSpeed, double lineOfSight, Sequence attackSequence,
      Attack baseAttack
  ) {
    assert startingHealth >= 0 : "Health should not be negative when starting";
    assert movingSpeed >= 0 : "Movement speed cannot be negative";
    assert lineOfSight >= 0 : "Line of Sight cannot be negative";

    this.startingHealth = startingHealth;
    this.movingSpeed = movingSpeed;
    this.lineOfSight = lineOfSight;
    this.attackSequence = attackSequence;
    this.baseAttack = baseAttack;
  }
}


