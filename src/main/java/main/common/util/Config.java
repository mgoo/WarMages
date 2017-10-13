package main.common.util;

import java.awt.Color;

/**
 * Holds the config and context of the game.
 * @author Andrew McGhie
 */
public class Config {
  private static final int CONTEXT_SCREEN_SIZE_NOT_SET = -1;

  private boolean isDebugMode = true;

  private int gameModelDelay = 50;

  private double gameViewScrollSpeed = 50;

  private int entityViewTilePixelsX = 75;
  private int entityViewTilePixelsY = 75;

  private Color baseFogOfWarColor = new Color(54, 59, 88);

  private int contextScreenWidth = CONTEXT_SCREEN_SIZE_NOT_SET;
  private int contextScreenHeight = CONTEXT_SCREEN_SIZE_NOT_SET;

  public void enableDebugMode(){
    this.isDebugMode = true;
  }

  /**
   * Runs the game in debug mode
   * @return
   */
  public boolean isDebugMode() {
    return isDebugMode;
  }

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

  public void setEntityViewTilePixelsX(int entityViewTilePixelsX) {
    this.entityViewTilePixelsX = entityViewTilePixelsX;
  }

  public void setEntityViewTilePixelsY(int entityViewTilePixelsY) {
    this.entityViewTilePixelsY = entityViewTilePixelsY;
  }

  public Color getBaseFogOfWarColor() {
    return this.baseFogOfWarColor;
  }

  /**
   * Gets the width of the screen.
   * @return screenWidth
   * @throws Error if screenWidth not set
   */
  public int getContextScreenWidth() {
    if (contextScreenWidth == CONTEXT_SCREEN_SIZE_NOT_SET) {
      throw new IllegalStateException("You have not set the screen height yet");
    }
    return contextScreenWidth;
  }

  /**
   * Gets the height of the screen.
   * @return screenHeight
   * @throws Error if screenHeight not set
   */
  public int getContextScreenHeight() {
    if (contextScreenHeight == CONTEXT_SCREEN_SIZE_NOT_SET) {
      throw new IllegalStateException("You have not set the screen height yet");
    }
    return contextScreenHeight;
  }

  public void setScreenDim(int width, int height) {
    this.contextScreenWidth = width;
    this.contextScreenHeight = height;
  }
}
