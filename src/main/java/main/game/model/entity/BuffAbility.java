package main.game.model.entity;

/**
 * The BuffAbility is a type of Ability that allows the unit using it to deal more damage.
 */
public class BuffAbility extends Ability {

  /**
   * Constructor takes no parameters.
   */
  public BuffAbility(){
    super("This ability buffs the unit using it, allowing the unit to cause more damage.", null);
    //todo pass description and buff image
  }

  @Override
  public void apply(Unit unit){
    throw new Error("NYI");
  }
}
