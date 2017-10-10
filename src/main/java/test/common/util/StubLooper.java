package test.common.util;

import main.common.util.looper.Looper;

public class StubLooper implements Looper {

  private boolean isPaused;
  private InterruptableRunnable runnable;
  private long repeatDelay;

  @Override
  public boolean isPaused() {
    return isPaused;
  }

  @Override
  public void setPaused(boolean paused) {
    this.isPaused = paused;
  }

  @Override
  public void start(InterruptableRunnable runnable, long repeatDelay) {
    this.runnable = runnable;
    this.repeatDelay = repeatDelay;

    // Don't actually loop
  }

  @Override
  public void finish() {
    // Do nothing
  }

  /**
   * Gets the last known {@link InterruptableRunnable}.
   */
  public InterruptableRunnable getRunnable() {
    if (isPaused) {
      throw new IllegalStateException();
    }
    return runnable;
  }
}
