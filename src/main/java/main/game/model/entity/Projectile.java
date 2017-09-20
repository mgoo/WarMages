package main.game.model.entity;

/**
 * Projectile extends {@link Entity}. A projectile is fired by a unit at a
 * target (another unit) and affects it in some way upon impact.
 */
public abstract class Projectile extends Entity {
  private final Unit target;

  public Projectile(Unit target){
    throw new Error("NYI");
  }

  @Override
  public GameImage.Config getImage(){
    throw new Error("NYI");
  }

  public abstract void hits(Unit unit);
}

