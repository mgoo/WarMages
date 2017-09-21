package main.util;

import java.util.ArrayList;
import java.util.Collection;

public class Event<ParamT> {

  private Collection<Listener<ParamT>> listeners = new ArrayList<>();

  public Runnable registerListener(Listener<ParamT> listener) {
    listeners.add(listener);
    return () -> listeners.remove(listener);
  }

  public void broadcast(ParamT parameter) {
    listeners.forEach(listener -> listener.onNotify(parameter));
  }

  @FunctionalInterface
  public interface Listener<ParamT> {

    void onNotify(ParamT parameter);
  }
}
