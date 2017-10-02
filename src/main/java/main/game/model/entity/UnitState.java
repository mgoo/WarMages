package main.game.model.entity;

import java.io.Serializable;
import main.images.GameImage;
import main.images.UnitSpriteSheet;
import main.images.UnitSpriteSheet.Sequence;

/**
 * An interface for the states of a unit.
 */
public abstract class UnitState implements Serializable {

  private static final long serialVersionUID = 1L;

  protected UnitImagesComponent imagesComponent;
  protected UnitState nextState;

  public UnitState(Sequence sequence, Direction direction, UnitSpriteSheet sheet) {
    imagesComponent = new UnitImagesComponent(sequence, sheet, direction);
  }

  /**
   * Updates the state.
   *
   * @param timeSinceLastTick time passed since last tick call.
   */
  public void tick(Long timeSinceLastTick) {
    imagesComponent.changeImage(timeSinceLastTick);
  }

  /**
   * Returns the current image of the AbstractUnitState.
   *
   * @return GameImage image of current state.
   */
  public GameImage getImage() {
    return imagesComponent.getImage();
  }

  /**
   * Sets the "next" state to be the requested state, if there isn't already a requested state.
   *
   * @param nextState the requested state.
   */
  public void requestState(UnitState nextState) {
    if (this.getClass().equals(nextState.getClass())) {
      return;
    }
    this.nextState = nextState;
  }

  /**
   * Updates the imagesComponent if the given direction differs to the current direction.
   *
   * @param newDirection direction to be changed to.
   */
  public void setDirection(Direction newDirection) {
    if (imagesComponent.getDirection() != newDirection) {
      imagesComponent = new UnitImagesComponent(
          imagesComponent.getSequence(), imagesComponent.getSpriteSheet(), newDirection);
    }
  }

  /**
   * Returns the direction of this state.
   *
   * @return Direction of the current state.
   */
  public Direction getDirection() {
    return imagesComponent.getDirection();
  }

  abstract UnitState updateState();
}
