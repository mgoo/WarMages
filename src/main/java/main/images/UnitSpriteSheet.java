package main.images;

/**
 * Represents an image with multiple images on it.
 */
public class UnitSpriteSheet {

  // TODO

  private final GameImage baseImage;

  public UnitSpriteSheet(GameImageResource baseImageResource) {
    this.baseImage = baseImageResource.getGameImage();
  }
}
