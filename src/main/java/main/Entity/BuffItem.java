package main.Entity;

/**
 * BuffItem extends{@link Item}. Gives the unit a buff ability
 */
public class BuffItem extends Item {
  private Ability buff;

  @Override
  public void applyTo(Unit unit){
    throw new Error("NYI");
  }

  @Override
  public GameImage.Config getImage(){
    throw new Error("NYI");
  }
}
