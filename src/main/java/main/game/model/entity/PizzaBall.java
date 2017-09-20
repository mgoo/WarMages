package main.game.model.entity;

/**
 * PizzaBall extends {@link Projectile}. It deals __ damage to it’s target
 */
public class PizzaBall extends Projectile{
  private final int damageAmount = 5; //todo how to decide damage amount

  public PizzaBall(Unit target){
    super(target);
  }

  @Override
  public void hits(Unit unit){
    throw new Error("NYI");
//    unit.takeDamage(damageAmount);
  }

  @Override
  public GameImage.Config getImage(){
    throw new Error("NYI");
  }
}
