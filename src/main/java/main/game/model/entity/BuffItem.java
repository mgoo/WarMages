package main.game.model.entity;

import main.util.MapPoint;

/**
 * BuffItem extends{@link Item}. Gives the unit a buff ability (increases damage made to other units).
 */
public class BuffItem extends Item {

  private Ability buff;

  /**
   * Constructor takes the coords to create the item at.
   * @param coord
   */
  public BuffItem(MapPoint coord) {
    super(coord);
    buff = new BuffAbility(null);
    //todo pass image
  }

  @Override
  public void applyTo(Unit unit) {
    assert unit!=null;
    buff.apply(unit);
  }

}
