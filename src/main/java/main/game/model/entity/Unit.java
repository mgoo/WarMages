package main.game.model.entity;

import main.images.GameImage;
import main.util.MapPoint;

/**
 * Unit extends{@link Entity}. A unit is a part of a team, specified by an enum colour. It has
 * health, and can attack other team units.
 */
public class Unit extends Entity {

  protected int health;

  public Unit(MapPoint position, float size) {
    super(position, size);
  }
  //todo enum colour field

  public void attack(Unit unit) {
    throw new Error("NYI");
  }

  public void takeDamage(int amount) {
    throw new Error("NYI");
  }

  public void gainHealth(int amount) {
    throw new Error("NYI");
  }

  @Override
  public GameImage getImage() {
    throw new Error("NYI");
  }
}

