package main.game.model.data.dataobject;

import main.game.model.data.DataLoader;

/**
 * Data object for Abilities.
 * @author Andrew McGhie
 */
public class AbilityData {
  private String id;
  private double cooldown;
  private String description;
  private String targets;

  // Foreign  keys.
  private String attack;
  private AttackData attackData;
  private String icon;
  private ImageData iconImage;

  public void buildRelationships(DataLoader dataLoader) {
    this.attackData = dataLoader.getDataForAttack(this.attack);
    this.iconImage = dataLoader.getDataForImage(this.icon);
  }

  public String getId() {
    return id;
  }

  public double getCooldown() {
    return cooldown;
  }

  public String getDescription() {
    return description;
  }

  public String getTargets() {
    return targets;
  }

  public ImageData getIcon() {
    return iconImage;
  }

  public AttackData getAttackData() {
    return attackData;
  }
}
