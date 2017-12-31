package main.common.images;

/**
 * ************************************************************************************************
 * NOTE TO TUTORS: I emailed Marco about keeping these images classes in the common package,
 * and he said it was ok. (I need to keep these classes here because GameImageResource defines
 * all the {@link GameImage}s and is used in various parts of the app.
 * ************************************************************************************************
 *
 * <p>
 * Defines all the {@link GameImage} objects with their filenames. All other {@link GameImage}
 * objects not defined here must be composed of {@link GameImage}s defined here.
 * </p>
 * <p>
 * This enum provides easy access to images to the rest of the app without the rest of the app
 * having to know about the file paths.
 * </p>
 * @author chongdyla
 */
public enum GameImageResource {
  // Items
  POTION_BLUE_ITEM("images/items/potion-blue.png"),
  RING_GOLD_ITEM("images/items/ring-gold.png"),
  WHITE_BALL_ITEM("images/projectiles/whitemissile-impact-3.png"),

  HEAL_SPELL_ICON("images/icons/potion_green_medium.png"),
  SMALL_POTION_ICON("images/icons/potion_green_small.png"),
  RING_ICON("images/icons/ring_gold.png"),
  FIREBALL_ICON("images/icons/fireball.png"),
  LIGHTING_ICON("images/icons/lightning.png"),

  // Projectiles
  ARROW_PROJECTILE("images/projectile-sheets/arrow-sheet.png"),
  // Use this one as static image, rather than a sequence of images:
  FIREBALL_PROJECTILE("images/projectile-sheets/fireball-sheet.png"),
  WHITE_PROJECTILE("images/projectile-sheets/whitemissile-sheet.png"),
  ICE_PROJECTILE("images/projectile-sheets/icemissile-sheet.png"),
  LIGHTING("images/projectile-sheets/lighting-sheet.png"),
  EXPLOSION("images/projectile-sheets/explosion1-sheet.png"),

  HEAL_SPRITESHEET("images/misc-spritesheets/healeffect.png"),

  // Tiles
  GRASS_TILE("images/tiles/grass.png"),

  // Map Entities (Landmark stuff)
  TREE_1(
      new GameImageBuilder("images/map-entities/magecity.png")
          .setStartX(128)
          .setStartY(448)
          .setWidth(128)
          .setHeight(160)
          .setOverflowTop(160 - 128)
          .create()
  ),
  TREE_2(
      new GameImageBuilder("images/map-entities/trees_1.png")
          .setStartX(6)
          .setStartY(1)
          .setWidth(153)
          .setHeight(203)
          .create()
  ),
  TREE_3(
      new GameImageBuilder("images/map-entities/trees_1.png")
          .setStartX(171)
          .setStartY(0)
          .setWidth(81)
          .setHeight(96)
          .create()
  ),
  TREE_4(
      new GameImageBuilder("images/map-entities/trees_1.png")
          .setStartX(162)
          .setStartY(95)
          .setWidth(92)
          .setHeight(98)
          .create()
  ),
  TREE_5(
      new GameImageBuilder("images/map-entities/trees_1.png")
          .setStartX(0)
          .setStartY(197)
          .setWidth(130)
          .setHeight(178)
          .create()
  ),
  TREE_6(
      new GameImageBuilder("images/map-entities/trees_1.png")
          .setStartX(134)
          .setStartY(197)
          .setWidth(143)
          .setHeight(189)
          .create()
  ),
  TREE_7(
      new GameImageBuilder("images/map-entities/trees_1.png")
          .setStartX(0)
          .setStartY(387)
          .setWidth(127)
          .setHeight(122)
          .create()
  ),
  TREE_8(
      new GameImageBuilder("images/map-entities/trees_1.png")
          .setStartX(133)
          .setStartY(388)
          .setWidth(88)
          .setHeight(123)
          .create()
  ),

  BARRLE_1(
      new GameImageBuilder("images/map-entities/magecity.png")
          .setStartX(129)
          .setStartY(77)
          .setWidth(31)
          .setHeight(40)
          .create()
  ),
  BARRLE_2(
      new GameImageBuilder("images/map-entities/magecity.png")
          .setStartX(96)
          .setStartY(65)
          .setWidth(33)
          .setHeight(61)
          .create()
  ),
  BARRLE_3(
      new GameImageBuilder("images/map-entities/magecity.png")
          .setStartX(38)
          .setStartY(67)
          .setWidth(54)
          .setHeight(61)
          .create()
  ),
  BARRLE_4(
      new GameImageBuilder("images/map-entities/magecity.png")
          .setStartX(0)
          .setStartY(67)
          .setWidth(33)
          .setHeight(61)
          .create()
  ),

  FOUNTAIN(
      new GameImageBuilder("images/map-entities/magecity.png")
          .setStartX(192)
          .setStartY(63)
          .setWidth(64)
          .setHeight(64)
          .create()
  ),
  TABLE(
      new GameImageBuilder("images/map-entities/magecity.png")
          .setStartX(162)
          .setStartY(16)
          .setWidth(63)
          .setHeight(47)
          .create()
  ),
  CHAIR(
      new GameImageBuilder("images/map-entities/magecity.png")
          .setStartX(229)
          .setStartY(7)
          .setWidth(27)
          .setHeight(26)
          .create()
  ),

  BUILDING_1("images/map-entities/building_1.png"),
  BUILDING_2("images/map-entities/building_2.png"),
  BUILDING_3("images/map-entities/building_3.png"),
  BUILDING_4("images/map-entities/building_4.png"),
  BUILDING_5("images/map-entities/building_5.png"),
  BUILDING_6("images/map-entities/building_6.png"),
  BUILDING_7("images/map-entities/building_7.png"),
  BUILDING_8("images/map-entities/building_8.png"),
  BUILDING_9("images/map-entities/building_9.png"),
  BUILDING_10("images/map-entities/building_10.png"),
  BUILDING_11("images/map-entities/building_11.png"),
  BUILDING_12("images/map-entities/building_12.png"),
  BUILDING_13("images/map-entities/building_13.png"),
  BUILDING_14("images/map-entities/building_14.png"),
  BUILDING_15("images/map-entities/building_15.png"),
  BUILDING_16("images/map-entities/building_16.png"),
  BUILDING_17("images/map-entities/building_17.png"),
  BUILDING_18("images/map-entities/building_18.png"),
  BUILDING_19("images/map-entities/building_19.png"),
  BUILDING_20("images/map-entities/building_20.png"),
  BUILDING_21("images/map-entities/building_21.png"),

  // Unit Sprite Sheets
  ARCHER_SPRITE_SHEET("images/units/archer.png"),
  DARK_ELF_SPRITE_SHEET("images/units/dark_elf.png"),
  FOOT_KNIGHT_SPRITE_SHEET("images/units/footknight.png"),
  SPEARMAN_SPRITE_SHEET("images/units/spearman.png"),
  GOLDEN_HERO_SPRITE_SHEET("images/units/golden_hero.png"),
  MALE_MAGE_SPRITE_SHEET("images/units/male_mage.png"),
  MAGE_CAPE_SPRITE_SHEET("images/units/female_mage.png"),
  MAGE_WHITE_SPRITE_SHEET("images/units/white_mage.png"),
  ORC_SPEARMAN_SPRITE_SHEET("images/units/orc_spearman.png"),
  SKELETON_ARCHER_SPRITE_SHEET("images/units/skeleton_archer.png"),

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
