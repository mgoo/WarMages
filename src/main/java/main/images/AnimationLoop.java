package main.images;

import main.game.model.data.dataObject.SpriteSheetData;

public class AnimationLoop extends Animation {

  /**
   * Constructor uses list of frames and time.
   *
   * @param length how many ticks one play of the Animation should take
   */
  public AnimationLoop(
      SpriteSheetData spriteSheet, String animation,
      int length
  ) {
    super(spriteSheet, animation, length);
  }

  @Override
  public void tick() {
    this.currentTick = this.currentTick + 1;
    if (this.currentTick >= this.animationData.getFrames()) {
      this.currentTick = 0;
    }
  }
}
