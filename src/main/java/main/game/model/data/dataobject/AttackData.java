package main.game.model.data.dataobject;

import main.game.model.data.DataLoader;

/**
 * Data object for attacks.
 * @author Andrew McGhie
 */
public class AttackData {

  private String id;
  private String scriptLocation;
  private double range;
  private int attackSpeed;
  private double windupPortion;
  private String type;
  private double amount = 0;
  private double duration = 0;
  private double radius = 0;

  // Forign Keys.
  private String animation;
  private AnimationData animationData;

  public void buildRelationships(DataLoader dataLoader) {
    this.animationData = dataLoader.getDataForAnimation(animation);
  }

  public String getId() {
    return id;
  }

  public String getScriptLocation() {
    return scriptLocation;
  }

  public double getRange() {
    return range;
  }

  public int getAttackSpeed() {
    return attackSpeed;
  }

  public double getWindupPortion() {
    return windupPortion;
  }

  public AnimationData getAnimation() {
    return animationData;
  }

  public String getType() {
    return type;
  }

  public double getAmount() {
    return amount;
  }

  public double getDuration() {
    return duration;
  }

  public double getRadius() {
    return radius;
  }
}
