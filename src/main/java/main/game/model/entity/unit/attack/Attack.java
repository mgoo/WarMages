package main.game.model.entity.unit.attack;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import main.game.model.data.DataLoader;
import main.game.model.data.dataobject.AnimationData;
import main.game.model.data.dataobject.AttackData;
import main.game.model.entity.Unit;
import main.game.model.entity.unit.state.Targetable;
import main.game.model.world.World;

/**
 * Base class for Attacks.
 * @author Andrew McGhie
 */
public class Attack implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final double INSTANT_DURATION = 0;
  public static final double FIXED_AMOUNT = 0;

  private final AttackType attackType;

  private final AttackData data;

  private transient Invocable engine;

  // Save the data loader only so it can be loaded back into the script engine on deserialization.
  private final DataLoader dataLoader;

  public Attack(AttackData data, DataLoader dataLoader) {
    this.attackType = AttackType.valueOf(data.getType());
    this.dataLoader = dataLoader;
    this.data = data;

    ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
    try {
      engine.eval(new FileReader(this.data.getScriptLocation()));
      engine.put("dataLoader", dataLoader);
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
      engine.eval(new FileReader(this.data.getScriptLocation()));
      engine.put("dataLoader", dataLoader);
    } catch (ScriptException | FileNotFoundException e) {
      e.printStackTrace();
    }
    this.engine = (Invocable) engine;
  }

  /**
   * Gets the amount of the attack.
   * eg damage amount, heal amount, damage buff amount.
   * -1 for no amount.
   * Handleing of the amount should be handled by javascript.
   */
  public double getAmount() {
    return this.data.getAmount();
  }

  /**
   * Gets the duration of the attack.
   * -1 for instant.
   * Handling of the duration should be handled by javascript
   */
  public double getDuration() {
    return this.data.getDuration();
  }

  /**
   * Gets the radius to apply the attack to.
   * 0 for no radius
   */
  public double getRadius() {
    return this.data.getRadius();
  }

  /**
   * The base range of the attack.
   */
  double getRange() {
    return this.data.getRange();
  }

  /**
   * The base time that the animation should take.
   */
  int getAttackSpeed() {
    return this.data.getAttackSpeed();
  }

  /**
   * The portion of the animation that plays before the attack should trigger.
   */
  public double getWindupPortion() {
    return this.data.getWindupPortion();
  }

  /**
   * Gets the sequence that the unit should do when doing the attack.
   */
  public AnimationData getAnimation() {
    return this.data.getAnimation();
  }

  /**
   * Get the type of attack.
   */
  AttackType getType() {
    return this.attackType;
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
   * Gets the Units that are effected by the attack at the target.
   */
  public Collection<Unit> getEffectedUnits(Unit owner, World world, Targetable target) {
    try {
      return (List<Unit>)this.engine.invokeFunction("getEffectedUnits", owner, world, target, this);
    } catch (ScriptException | NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Attack)) {
      return false;
    }
    Attack attack = (Attack) o;
    return Objects.equals(data, attack.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data);
  }
}
