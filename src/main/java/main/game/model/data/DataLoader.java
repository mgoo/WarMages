package main.game.model.data;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import main.game.model.data.dataobject.AbilityData;
import main.game.model.data.dataobject.AnimationData;
import main.game.model.data.dataobject.AttackData;
import main.game.model.data.dataobject.ImageData;
import main.game.model.data.dataobject.SpriteSheetData;
import main.game.model.data.dataobject.SpriteSheetTypeData;
import main.game.model.data.dataobject.UnitData;

/**
 * Class to hold the caches for the data.
 * Basically a wrapper for the json data files.
 * @author Andrew McGhie
 */
public class DataLoader {

  // TODO organise in to resource packs
  private static final String ANIMATIONS_FILE = "resources/data/animations.json";
  private static final String SPRITESHEETTYPES_FILE = "resources/data/spritesheettypes.json";
  private static final String SPRITESHEETS_FILE = "resources/data/spritesheets.json";
  private static final String IMAGES_FILE = "resources/data/images.json";
  private static final String ATTACKS_FILE = "resources/data/attacks.json";
  private static final String UNITTYPES_FILE = "resources/data/unittypes.json";
  private static final String ABILITIES_FILE = "resources/data/abilities.json";

  private final Map<String, AnimationData> animations = new HashMap<>();
  private final Map<String, SpriteSheetTypeData> spriteSheetTypes = new HashMap<>();
  private final Map<String, SpriteSheetData> spriteSheets = new HashMap<>();
  private final Map<String, ImageData> images = new HashMap<>();
  private final Map<String, AttackData> attacks = new HashMap<>();
  private final Map<String, UnitData> unitTypes = new HashMap<>();
  private final Map<String, AbilityData> abilities = new HashMap<>();

  public DataLoader() {
    this(ANIMATIONS_FILE, SPRITESHEETTYPES_FILE, SPRITESHEETS_FILE, IMAGES_FILE,
        ATTACKS_FILE, UNITTYPES_FILE, ABILITIES_FILE);
  }

  public DataLoader(String animationsFile, String spriteSheetTypesFile, String spriteSheetsFile,
                    String imagesFile, String attacksFile, String unittypesFile,
                    String abilitiesFile) {

    this.loadAnimations(animationsFile);
    this.loadSpriteSheetTypes(spriteSheetTypesFile);
    this.loadSpriteSheets(spriteSheetsFile);
    this.loadImages(imagesFile);
    this.loadAttacks(attacksFile);
    this.loadUnitTypes(unittypesFile);
    this.loadAbilities(abilitiesFile);
  }

  private void loadAnimations(String file) {
    try {
      FileReader animationsFile = new FileReader(file);
      AnimationData[] animationsData = new Gson().fromJson(animationsFile, AnimationData[].class);
      Arrays.stream(animationsData).forEach(ad -> {
        animations.put(ad.getId(), ad);
      });
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("There was a problem reading the file " + file);
    }
  }

  private void loadSpriteSheetTypes(String file) {
    try {
      FileReader spriteSheetTypesFile = new FileReader(file);
      SpriteSheetTypeData[] spriteSheetTypesData = new Gson().fromJson(
          spriteSheetTypesFile,
          SpriteSheetTypeData[].class
      );
      Arrays.stream(spriteSheetTypesData).forEach(sstd -> {
        sstd.buildRelationships(this);
        spriteSheetTypes.put(sstd.getId(), sstd);
      });
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("There was a problem reading the file " + file);
    }
  }

  private void loadSpriteSheets(String file) {
    try {
      FileReader spriteSheetsFile = new FileReader(file);
      SpriteSheetData[] spriteSheetsData = new Gson().fromJson(
          spriteSheetsFile,
          SpriteSheetData[].class
      );
      Arrays.stream(spriteSheetsData).forEach(ssd -> {
        ssd.build(this);
        spriteSheets.put(ssd.getId(), ssd);
      });
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("There was a problem reading the file " + file);
    }
  }

  private void loadImages(String file) {
    try {
      FileReader imagesFile = new FileReader(file);
      ImageData[] imageData = new Gson().fromJson(imagesFile, ImageData[].class);
      Arrays.stream(imageData).forEach(id -> {
        id.build();
        images.put(id.getId(), id);
      });
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("There was a problem reading the file " + file);
    }
  }

  private void loadAttacks(String file) {
    try {
      FileReader attacksFile = new FileReader(file);
      AttackData[] attackData = new Gson().fromJson(attacksFile, AttackData[].class);
      Arrays.stream(attackData).forEach(ad -> {
        ad.buildRelationships(this);
        attacks.put(ad.getId(), ad);
      });
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("There was a problem reading the file " + file);
    }
  }

  private void loadUnitTypes(String file) {
    try {
      FileReader unitTypesFile = new FileReader(file);
      UnitData[] unitData = new Gson().fromJson(unitTypesFile, UnitData[].class);
      Arrays.stream(unitData).forEach(utd -> {
        utd.buildRelationships(this);
        unitTypes.put(utd.getId(), utd);
      });
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("There was a problem reading the file " + file);
    }
  }

  private void loadAbilities(String file) {
    try {
      FileReader abilitiesFile = new FileReader(file);
      AbilityData[] abilitiesData = new Gson().fromJson(abilitiesFile, AbilityData[].class);
      Arrays.stream(abilitiesData).forEach(ad -> {
        ad.buildRelationships(this);
        abilities.put(ad.getId(), ad);
      });
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("There was a problem reading the file " + file);
    }
  }

  /**
   * Gets the data Object for a Animation.
   * @throws IllegalStateException If the animation does not exist
   * @throws AssertionError If the animations have not being loaded yet
   */
  public AnimationData getDataForAnimation(String id) {
    assert !animations.isEmpty() : "The animations data has not being loaded yet";
    if (!animations.containsKey(id)) {
      throw new IllegalStateException("The animation " + id + " was not found");
    }
    return animations.get(id);
  }

  /**
   * Gets the data Object for a type of Sprite sheet.
   * @throws IllegalStateException If the Sprite sheet type does not exist
   * @throws AssertionError If the Sprite sheet types have not being loaded yet
   */
  public SpriteSheetTypeData getDataForSpriteSheetType(String id) {
    assert !spriteSheetTypes.isEmpty() : "The spritesheet type data has not being loaded yet";
    if (!spriteSheetTypes.containsKey(id)) {
      throw new IllegalStateException("The spritesheet type " + id + " was not found");
    }
    return spriteSheetTypes.get(id);
  }

  /**
   * Gets the data Object for a Sprite sheet.
   * @throws IllegalStateException If the Sprite sheet does not exist
   * @throws AssertionError If the Sprite sheet have not being loaded yet
   */
  public SpriteSheetData getDataForSpriteSheet(String id) {
    assert !spriteSheets.isEmpty() : "The spritesheet data has not being loaded yet";
    if (!spriteSheets.containsKey(id)) {
      throw new IllegalStateException("The spritesheet " + id + " was not found");
    }
    return spriteSheets.get(id);
  }

  /**
   * Gets the data Object for a image.
   * @throws IllegalStateException If the image does not exist
   * @throws AssertionError If the images have not being loaded yet
   */
  public ImageData getDataForImage(String id) {
    assert !images.isEmpty() : "The images data has not being loaded yet";
    if (!images.containsKey(id)) {
      throw new IllegalStateException("The image " + id + " was not found");
    }
    return images.get(id);
  }

  /**
   * Gets the data Object for a attack.
   * @throws IllegalStateException If the attack does not exist
   * @throws AssertionError If the attacks have not being loaded yet
   */
  public AttackData getDataForAttack(String id) {
    assert !attacks.isEmpty() : "The attack data has not being loaded yet";
    if (!attacks.containsKey(id)) {
      throw new IllegalStateException("The attack " + id + " was not found");
    }
    return attacks.get(id);
  }

  /**
   * Gets the data Object for a type of unit.
   * @throws IllegalStateException If the unit type does not exist
   * @throws AssertionError If the unit types have not being loaded yet
   */
  public UnitData getDataForUnitType(String id) {
    assert !unitTypes.isEmpty() : "The unit types have not being loaded yet";

    if (!unitTypes.containsKey(id)) {
      throw new IllegalStateException("The unit type " + id + " was not found");
    }
    return unitTypes.get(id);
  }

  /**
   * Gets the data Object for a ability.
   * @throws IllegalStateException If the ability does not exist
   * @throws AssertionError If the ability have not being loaded yet
   */
  public AbilityData getDataForAbility(String id) {
    assert !abilities.isEmpty() : "The ability data has not being loaded yet";
    if (!abilities.containsKey(id)) {
      throw new IllegalStateException("The ability " + id + " was not found");
    }
    return abilities.get(id);
  }

}
