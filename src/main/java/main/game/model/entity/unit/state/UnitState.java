package main.game.model.entity.unit.state;

import java.io.Serializable;
import main.game.model.data.dataObject.ImageData;
import main.game.model.entity.Unit;
import main.game.model.world.World;
import main.images.Animation;

/**
 * Holds information about what a unit is doing.
 *
 * @author paladogabr
 */
public abstract class UnitState implements Serializable {

  private static final long serialVersionUID = 1L;

  protected final Unit unit;
  protected final Animation unitAnimation;

  public UnitState(Animation animation, Unit unit) {
    this.unit = unit;
    this.unitAnimation = animation;
  }

  /**
   * Updates the state.
   *
   * @param timeSinceLastTick time passed since last tick call.
   */
  public void tick(Long timeSinceLastTick, World world) {
    unitAnimation.tick();
  }

  /**
   * Returns the current image of the AbstractUnitState.
   *
   * @return GameImage image of current state.
   */
  public ImageData getImage() {
    return unitAnimation.getImage(this.unit.getCurrentAngle());
  }

  /**
   * Gets the direction the unit should face, defaults to face away from the last position.
   */
  public abstract double getCurrentAngle();

  public void onTakeDamage(double amount, World world, Unit attacker) {
    // do nothing
  }

}
