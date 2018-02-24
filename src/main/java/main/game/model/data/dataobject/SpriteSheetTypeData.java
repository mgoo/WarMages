package main.game.model.data.dataobject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import main.game.model.data.DataLoader;

/**
 * Data object for a type of sprite sheet.
 * @author Andrew McGhie
 */
public class SpriteSheetTypeData {
  private String id;
  /** Map of animation id to ystart. */
  private Map<String, Integer> animations;
  /** Map of Data to ystart. */
  private Map<String, AnimationData> animationData = new HashMap<>();

  /**
   * Builds relationships between the images and the animation and the spritesheet type.
   */
  public void buildRelationships(DataLoader dataLoader) {
    animations.keySet().forEach(animationId -> {
      AnimationData animationData = dataLoader.getDataForAnimation(animationId);
      this.animationData.put(animationId, animationData);
    });
  }

  public String getId() {
    return this.id;
  }

  public Collection<AnimationData> getAnimations() {
    return this.animationData.values();
  }

  public AnimationData getAnimationData(String id) {
    return this.animationData.get(id);
  }

  /**
   * Gets the y value that the animation should start at.
   */
  public int getAnimationStart(String id) {
    return this.animations.get(id);
  }

}
