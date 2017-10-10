package main.common.util.looper;

/**
 * Something that loops forever (or until a {@link InterruptedException} is thrown), or is
 * paused. Initially, the {@link Looper} should not be paused.
 */
public interface Looper {

  boolean isPaused();

  void setPaused(boolean paused);

  /**
   * Repeatedly runs the {@link InterruptableRunnable}. Will continue to repeat after an
   * unpause.
   */
  void start(InterruptableRunnable runnable, long repeatDelay);

  void finish();

  @FunctionalInterface
  interface InterruptableRunnable {
    void run() throws InterruptedException;
  }
}
