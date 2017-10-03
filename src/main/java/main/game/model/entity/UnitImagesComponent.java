package main.game.model.entity;

import java.util.List;
import main.images.GameImage;
import main.images.UnitSpriteSheet;
import main.images.UnitSpriteSheet.Sequence;

public class UnitImagesComponent implements ImagesComponent {

  private static final long serialVersionUID = 1L;

  public static final int TICKS_TO_CHANGE = 5;

  private Sequence sequence;
  private int imagesIdx;
  private int tickCount = 0;
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
    this.spriteSheet = unit.spriteSheet;
    imagesIdx = 0;
    images = spriteSheet.getImagesForSequence(sequence, direction);
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
  public void changeImage(Long timeSinceLastTick) {
    tickCount++;
    if (tickCount % TICKS_TO_CHANGE == 0) { //change image every certain number of ticks.
      imagesIdx = (imagesIdx + 1 >= images.size()) ? 0 : imagesIdx + 1;
    }
  }

  @Override
  public GameImage getImage() {
    return images.get(imagesIdx);
  }

  @Override
  public boolean readyToTransition() {
    return imagesIdx == images.size() - 1;
  }

  public boolean isOnAttackFrame() {
    return sequence.getAttackFrame() == imagesIdx;
  }
}
