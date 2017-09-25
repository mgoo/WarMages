package main.game.model.entity;

/**
 * This enum represents the state of a Unit. It can be either attacking, hit, default, or walking.
 */
public enum UnitState {

  ATTACKING(),

  BEEN_HIT(),

  DEFAULT_STATE(),

  WALKING();

  private Direction direction;

  public void setDirection(Direction direction){
    if (direction == null) {
      throw new IllegalArgumentException("Direction cannot be null!");
    }
    this.direction=direction;
  }

  public Direction getDirection(){
    if (direction == null) {
      throw new NullPointerException("Direction has not been set!");
    }
    return direction;
  }
}
