package main.common.util.looper;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class TimerBasedLooper implements Looper {

  private final AtomicBoolean isPaused = new AtomicBoolean(false);
  private final Timer timer;

  private volatile boolean hasStarted = false;
  private volatile boolean hasFinished = false;
  private volatile TimerTask timerTask;

  private volatile InterruptableRunnable runnable;
  private volatile long repeatDelay;

  public TimerBasedLooper() {
    this.timer = new Timer();
  }

  @Override
  public synchronized boolean isPaused() {
    return isPaused.get();
  }

  @Override
  public synchronized void setPaused(boolean paused) {
    if (!hasStarted || hasFinished) {
      throw new IllegalStateException();
    }

    if (isPaused.get() == paused) {
      return;
    }

    isPaused.set(paused);

    if (isPaused.get()) {
      startLoop(runnable, repeatDelay);
    } else {
      timerTask.cancel();
    }
  }

  @Override
  public synchronized void start(InterruptableRunnable runnable, long repeatDelay) {
    if (hasStarted || hasFinished) {
      throw new IllegalStateException();
    }

    hasStarted = true;

    this.runnable = runnable;
    this.repeatDelay = repeatDelay;

    startLoop(runnable, repeatDelay);
  }

  @Override
  public synchronized void finish() {
    if (!hasStarted) {
      throw new IllegalStateException();
    }

    timer.cancel();
  }

  private synchronized void startLoop(InterruptableRunnable runnable, long repeatDelay) {
    if (!hasStarted || isPaused.get() || hasFinished) {
      throw new IllegalStateException();
    }

    timerTask = new TimerTask() {
      @Override
      public void run() {
        try {
          runnable.run();
        } catch (InterruptedException e) {
          throw new Error(e);
        }
      }
    };
    timer.scheduleAtFixedRate(timerTask, 1, repeatDelay);
  }
}
