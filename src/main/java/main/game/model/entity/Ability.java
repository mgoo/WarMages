package main.game.model.entity;

/**
 * An ability can be applied to (a) Unit(s)
 */
public class Ability {
  public void apply(Unit unit){
    throw new Error("NYI");
  }

  public GameImage.Config getIconImage(){
    throw new Error("NYI");
  }

  public String getInfo(){ throw new Error("NYI");}
}
