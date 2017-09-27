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

  ARCHER(5, 200, 5),

  SWORDSMAN(10, 250, 6),

  SPEARMAN(7, 150, 7),

  MAGICIAN(15, 300, 8);

  protected int baselineDamage;
  protected int startingHealth;
  protected int speed;

  public int getBaselineDamage() {
    return baselineDamage;
  }

  public int getStartingHealth() {
    return startingHealth;
  }

  public int getSpeed() {
    return speed;
  }

  UnitType(int baselineDamage, int startingHealth, int speed) {
    this.baselineDamage = baselineDamage;
    this.startingHealth = startingHealth;
    this.speed = speed;
  }
}


