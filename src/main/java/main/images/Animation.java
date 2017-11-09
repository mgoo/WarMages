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
  private final int ticksPerFrame;
  private int currentTick = 0;

  /**
   * Constructor uses list of frames and time
   *
   * @param length how many tick one play of the unitAnimation should take
   */
  public Animation(
      List<GameImage> frames,
      int length
  ) {
    this.frames = frames;
    this.length = length;
    this.ticksPerFrame = Math.max(1, length / frames.size());
  }

  /**
   * The unit should call this when the {@link GameModel} ticks.
   */
  public void tick() {
    currentTick = (currentTick + 1) % this.length;
  }

  public GameImage getImage() {
    return getImages().get(this.currentTick / this.ticksPerFrame);
  }

  public boolean isLastTick() {
    return currentTick == this.length - 1;
  }

  private List<GameImage> getImages() {
    return this.frames;
  }
}
