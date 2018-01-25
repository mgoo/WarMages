package main.game.model.entity.unit.attack;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import main.game.model.entity.Unit;
import main.game.model.entity.unit.state.Targetable;
import main.game.model.world.World;
import main.images.UnitSpriteSheet.Sequence;

/**
 * Base class for Attacks.
 * @author Andrew McGhie
 */
public class FixedAttack implements Serializable {

  public final CanEffect canEffectUnits;
  private final String scriptLocation;
  private final double range;
  private final int attackSpeed;
  private final double windupPortion;
  private final Sequence attackSequence;
  private final AttackType attackType;

  public enum CanEffect {
    EVERYONE,
    ALLIES,
    ENEMIES
  }

  private static final long serialVersionUID = 1L;
  private transient Invocable engine;

  public FixedAttack(CanEffect canEffect,
                     String scriptLocation,
                     double range,
                     int attackSpeed,
                     double windupPortion,
                     Sequence attackSequence,
                     AttackType attackType) {
    this.canEffectUnits = canEffect;
    this.scriptLocation = scriptLocation;
    this.range = range;
    this.attackSpeed = attackSpeed;
    this.windupPortion = windupPortion;
    this.attackSequence = attackSequence;
    this.attackType = attackType;

    ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
    try {
      engine.eval(new FileReader(scriptLocation));
    } catch (ScriptException | FileNotFoundException e) {
      e.printStackTrace();
    }
    this.engine = (Invocable) engine;
  }

  /**
   * Called during deserialisation.
   */
  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject();
    ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
    try {
      engine.eval(new FileReader(this.scriptLocation));
    } catch (ScriptException | FileNotFoundException e) {
      e.printStackTrace();
    }
    this.engine = (Invocable) engine;
  }

  /**
   * Make the enemy unit take damage.
   */
  public void execute(Unit unit, Targetable target, World world) {
    try {
      this.engine.invokeFunction("apply", unit, target, this, world);
    } catch (ScriptException | NoSuchMethodException e) {
      e.printStackTrace();
    }
  }

  public double getModifiedRange(Unit unit) {
    return this.getRange() * unit.getRangeModifier();
  }

  public int getModifiedAttackSpeed(Unit unit) {
    return (int)(this.getAttackSpeed() * unit.getAttackSpeedModifier());
  }

  /**
   * The base range of the attack.
   */
  double getRange() {
    return this.range;
  }

  /**
   * The base time that the animation should take.
   */
  int getAttackSpeed() {
    return this.attackSpeed;
  }

  /**
   * The portion of the animation that plays before the attack should trigger.
   */
  public double getWindupPortion() {
    return this.windupPortion;
  }

  /**
   * Gets the sequence that the unit should do when doing the attack.
   */
  public Sequence getAttackSequence() {
    return this.attackSequence;
  }

  /**
   * Get the type of attack.
   */
  AttackType getType() {
    return this.attackType;
  }

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
