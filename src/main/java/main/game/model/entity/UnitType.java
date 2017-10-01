package main.game.model.entity;

import main.images.UnitSpriteSheet.Sequence;

/**
 * This enum represents the type of a Unit. A unit can be an archer, a swordsman, a spearman, or a
 * magician.
 */
public enum UnitType {

  //todo confirm attack and moving speeds
  ARCHER(5, 200, 5, 5, Sequence.SHOOT),

  SWORDSMAN(10, 250, 6, 5, Sequence.SLASH),

  SPEARMAN(7, 150, 5, 5, Sequence.THRUST),

  MAGICIAN(15, 300, 8, 7, Sequence.SPELL_CAST);

  protected int baselineDamage;
  protected int startingHealth;
  protected int attackSpeed;
  protected int movingSpeed;
  protected Sequence attackSequence;

  public int getBaselineDamage() {
    return baselineDamage;
  }

  public int getStartingHealth() {
    return startingHealth;
  }

  public int getAttackSpeed() {
    return attackSpeed;
  }

  public int getMovingSpeed() {
    return movingSpeed;
  }

  public Sequence getAttackSequence() {
    return attackSequence;
  }

  UnitType(
      int baselineDamage, int startingHealth, int attackSpeed, int movingSpeed,
      Sequence attackSequence
  ) {
    this.baselineDamage = baselineDamage;
    this.startingHealth = startingHealth;
    this.attackSpeed = attackSpeed;
    this.movingSpeed = movingSpeed;
    this.attackSequence = attackSequence;
  }
}


