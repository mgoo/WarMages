package main.util;

import java.io.Serializable;
import main.game.model.GameModel;

/**
 * ﻿A restartable timer.
 * @author chongdyla
 */
public class TickTimer implements Serializable {

  private static final long serialVersionUID = 1L;

  public static int secondsToTicks(double seconds) {
    double ticksPerSecond = 1000 / GameModel.DELAY;
    return (int) (seconds * ticksPerSecond);
  }

  public static TickTimer withPeriodInSeconds(double seconds) {
    return new TickTimer(secondsToTicks(seconds));
  }

  /**
   * Number of ticks left to end cool down.
   */
  private int ticksLeft;

  /**
   * Number of ticks in a cool-down period.
   */
  private final int maxTicks;

  /**
   * Prefer to use the factory method {@link TickTimer#withPeriodInSeconds(double)}.
   */
  public TickTimer(int maxTicks) {
    if (maxTicks <= 0) {
      throw new IllegalArgumentException();
    }
    this.maxTicks = maxTicks;
  }

  public void tick(long timeSinceLastTick) {
    ticksLeft = Math.max(0, ticksLeft - 1);
  }

  /**
   * 0 if just used, 1 if ready to use.
   */
  public double getProgress() {
    if(this.isFinished()) return 1;
    double progress = 1 - (((double) ticksLeft) / maxTicks);
    assert progress >= 0 && progress <= 1;
    return progress;
  }

  public boolean isFinished() {
    return ticksLeft == 0;
  }

  public void restart() {
    ticksLeft = maxTicks;
  }
}
