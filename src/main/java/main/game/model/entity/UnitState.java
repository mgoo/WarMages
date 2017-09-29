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
    public void changeImage() {
      //todo cooldown, projectile if applicable
    }
  },

  BEEN_HIT() {
    @Override
    protected List<GameImage> getImagesFor(UnitType type, UnitSpriteSheet sheet) {
      return sheet.getImagesForSequence(Sequence.HURT, direction);
    }

    @Override
    public void changeImage() {
      //todo recovery?
    }
  },

  DEFAULT_STATE() {
    @Override
    protected List<GameImage> getImagesFor(UnitType type, UnitSpriteSheet sheet) {
      return sheet.getImagesForSequence(Sequence.IDLE, direction);
    }

    @Override
    public void changeImage() {
      imagesIdx = (imagesIdx+1==images.size()) ? 0 : imagesIdx+1;
    }
  },

  WALKING() {
    @Override
    protected List<GameImage> getImagesFor(UnitType type, UnitSpriteSheet sheet) {
      return sheet.getImagesForSequence(Sequence.WALK, direction);
    }

    @Override
    public void changeImage() {
      imagesIdx = (imagesIdx+1==images.size()) ? 0 : imagesIdx+1;
    }
  };

  protected Direction direction;
  protected List<GameImage> images;
  protected int imagesIdx;

  /**
   * Constructor takes no arguments. It sets a random initial direction for the state.
   */
  UnitState(){
    direction = (Math.random()<0.5) ? ((Math.random()<0.5) ? Direction.LEFT : Direction.RIGHT) :
        ((Math.random()<0.5) ? Direction.UP : Direction.DOWN);
    imagesIdx=0;
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
  public GameImage getImage(){
    return images.get(imagesIdx);
  }

  protected abstract List<GameImage> getImagesFor(UnitType type, UnitSpriteSheet sheet);

  protected abstract void changeImage();

  /**
   * Updates the image of the UnitState.
   *
   * @param timeSinceLastTick time past since last update.
   */
  public void tick(Long timeSinceLastTick){
    changeImage();
  }
}
