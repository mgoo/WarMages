package main.util;

public class Events {

  private Events() {

  }

  public static class MainGameTick extends Event<Long> {

  }

  public static class GameWon extends Event<Void> {

  }

  public static class GameLost extends Event<Void> {

  }
}
