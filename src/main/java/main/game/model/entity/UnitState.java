package main.game.model.entity;

import java.util.List;
import main.images.GameImage;
import main.images.UnitSpriteSheet;
import main.images.UnitSpriteSheet.Sequence;

/**
 * This enum represents the state of a Unit. It can be either attacking, hit, default, or walking.
 */
public enum UnitState {

  ATTACKING() {
    @Override
    protected List<GameImage> getImagesFor(UnitType type, UnitSpriteSheet sheet) {
      return sheet.getImagesForSequence(type.getAttackSequence(), direction);
    }

    @Override
    public void changeImage(Long timeSinceLastTick) {
      //todo cooldown, projectile if applicable
      imagesIdx = (imagesIdx + 1 >= images.size()) ? 0 : imagesIdx + 1;
    }
  },

  BEEN_HIT() {
    @Override
    protected List<GameImage> getImagesFor(UnitType type, UnitSpriteSheet sheet) {
      return sheet.getImagesForSequence(Sequence.HURT, direction);
    }

    @Override
    public void changeImage(Long timeSinceLastTick) {
      //todo recovery?
      imagesIdx = (imagesIdx + 1 >= images.size()) ? 0 : imagesIdx + 1;
    }
  },

  DEFAULT_STATE() {
    @Override
    protected List<GameImage> getImagesFor(UnitType type, UnitSpriteSheet sheet) {
      return sheet.getImagesForSequence(Sequence.IDLE, direction);
    }

    @Override
    public void changeImage(Long timeSinceLastTick) {
      imagesIdx = (imagesIdx + 1 >= images.size()) ? 0 : imagesIdx + 1;
    }
  },

  WALKING() {
    @Override
    protected List<GameImage> getImagesFor(UnitType type, UnitSpriteSheet sheet) {
      return sheet.getImagesForSequence(Sequence.WALK, direction);
    }

    @Override
    public void changeImage(Long timeSinceLastTick) {
      imagesIdx = (imagesIdx + 1 >= images.size()) ? 0 : imagesIdx + 1;
    }
  };

  protected Direction direction;
  protected List<GameImage> images;
  protected float imagesIdx;

  /**
   * Constructor takes no arguments. It sets a random initial direction for the state.
   */
  UnitState() {
    direction = (Math.random() < 0.5) ? ((Math.random() < 0.5) ? Direction.LEFT : Direction.RIGHT) :
        ((Math.random() < 0.5) ? Direction.UP : Direction.DOWN);
    imagesIdx = 0;
  }

  /**
   * Sets the direction of the unit to the given direction.
   *
   * @param direction to be set to.
   */
  public void setDirection(Direction direction) {
    if (direction == null) {
      throw new IllegalArgumentException("Direction cannot be null!");
    }
    this.direction = direction;
  }

  /**
   * Returns the current direction of the unit state.
   *
   * @return current unit direction.
   */
  public Direction getDirection() {
    return direction;
  }

  /**
   * Returns the current image of the state.
   *
   * @return GameImage image of the state at this current point.
   */
  public GameImage getImage() {
    return images.get(imagesIdx);
  }

  protected abstract List<GameImage> getImagesFor(UnitType type, UnitSpriteSheet sheet);

  //todo take into account attack speed

  /**
   * Changes the image of the UnitState depending on the amount of time that has passed.
   *
   * @param timeSinceLastTick time that has passed since last tick.
   */
  protected abstract void changeImage(Long timeSinceLastTick);

  /**
   * Sets the "next" state to be the requested state, if there isn't already a requested state.
   */
  protected abstract void requestState(UnitState nextState);

  /**
   * Returns the UnitState which may be different from the current state, depending on whether a
   * state has been requested or not.
   *
   * @return UnitState to be changed to
   */
  protected abstract UnitState updateState();

  /**
   * Updates the image of the UnitState.
   *
   * @param timeSinceLastTick time past since last update.
   */
  public void tick(Long timeSinceLastTick) {
    changeImage(timeSinceLastTick);
  }
}
