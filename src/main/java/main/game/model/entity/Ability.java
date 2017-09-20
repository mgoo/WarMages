package main.game.model.entity;

import main.images.GameImage;

/**
 * An ability can be applied to (a) Unit(s)
 */
public class Ability {

  public void apply(Unit unit) {
    throw new Error("NYI");
  }

  public GameImage getIconImage() {
    throw new Error("NYI");
  }

  public String getInfo() {
    throw new Error("NYI");
  }
}
