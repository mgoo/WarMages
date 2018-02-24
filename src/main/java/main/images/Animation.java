package main.images;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.List;
import main.game.model.GameModel;
import main.game.model.data.dataObject.AnimationData;
import main.game.model.data.dataObject.ImageData;
import main.game.model.data.dataObject.SpriteSheetData;

/**
 * Handles what image should be shown as time progresses.
 * @author Andrew McGhie
 */
public class Animation implements Serializable {

  private static final long serialVersionUID = 1L;

  protected final AnimationData animationData;
  private final SpriteSheetData spriteSheet;
  protected final int length;
  protected final double ticksPerFrame;
  protected int currentTick = 0;
  private boolean isFinished = false;

  /**
   * Constructor uses list of frames and time.
   *
   * @param length how many ticks one play of the Animation should take
   */
  public Animation(
      SpriteSheetData spriteSheet,
      String animation,
      int length
  ) {
    this.spriteSheet = spriteSheet;
    this.animationData = this.spriteSheet.getAnimation(animation);
    this.length = length;
    this.ticksPerFrame = (double)length / (double)this.animationData.getFrames();
  }

  /**
   * This will be called every tick {@link GameModel} ticks.
   */
  public void tick() {
    if (this.isFinished) {
      return;
    }
    this.currentTick = this.currentTick + 1;
    if (this.currentTick >= this.length - 1) {
      this.isFinished = true;
    }
  }

  /**
   * Gets the current GameImage.
   * If the animation is finished it will just return the last one.
   * Will pick the closest direction to the angle.
   * @param angle the angle in radians that you want the frame for.
   */
  public ImageData getImage(double angle) {
    int direction = this.angleToDirection(angle);

    if (direction >= this.animationData.getDirections()) {
      direction = 0;
    }
    if (this.isFinished) {
      this.spriteSheet.getImage(this.animationData.getId(), this.animationData.getFrames() - 1, direction);
    }

    int currentFrame = (int)(this.currentTick / this.ticksPerFrame);
    if (currentFrame >= this.animationData.getFrames()) {
      currentFrame = this.animationData.getFrames() - 1;
    }
    return this.spriteSheet.getImage(this.animationData.getId(), currentFrame, direction);
  }

  /**
   * Converts radians into a direction index.
   */
  private int angleToDirection(double angle) {
    angle += Math.PI / 2;
    angle = angle % 2 * Math.PI;

    double angleStep = 2 * Math.PI / this.animationData.getDirections();
    int direction = (int)Math.round(angle / angleStep);
    if (direction == this.animationData.getDirections()) {
      direction = 0;
    }
    return direction;
  }

  public boolean isFinished() {
    return this.isFinished;
  }
}
