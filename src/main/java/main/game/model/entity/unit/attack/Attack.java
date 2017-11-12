package main.game.model.entity.unit.attack;

import main.common.World;
import main.common.entity.Unit;

/**
 * Base class for Attacks.
 * @author Andrew McGhie
 */
public abstract class Attack {

  /**
   * Make the enemy unit take damage.
   */
  public abstract void execute(Unit unit, Unit enemyUnit, World world);

  public double getModifiedRange(Unit unit) {
    return this.getRange(unit) * unit.getRangeModifier();
  }

  public int getModifiedAttackSpeed(Unit unit) {
    return (int)(this.getAttackSpeed(unit) * unit.getAttackSpeedModifier());
  }

  public double getModifiedDamage(Unit unit) {
    return this.getDamage(unit) * unit.getDamageModifier();
  }

  /**
   * The base range of the attack.
   */
  abstract double getRange(Unit unit);

  /**
   * The base time that the animation should take.
   */
  abstract int getAttackSpeed(Unit unit);

  /**
   * Base damage of the attack.
   */
  abstract double getDamage(Unit unit);

  /**
   * The portion of the animation that plays before the attack should trigger.
   */
  public abstract double getWindupPortion(Unit unit);

  /**
   * Get the type of attack.
   */
  abstract AttackType getType(Unit unit);
}
