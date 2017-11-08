package main.game.model.entity.unit.state;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import main.common.entity.Direction;
import main.common.entity.Unit;
import main.game.model.entity.unit.UnitAnimation;
import main.game.model.entity.unit.DefaultUnit;
import main.common.World;
import main.common.images.GameImage;
import main.common.images.UnitSpriteSheet.Sequence;

/**
 * Holds information about what a unit is doing.
 *
 * @author paladogabr
 */
public abstract class UnitState implements Serializable {

  private static final long serialVersionUID = 1L;

  protected final Unit unit;
  protected final UnitAnimation unitAnimation;

  protected UnitState requestedNextState;

  public UnitState(UnitAnimation unitAnimation, Unit unit) {
    this.unit = unit;
    this.unitAnimation = unitAnimation;
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
  public GameImage getImage() {
    return unitAnimation.getImage();
  }

  /**
   * Sets the "next" state to be the requested state, if there isn't already a requested state.
   *
   * @param nextState the requested state.
   */
  public void requestState(UnitState nextState) {
    this.requestedNextState = requireNonNull(nextState);
  }

  /**
   * Gets the direction the unit should face, defaults to face away from the last position.
   */
  public Direction getCurrentDirection() {
    return Direction.between(unit.getPreviousTopLeft(), unit.getTopLeft());
  }

  /**
   * Gets the next state or this if no change was requested.
   */
  public abstract UnitState updateState();

  public void onTakeDamage(double amount, World world, Unit attacker) {
    // do nothing
  }

}
