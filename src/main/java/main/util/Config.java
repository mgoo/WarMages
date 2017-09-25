package main.util;

/**
 * Holds the config and context of the game.
 * @author Andrew McGhie
 */
public class Config {

  private int gameModelDelay = 50;

  private double gameViewScrollSpeed = 1;

  private int entityViewTilePixelsX = 50;
  private int entityViewTilePixelsY = 50;

  private int contextScreenWidth = -1;
  private int contextScreenHeight = -1;

  public int getGameModelDelay() {
    return gameModelDelay;
  }

  public double getGameViewScrollSpeed() {
    return gameViewScrollSpeed;
  }

  public int getEntityViewTilePixelsX() {
    return entityViewTilePixelsX;
  }

  public int getEntityViewTilePixelsY() {
    return entityViewTilePixelsY;
  }

  /**
   * Gets the width of the screen.
   * @return screenWidth
   * @throws Error if screenWidth not set
   */
  public int getContextScreenWidth() {
    if (contextScreenWidth == -1) {
      throw new Error("You have not set the screen height yet");
    }
    return contextScreenWidth;
  }

  /**
   * Gets the height of the screen.
   * @return screenHeight
   * @throws Error if screenHeight not set
   */
  public int getContextScreenHeight() {
    if (contextScreenHeight == -1) {
      throw new Error("You have not set the screen height yet");
    }
    return contextScreenHeight;
  }

  public void setScreenDim(int width, int height) {
    this.contextScreenWidth = width;
    this.contextScreenHeight = height;
  }
}
