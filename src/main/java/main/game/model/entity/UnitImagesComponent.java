package main.game.model.entity;

import java.util.List;
import main.images.GameImage;
import main.images.UnitSpriteSheet;
import main.images.UnitSpriteSheet.Sequence;

public class UnitImagesComponent implements ImagesComponent {

  private static final long serialVersionUID = 1L;

  public static final int TICKS_PER_FRAME = 5;

  private Sequence sequence;
  private int imageIndex;
  private int ticksLeftToChange = TICKS_PER_FRAME;
  private List<GameImage> images;
  private Direction direction;
  private UnitSpriteSheet spriteSheet;


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
    this.imageIndex = 0;
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

  @Override
  public void tick(Long timeSinceLastTick) {
    if (ticksLeftToChange == 0) {
      ticksLeftToChange = TICKS_PER_FRAME;
      imageIndex = (imageIndex + 1) % images.size();
    }

    ticksLeftToChange--;
  }

  @Override
  public GameImage getImage() {
    return images.get(imageIndex);
  }

  @Override
  public boolean isReadyToTransition() {
    return imageIndex == images.size() - 1;
  }

  /**
   * True if the current image is the image that the attack is supposed to be applied on.
   *
   * @throws IllegalStateException if the current sequence is not meant to be for attacking, as
   *     specified in {@link Sequence#getAttackFrame()};
   */
  public boolean isOnAttackFrame() {
    return sequence.getAttackFrame() == imageIndex
        && ticksLeftToChange == 0;
  }
}
