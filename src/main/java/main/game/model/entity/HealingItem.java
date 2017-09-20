package main.game.model.entity;

import main.images.GameImage;
import main.util.MapPoint;

/**
 * HealingItem extends{@link Item}. Can be used to heal a unit
 */
public class HealingItem extends Item {

  public HealingItem(MapPoint coord, float size) {
    super(coord, size);
  }

  @Override
  public GameImage getImage(){
    throw new Error("NYI");
  }

  @Override
  public void applyTo(Unit unit){
    throw new Error("NYI");
  }
}
