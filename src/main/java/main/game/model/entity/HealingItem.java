package main.game.model.entity;

import main.util.MapPoint;

/**
 * HealingItem extends{@link Item}. Can be used to heal a unit
 */
public class HealingItem extends Item {

  /**
   * Constructor takes the coords of the HealingItem
   * @param coord
   */
  public HealingItem(MapPoint coord) {
    super(coord);
    //todo set image
  }

  @Override
  public void applyTo(Unit unit) {
    throw new Error("NYI");
  }
}
