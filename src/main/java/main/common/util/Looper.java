package main.common.util;

import java.util.concurrent.atomic.AtomicBoolean;

public class Looper {

  private final AtomicBoolean isPaused = new AtomicBoolean(false);
  private final AtomicBoolean hasStarted = new AtomicBoolean(false);
  private final Object pauseLock = new Object();

  public synchronized void start(Runnable repeatable) {
    if (hasStarted.get()) {
      throw new IllegalStateException();
    }
    hasStarted.set(true);

    Thread thread = new Thread(() -> {
      try {
        while (true) {
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

  public synchronized void setPaused(boolean paused) {
    if (!hasStarted.get()) {
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
