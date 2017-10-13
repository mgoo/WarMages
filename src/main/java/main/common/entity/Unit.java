package main.common.entity;

import java.util.List;
import main.common.entity.usable.Effect;
import main.common.images.UnitSpriteSheet;
import main.common.util.MapPoint;
import main.game.model.entity.unit.state.DeadUnit;
import main.common.World;

/**
 * Unit extends{@link Entity}. A unit is a part of a team, specified by an enum colour. It has
 * health, and can attack other team units.
 */
public interface Unit extends Entity {

  /**
   * Applies the given damage to the Unit. Requires the amount given is a positive integer.
   */
  void takeDamage(int amount, World world);

  /**
   * Gives the Unit the given amount of health. Requires the amount given is a positive integer.
   */
  void gainHealth(int amount);

  /**
   * Returns the current health of the Unit.
   *
   * @return unt health of the Unit.
   */
  int getHealth();

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
   * Attacks the current target.
   */
  void attack();

  /**
   * Set's the Unit's target to the given Unit.
   *
   * @param target to be attacked
   */
  void setTarget(Unit target, World world);

  /**
   * Clears the current target.
   */
  void clearTarget();

  /**
   * Sets the damage amount of this Unit's attack to the given amount. Must be 0 < amount < 100.
   *
   * @param amount of damage to deal to target.
   */
  void setDamageAmount(int amount);

  /**
   * Returns the amount of damage dealt.
   *
   * @return int amount of damage dealt.
   */
  int getDamageAmount();

  /**
   * Sets the Unit's path to the given path.
   *
   * @param path list of MapPoints
   */
  void setPath(List<MapPoint> path);

  /**
   * Gets the percentage of health remaining. Should be below 1 and above 0 if alive.
   */
  double getHealthPercent();
}

