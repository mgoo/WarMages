package main.game.model.entity;

import java.io.Serializable;
import java.util.List;
import main.common.images.GameImage;
import main.common.images.UnitSpriteSheet;
import main.common.images.UnitSpriteSheet.Sequence;

public class UnitImagesComponent implements Serializable {

  private static final long serialVersionUID = 1L;

  public static final int TICKS_PER_FRAME = 5;

  private final Sequence sequence;
  private final UnitSpriteSheet spriteSheet;
  private final List<GameImage> images;

  private Direction direction;

  private int currentTick = 0;


  /**
   * Constructor takes the sequence relevant to the unit's state, the sprite sheet for the unit, and
   * the direction of the unit.
   */
  public UnitImagesComponent(
      Sequence sequence,
      Direction direction,
      Unit unit
  ) {
    this.sequence = sequence;
    this.direction = direction;
    this.spriteSheet = unit.getSpriteSheet();
    this.images = spriteSheet.getImagesForSequence(sequence, direction);
  }

  /**
   * Returns the direction of the images.
   *
   * @return Direction of images.
   */
  public Direction getDirection() {
    return direction;
  }

  /**
   * Returns the SpriteSheet used by this ImagesComponent.
   *
   * @return UnitSpriteSheet currently used.
   */
  public UnitSpriteSheet getSpriteSheet() {
    return spriteSheet;
  }

  /**
   * Returns the sequence used for these images.
   *
   * @return Sequence currently used.
   */
  public Sequence getSequence() {
    return sequence;
  }

  /**
   * The unit should call this when the {@link main.game.model.GameModel} ticks.
   */
  public void tick(Long timeSinceLastTick) {
    currentTick = (currentTick + 1) % maxNumberOfTicks();
  }

  public GameImage getImage() {
    return images.get(getImageIndex());
  }

  /**
   * If on last tick.
   */
  public boolean isReadyToTransition() {
    return currentTick == maxNumberOfTicks() - 1;
  }

  /**
   * True if the current image is the {@link Sequence#attackFrame}. Note this only returns
   * true for one of the {@link UnitImagesComponent#TICKS_PER_FRAME} ticks per frame.
   *
   * @throws IllegalStateException if the current sequence is not meant to be for attacking, as
   *     specified in {@link Sequence#getAttackFrame()};
   */
  public boolean isOnAttackTick() {
    return sequence.getAttackFrame() * TICKS_PER_FRAME == currentTick;
  }

  /**
   * Public only for testing.
   */
  public int _getCurrentTick() {
    return currentTick;
  }

  public boolean isLastTick() {
    return _getCurrentTick() == maxNumberOfTicks() - 1;
  }

  private int maxNumberOfTicks() {
    return TICKS_PER_FRAME * images.size();
  }

  private int getImageIndex() {
    return currentTick / TICKS_PER_FRAME;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }
}
