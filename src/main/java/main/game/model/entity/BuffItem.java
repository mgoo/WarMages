package main.game.model.entity;

import main.images.GameImage;
import main.util.MapPoint;

/**
 * BuffItem extends{@link Item}. Gives the unit a buff ability
 */
public class BuffItem extends Item {

  private Ability buff;

  public BuffItem(MapPoint coord, float size) {
    super(coord, size);
  }

  @Override
  public void applyTo(Unit unit) {
    throw new Error("NYI");
  }

  @Override
  public GameImage getImage() {
    throw new Error("NYI");
  }
}
