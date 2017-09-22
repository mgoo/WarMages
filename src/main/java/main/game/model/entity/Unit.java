package main.game.model.entity;

import main.images.GameImage;
import main.util.MapPoint;
import main.util.MapSize;

/**
 * Unit extends{@link Entity}. A unit is a part of a team, specified by an enum colour. It has
 * health, and can attack other team units.
 */
public class Unit extends Entity implements Damageable, Attackable {

  protected int health;
  protected Team team;

  public Unit(MapPoint position, MapSize size, Team team) {
    super(position, size);
    this.team=team;
    //todo set starting health
  }

  @Override
  public void setImage(GameImage image) {
    if (image == null) {
      throw new NullPointerException("Parameter image cannot be null");
    }
    this.image=image;
  }

  @Override
  public void attack(Unit unit) {
    throw new Error("NYI");
  }

  @Override
  public void takeDamage(int amount) {
    if(health-amount<0) health=0; //todo shiz when you die
    else health-=amount;
  }

  @Override
  public void gainHealth(int amount) {
    health+=amount;
  }
}

