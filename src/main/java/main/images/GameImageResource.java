package main.images;

/**
 * Defines all the {@link GameImage} objects with their filenames. All other {@link GameImage}
 * objects not defined here must be composed of {@link GameImage}s defined here.
 * <p>
 * This enum provides easy access to images to the rest of the app without the rest of the app
 * having to know about the file paths.
 * </p>
 */
public enum GameImageResource {
  MALE_MAGE_SPRITE_SHEET(new GameImage("images/units/male_mage.png")),
  /**
   * For testing only.
   */
  TEST_IMAGE_FULL_SIZE(new GameImage("fixtures/images/image_for_image_provider_tests.png")),
  /**
   * For testing or blank image only.
   */
  TEST_IMAGE_1_1(new GameImage("fixtures/images/test11.png")),
  /**
   * For testing only.
   */
  TEST_IMAGE_PARTIAL_SIZE(new GameImage(
      "fixtures/images/image_for_image_provider_tests.png", 1, 1, 3, 2
  ));

  private final GameImage gameImage;

  GameImageResource(GameImage gameImage) {
    this.gameImage = gameImage;
  }

  public GameImage getGameImage() {
    return gameImage;
  }
}
