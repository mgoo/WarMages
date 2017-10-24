package main.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Similar to the Observable class in Java.
 *
 * @param <ParamT> the arg
 * @author chongdyla
 */
public class Event<ParamT> {

  private Collection<Listener<ParamT>> listeners = new CopyOnWriteArrayList<>();

  public Runnable registerListener(Listener<ParamT> listener) {
    listeners.add(listener);
    return () -> listeners.remove(listener);
  }

  /**
   * Adds a listener that is only executed on the first event.
   */
  public Runnable doOnce(Listener<ParamT> listener) {
    Listener removingListener = new Listener<ParamT>() {
      @Override
      public void onNotify(ParamT parameter) {
        Event.this.listeners.remove(this);
        listener.onNotify(parameter);
      }
    };
    return this.registerListener(removingListener);
  }

  public void broadcast(ParamT parameter) {
    listeners.forEach(listener -> listener.onNotify(parameter));
  }

  @FunctionalInterface
  public interface Listener<ParamT> {

    void onNotify(ParamT parameter);
  }
}
