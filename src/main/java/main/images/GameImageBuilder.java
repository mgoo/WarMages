package main.images;

public class GameImageBuilder {

  private String filePath;
  private int startX;
  private int startY;
  private int width = GameImage.FILL_SPACE;
  private int height = GameImage.FILL_SPACE;
  private int overflowTop;
  private int overflowRight;
  private int overflowBottom;
  private int overflowLeft;

  GameImageBuilder(String filePath) {
    this.filePath = filePath;
  }

  public GameImageBuilder setStartX(int startX) {
    this.startX = startX;
    return this;
  }

  public GameImageBuilder setStartY(int startY) {
    this.startY = startY;
    return this;
  }

  public GameImageBuilder setWidth(int width) {
    this.width = width;
    return this;
  }

  public GameImageBuilder setHeight(int height) {
    this.height = height;
    return this;
  }

  public GameImageBuilder setOverflowTop(int overflowTop) {
    this.overflowTop = overflowTop;
    return this;
  }

  public GameImageBuilder setOverflowRight(int overflowRight) {
    this.overflowRight = overflowRight;
    return this;
  }

  public GameImageBuilder setOverflowBottom(int overflowBottom) {
    this.overflowBottom = overflowBottom;
    return this;
  }

  public GameImageBuilder setOverflowLeft(int overflowLeft) {
    this.overflowLeft = overflowLeft;
    return this;
  }

  /**
   * Creates the object with the set/default parameters.
   */
  public GameImage create() {
    return new GameImage(
        filePath, startX, startY, width, height, overflowTop, overflowRight, overflowBottom,
        overflowLeft
    );
  }
}