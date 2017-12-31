package main.util;

import static java.lang.Thread.sleep;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Loops some runnable on this new {@link Thread}.
 * @author chongdyla
 */
public class Looper {

  private final AtomicBoolean isPaused = new AtomicBoolean(false);
  private final AtomicBoolean isRunning = new AtomicBoolean(false);
  private final Object pauseLock = new Object();

  /**
   * Start looping on a new thread.
   */
  public synchronized void start(Runnable repeatable) {
    if (isRunning.get()) {
      throw new IllegalStateException();
    }
    isRunning.set(true);

    Thread thread = new Thread(() -> {
      try {
        while (isRunning.get()) {
          repeatable.run();

          if (isPaused.get()) {
            synchronized (pauseLock) {
              pauseLock.wait();
            }
          }
        }
      } catch (InterruptedException e) {
        throw new Error(e);
      }
    });
    thread.start();
  }

  /**
   * Start looping on a new thread with a schedule.
   * @param repeatable method to run
   * @param delay time between ticks
   */
  public void startWithSchedule(Runnable repeatable, long delay) {
    start(() -> {
      repeatable.run();
      try {
        sleep(delay);
      } catch (InterruptedException e) {
        throw new IllegalStateException("task interrupted", e);
      }
    });
  }

  /**
   * Stops the running thread.
   */
  public synchronized void stop() {
    if (!isRunning.get()) {
      throw new IllegalStateException("Thread is already stopped!");
    }
    isRunning.set(false);
    synchronized (pauseLock) {
      pauseLock.notify();
    }
  }

  /**
   * Pause/resume.
   */
  public synchronized void setPaused(boolean paused) {
    if (!isRunning.get()) {
      throw new IllegalStateException();
    }

    if (paused == isPaused.get()) {
      return;
    }

    isPaused.set(paused);

    if (!paused) {
      synchronized (pauseLock) {
        pauseLock.notify();
      }
    }
  }


}
