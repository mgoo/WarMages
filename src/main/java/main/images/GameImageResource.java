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
  // Items
  POTION_BLUE_ITEM(new GameImageBuilder("images/items/potion-blue.png").create()),
  RING_BLUE_ITEM(new GameImageBuilder("images/items/ring-gold.png").create()),

  // Projectiles
  //   NOTE: For consistency, if there are projectiles that have a direction (e.g. an arrow),
  //   the image should have the projectile pointing upwards.
  // Use this one as static image, rather than a sequence of images:
  WHITE_BALL_PROJECTILE(
      new GameImageBuilder("images/projectiles/whitemissile-impact-3.png").create()
  ),
  ARROW_PROJECTILE(new GameImageBuilder("images/projectiles/missile-n.png").create()),

  // Tiles
  GRASS_TILE(new GameImageBuilder("images/tiles/grass.png").create()),

  // Map Entities (Landmark stuff)
  TREE_MAP_ENTITY(
      new GameImageBuilder("images/map-entities/magecity.png")
          .setStartX(128)
          .setStartY(448)
          .setWidth(128)
          .setHeight(160)
          .setOverflowTop(160 - 128)
          .create()
  ),

  // Unit Sprite Sheets
  ARCHER_SPRITE_SHEET(new GameImageBuilder("images/units/archer.png").create()),
  FOOT_KNIGHT_SPRITE_SHEET(new GameImageBuilder("images/units/footknight.png").create()),
  GOLDEN_HERO_SPRITE_SHEET(new GameImageBuilder("images/units/golden_hero.png").create()),
  MALE_MAGE_SPRITE_SHEET(new GameImageBuilder("images/units/male_mage.png").create()),
  ORC_SPEARMAN_SPRITE_SHEET(new GameImageBuilder("images/units/orc_spearman.png").create()),
  SKELETON_ARCHER_SPRITE_SHEET(new GameImageBuilder("images/units/skeleton_archer.png").create()),

  // Test Images
  TEST_IMAGE_FULL_SIZE(
      new GameImageBuilder("fixtures/images/image_for_image_provider_tests.png").create()
  ),
  TEST_IMAGE_PARTIAL_SIZE(
      new GameImageBuilder("fixtures/images/image_for_image_provider_tests.png")
          .setStartX(1)
          .setStartY(1)
          .setWidth(3)
          .setHeight(2)
          .create()
  ),
  /**
   * For testing or blank image only.
   */
  TEST_IMAGE_1_1(new GameImageBuilder("fixtures/images/test11.png").create());

  private final GameImage gameImage;

  GameImageResource(GameImage gameImage) {
    this.gameImage = gameImage;
  }

  public GameImage getGameImage() {
    return gameImage;
  }
}
