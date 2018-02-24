package main.game.model.data.dataobject;

import main.game.model.data.DataLoader;

/**
 * Data Object for the unit data.
 * @author Andrew McGhie
 */
public class UnitData {
  private String id;
  private double startingHealth;
  private double movementSpeed;
  private double lineOfSight;
  private double size;

  // Foreign Keys
  private String baseAttack;
  private AttackData baseAttackData;
  private String spriteSheet;
  private SpriteSheetData spriteSheetData;

  public void buildRelationships(DataLoader dataLoader) {
    this.spriteSheetData = dataLoader.getDataForSpriteSheet(this.spriteSheet);
    this.baseAttackData = dataLoader.getDataForAttack(this.baseAttack);
  }

  public String getId() {
    return id;
  }

  public double getStartingHealth() {
    return startingHealth;
  }

  public double getMovementSpeed() {
    return movementSpeed;
  }

  public double getLineOfSight() {
    return lineOfSight;
  }

  public AttackData getBaseAttackData() {
    return baseAttackData;
  }

  public double getSize() {
    return size;
  }

  public SpriteSheetData getSpritesheetData() {
    return spriteSheetData;
  }
}
