package main.game.model.entity.unit.attack;

import java.io.Serializable;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import main.game.model.entity.Unit;
import main.game.model.entity.unit.state.Targetable;
import main.game.model.world.World;
import main.images.UnitSpriteSheet.Sequence;

/**
 * Base class for Attacks.
 * @author Andrew McGhie
 */
public abstract class Attack implements Serializable {

  public final CanEffect canEffectUnits;

  public enum CanEffect {
    EVERYONE,
    ALLIES,
    ENEMIES
  }

  private static final long serialVersionUID = 1L;

  public Attack(CanEffect canEffect) {
    this.canEffectUnits = canEffect;
  }

  /**
   * Make the enemy unit take damage.
   */
  public abstract void execute(Unit unit, Targetable target, World world);

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
   * Gets the sequence that the unit should do when doing the attack.
   */
  public abstract Sequence getAttackSequence();

  /**
   * Get the type of attack.
   */
  abstract AttackType getType(Unit unit);

  /**
   * Gets the Units that are effected by the attack at the target.
   */
  public Collection<Unit> getEffectedUnits(Unit owner, World world, Targetable target) {
    Predicate<Unit> filter;
    if (this.canEffectUnits == CanEffect.ENEMIES) {
      filter = u -> owner.getTeam().canAttack(u.getTeam());
    } else if (this.canEffectUnits == CanEffect.ALLIES) {
      filter = u -> !owner.getTeam().canAttack(u.getTeam());
    } else {
      filter = u -> true;
    }
    return target.getEffectedUnits(world)
        .stream()
        .filter(filter)
        .collect(Collectors.toSet());
  }
}
