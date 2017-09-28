package main.game.model.entity;

import java.util.List;
import main.images.GameImage;
import main.images.UnitSpriteSheet;
import main.images.UnitSpriteSheet.Sequence;

/**
 * This enum represents the type of a Unit. A unit can be an archer, a swordsman, a spearman, or a
 * magician.
 */
public enum UnitType {

  ARCHER(5, 200, 5, Sequence.SHOOT),

  SWORDSMAN(10, 250, 6, Sequence.SLASH),

  SPEARMAN(7, 150, 7, Sequence.THRUST),

  MAGICIAN(15, 300, 8, Sequence.SPELL_CAST);

  protected int baselineDamage;
  protected int startingHealth;
  protected int speed;
  protected Sequence attackSequence;

  public int getBaselineDamage() {
    return baselineDamage;
  }

  public int getStartingHealth() {
    return startingHealth;
  }

  public int getSpeed() {
    return speed;
  }

  public Sequence getAttackSequence(){
    return attackSequence;
  }

  UnitType(int baselineDamage, int startingHealth, int speed, Sequence attackSequence) {
    this.baselineDamage = baselineDamage;
    this.startingHealth = startingHealth;
    this.speed = speed;
    this.attackSequence=attackSequence;
  }
}


