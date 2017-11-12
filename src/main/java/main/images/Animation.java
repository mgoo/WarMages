package main.images;

import java.util.List;
import main.common.GameModel;
import main.common.images.GameImage;

/**
 * Handles what image should be shown as time progresses.
 * @author Andrew McGhie
 */
public class Animation {

  private final List<GameImage> frames;
  private final int length;
  private final double ticksPerFrame;
  private int currentTick = 0;
  private boolean isFinished = false;

  /**
   * Constructor uses list of frames and time.
   *
   * @param length how many tick one play of the unitAnimation should take
   */
  public Animation(
      List<GameImage> frames,
      int length
  ) {
    this.frames = frames;
    this.length = length;
    this.ticksPerFrame = (double)length / (double)frames.size();
  }

  /**
   * The unit should call this when the {@link GameModel} ticks.
   */
  public void tick() {
    if (this.isFinished) {
      return;
    }
    this.currentTick = this.currentTick + 1;
    if (this.currentTick >= this.length) {
      this.isFinished = true;
    }
  }

  /**
   * Gets the current GameImage.
   * If the animation is finished it will just return the last one.
   */
  public GameImage getImage() {
    if (this.isFinished) {
      return getImages().get(getImages().size() - 1);
    }
    return getImages().get((int)(this.currentTick / this.ticksPerFrame));
  }

  public boolean isFinished() {
    return this.isFinished;
  }

  private List<GameImage> getImages() {
    return this.frames;
  }
}
