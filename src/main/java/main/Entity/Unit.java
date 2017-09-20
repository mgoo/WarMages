package main.Entity;

/**
 * Unit extends{@link Entity}. A unit is a part of a team, specified by
 * an enum colour. It has health, and can attack other team units.
 */
public class Unit extends Entity {
  protected int health;
  //todo enum colour field

  public void attack(Unit unit){
    throw new Error("NYI");
  }

  public void takeDamage(int amount){
    throw new Error("NYI");
  }

  public void gainHealth(int amount){
    throw new Error("NYI");
  }

  @Override
  public GameImage.Config getImage(){
    throw new Error("NYI");
  }
}

