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
  ARCHER_SPRITE_SHEET(new GameImage("images/units/archer.png")),
  FOOT_KNIGHT_SPRITE_SHEET(new GameImage("images/units/footknight.png")),
  GOLDEN_HERO_SPRITE_SHEET(new GameImage("images/units/golden_hero.png")),
  MALE_MAGE_SPRITE_SHEET(new GameImage("images/units/male_mage.png")),
  ORC_SPEARMAN_SPRITE_SHEET(new GameImage("images/units/orc_spearman.png")),
  SKELETON_ARCHER_SPRITE_SHEET(new GameImage("images/units/skeleton_archer.png")),
  /**
   * For testing only.
   */
  TEST_IMAGE_FULL_SIZE(new GameImage("fixtures/images/image_for_image_provider_tests.png")),
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
