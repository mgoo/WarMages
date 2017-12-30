package main.common.entity;

import main.common.entity.usable.Effect;
import main.common.images.GameImage;
import main.common.images.UnitSpriteSheet;
import main.common.World;
import main.game.model.entity.unit.UnitType;
import main.game.model.entity.unit.attack.Attack;
import main.game.model.entity.unit.state.Target;

/**
 * Unit extends{@link Entity}. A unit is a part of a team, specified by an enum colour. It has
 * health, and can attack other team units.
 * @author paladogabr
 */
public interface Unit extends Entity {

  /**
   * Applies the given damage to the Unit. Requires the amount given is a positive integer.
   */
  void takeDamage(double amount, World world, Unit attacker);

  /**
   * Gives the Unit the given amount of health. Requires the amount given is a positive integer.
   */
  void gainHealth(double amount);

  /**
   * Gives the Unit the given amount of health. Requires the amount given is a positive integer.
   * Will add effects to the world
   */
  void gainHealth(double amount, World world);

  /**
   * Returns the current health of the Unit.
   *
   * @return unt health of the Unit.
   */
  double getHealth();

  /**
   * Gets the maximum health of the unit after appling the level multiplyer
   *
   * @return max health of unit
   */
  double getMaxHealth();

  /**
   * Gets the percentage of health remaining. Should be below 1 and above 0 if alive.
   */
  double getHealthPercent();

  double getLineOfSight();

  /**
   * Returns a DeadUnit to replace the current Unit when it dies.
   *
   * @return DeadUnit to represent dead current Unit.
   */
  DeadUnit createDeadUnit();

  Team getTeam();

  void addEffect(Effect effect);

  UnitSpriteSheet getSpriteSheet();

  Direction getCurrentDirection();

  /**
   * Set's the Unit's target to the given Unit.
   */
  void setTarget(Target target);

  /**
   * Returns a boolean based on whether the given unit is the same type as the current unit.
   *
   * @param other unit to be compared to
   * @return boolean same (true) or different (false)
   */
  boolean isSameTypeAs(Unit other);

  /**
   * Returns the string representation of the Unit's type.
   *
   * @return string type of unit.
   */
  String getType();

  /**
   * Gets the Type of the unit.
   */
  UnitType getUnitType();

  /**
   * Elevates the unit to the next level.
   */
  void nextLevel();

  /**
   * Gets the current level that the unit is on.
   */
  int getLevel();

  /**
   * Gets the icon of the unit to display on the hud.
   */
  GameImage getIcon();

  /**
   * Gets the amount that the attack speed should be multiplied by.
   */
  double getAttackSpeedModifier();

  /**
   * Returns the amount that the damage should be multiplied by.
   */
  double getDamageModifier();

  /**
   * Gets the amount that the range of the unit should be multiplied by.
   */
  double getRangeModifier();

  /**
   * Gets the movement speed of the unit.
   */
  double getSpeed();
}

