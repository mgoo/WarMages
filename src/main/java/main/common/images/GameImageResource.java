package main.common.images;

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
  POTION_BLUE_ITEM("images/items/potion-blue.png"),
  RING_GOLD_ITEM("images/items/ring-gold.png"),
  WHITE_BALL_ITEM("images/projectiles/whitemissile-impact-3.png"),

  // Projectiles
  //   NOTE: For consistency, if there are projectiles that have a direction (e.g. an arrow),
  //   the image should have the projectile pointing upwards.
  // Use this one as static image, rather than a sequence of images:
  ARROW_PROJECTILE("images/projectiles/missile-n.png"),
  FIREBALL_PROJECTILE("images/projectiles/fireball-impact-1.png"),

  // Tiles
  GRASS_TILE("images/tiles/grass.png"),

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
  ARCHER_SPRITE_SHEET("images/units/archer.png"),
  DARK_ELF_SPRITE_SHEET("images/units/dark_elf.png"),
  FOOT_KNIGHT_SPRITE_SHEET("images/units/footknight.png"),
  GOLDEN_HERO_SPRITE_SHEET("images/units/golden_hero.png"),
  MALE_MAGE_SPRITE_SHEET("images/units/male_mage.png"),
  ORC_SPEARMAN_SPRITE_SHEET("images/units/orc_spearman.png"),
  SKELETON_ARCHER_SPRITE_SHEET(
      "images/units/skeleton_archer.png"
  ),

  // Test Images
  TEST_IMAGE_FULL_SIZE("fixtures/images/image_for_image_provider_tests.png"),
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
  TEST_IMAGE_1_1("fixtures/images/test11.png");

  private final GameImage gameImage;

  GameImageResource(GameImage gameImage) {
    this.gameImage = gameImage;
  }

  GameImageResource(String filename) {
    this(new GameImageBuilder(filename).create());
  }

  public GameImage getGameImage() {
    return gameImage;
  }
}
