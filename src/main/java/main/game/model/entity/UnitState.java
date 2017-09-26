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
      switch(type){
        case ARCHER:
          return sheet.getImagesForSequence(Sequence.SHOOT, direction);
        case SPEARMAN:
          return sheet.getImagesForSequence(Sequence.THRUST, direction);
        case SWORDSMAN:
          return sheet.getImagesForSequence(Sequence.SLASH, direction);
        case MAGICIAN:
          return sheet.getImagesForSequence(Sequence.SPELL_CAST, direction);
        default:
          return sheet.getImagesForSequence(Sequence.IDLE, direction);
      }
    }
  },

  BEEN_HIT() {
    @Override
    protected List<GameImage> getImagesFor(UnitType type, UnitSpriteSheet sheet) {
      return sheet.getImagesForSequence(Sequence.HURT, direction);
    }
  },

  DEFAULT_STATE() {
    @Override
    protected List<GameImage> getImagesFor(UnitType type, UnitSpriteSheet sheet) {
      return sheet.getImagesForSequence(Sequence.IDLE, direction);
    }
  },

  WALKING() {
    @Override
    protected List<GameImage> getImagesFor(UnitType type, UnitSpriteSheet sheet) {
      return sheet.getImagesForSequence(Sequence.WALK, direction);
    }
  };

  protected Direction direction;

  /**
   * Sets the direction of the unit to the given direction.
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
   * @return current unit direction.
   */
  public Direction getDirection() {
    if (direction == null) {
      throw new NullPointerException("Direction has not been set!");
    }
    return direction;
  }

  protected abstract List<GameImage> getImagesFor(UnitType type, UnitSpriteSheet sheet);
}
