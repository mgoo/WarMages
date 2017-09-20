package main.Entity;

/**
 * HealingItem extends{@link Item}. Can be used to heal a unit
 */
public class HealingItem extends Item {

  @Override
  public GameImage.Config getImage(){
    throw new Error("NYI");
  }

  @Override
  public void applyTo(Unit unit){
    throw new Error("NYI");
  }
}
