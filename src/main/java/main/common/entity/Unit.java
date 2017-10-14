package main.common.entity;

import main.common.entity.usable.Effect;
import main.common.images.UnitSpriteSheet;
import main.common.util.MapPoint;
import main.game.model.entity.unit.DeadUnit;
import main.game.model.entity.unit.UnitType;
import main.game.model.world.World;

/**
 * Unit extends{@link Entity}. A unit is a part of a team, specified by an enum colour. It has
 * health, and can attack other team units.
 */
public interface Unit extends Entity {

  /**
   * Applies the given damage to the Unit. Requires the amount given is a positive integer.
   * @return returns true if the entity died
   */
  boolean takeDamage(double amount, World world);

  /**
   * Gives the Unit the given amount of health. Requires the amount given is a positive integer.
   */
  void gainHealth(double amount);

  /**
   * Returns the current health of the Unit.
   *
   * @return unt health of the Unit.
   */
  double getHealth();

  double getLineOfSight();

  /**
   * Returns a DeadUnit to replace the current Unit when it dies.
   *
   * @return DeadUnit to represent dead current Unit.
   */
  DeadUnit createDeadUnit();

  Team getTeam();

  Unit getTarget();

  void addEffect(Effect effect);

  UnitSpriteSheet getSpriteSheet();

  Direction getCurrentDirection();

  /**
   * Set's the Unit's target to the given Unit.
   *
   * @param targetUnit to be attacked
   */
  void setTargetUnit(Unit targetUnit);

  /**
   * Set's the Unit's target to the given point.
   */
  void setTargetPoint(MapPoint targetPoint);

  /**
   * Clears the current target.
   */
  void clearTarget();

  /**
   * Returns the amount of damage dealt.
   *
   * @return int amount of damage dealt.
   */
  double getDamageAmount();

  /**
   * Gets the percentage of health remaining. Should be below 1 and above 0 if alive.
   */
  double getHealthPercent();

  /**
   * Gets the base type of the unit.
   */
  UnitType getType();

  /**
   * Elevates the unit to the next level.
   */
  void nextLevel();

  /**
   * Gets the current level that the unit is on.
   */
  int getLevel();
}

