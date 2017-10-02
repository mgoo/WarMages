package main.images;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import main.game.model.entity.Direction;

/**
 * Represents an image with multiple images on it.
 */
public class UnitSpriteSheet implements Serializable {

  private static final long serialVersionUID = 1L;

  private final GameImageResource resource;
  private final transient Map<MapKey, List<GameImage>> sequenceToImages = new HashMap<>();

  /**
   * Default constructor.
   * @param baseImageResource A sprite sheet image.
   */
  public UnitSpriteSheet(GameImageResource baseImageResource) {
    this.resource = baseImageResource;

    if (resource.getGameImage().getStartX() != 0 || resource.getGameImage().getStartY() != 0) {
      throw new IllegalArgumentException(
          "Base images with an offset are not supported yet: " + baseImageResource
      );
    }

    if (!baseImageResource.name().endsWith("_SPRITE_SHEET")) {
      throw new IllegalArgumentException(
          "The given image resource is not a sprite sheet: " + baseImageResource
      );
    }
  }

  /**
   * Gets a list of images for the sequence for when a {@link main.game.model.entity.Unit}
   * is in a given direction.
   */
  public List<GameImage> getImagesForSequence(Sequence sequence, Direction unitDirection) {
    return sequenceToImages.computeIfAbsent(
        new MapKey(sequence, unitDirection),
        mapKey -> mapKey.sequence.getImages(
            mapKey.direction,
            resource.getGameImage().getFilePath()
        )
    );
  }

  /**
   * Represents rows of images in this sprite sheet. If a value {@link Sequence#supportsDirections}
   * then it generates all four rows of images.
   *
   * @see <a href="http://gaurav.munjal.us/Universal-LPC-Spritesheet-Character-Generator/"></a> for
   *     animations examples.
   */
  public enum Sequence {
    SPELL_CAST(0, 7, true, 6),
    THRUST(4, 8, true, 5),
    WALK(8, 9, true),
    SLASH(12, 6, true, 4),
    SHOOT(16, 13, true, 9),
    HURT(20, 6, false),
    /**
     * Just reuses the first few frames of the {@link Sequence#WALK} sequence.
     */
    IDLE(8, 3, true);

    public static final int UNIT_WIDTH = 64;
    public static final int UNIT_HEIGHT = 64;
    private static final int NOT_AN_ATTACK_SEQUENCE = -1;

    private final int firstRow;
    private final int numberOfColumns;
    private final boolean supportsDirections;
    private final int attackFrame;

    Sequence(int firstRow, int numberOfColumns, boolean supportsDirections) {
      this.firstRow = firstRow;
      this.numberOfColumns = numberOfColumns;
      this.supportsDirections = supportsDirections;
      this.attackFrame = NOT_AN_ATTACK_SEQUENCE;
    }

    Sequence(int firstRow, int numberOfColumns, boolean supportsDirections, int attackFrame) {
      if (attackFrame < 0 || attackFrame > numberOfColumns) {
        throw new IllegalArgumentException();
      }

      this.firstRow = firstRow;
      this.numberOfColumns = numberOfColumns;
      this.supportsDirections = supportsDirections;
      this.attackFrame = attackFrame;
    }

    public int getAttackFrame() {
      if (attackFrame == NOT_AN_ATTACK_SEQUENCE) {
        throw new IllegalStateException("This is not an attack sequence" + name());
      }

      return attackFrame;
    }

    List<GameImage> getImages(Direction direction, String filePath) {
      int row = supportsDirections ? rowWithDirection(direction) : firstRow;

      return IntStream.range(0, numberOfColumns)
          .mapToObj(col ->
              new GameImageBuilder(filePath)
                  .setStartX(col * UNIT_WIDTH)
                  .setStartY(row * UNIT_HEIGHT)
                  .setWidth(UNIT_WIDTH)
                  .setHeight(UNIT_HEIGHT)
                  .create()
          )
          .collect(Collectors.toList());
    }

    private int rowWithDirection(Direction direction) {
      return firstRow + direction.ordinal();
    }
  }

  /**
   * Key in {@link UnitSpriteSheet#sequenceToImages}.
   */
  private static class MapKey {

    private final Sequence sequence;
    private final Direction direction;

    MapKey(Sequence sequence, Direction direction) {
      this.sequence = sequence;

      if (sequence.supportsDirections) {
        this.direction = direction;
      } else {
        this.direction = null;
      }
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      MapKey mapKey = (MapKey) o;

      if (sequence != mapKey.sequence) {
        return false;
      }
      return direction == mapKey.direction;
    }

    @Override
    public int hashCode() {
      int result = sequence != null ? sequence.hashCode() : 0;
      result = 31 * result + (direction != null ? direction.hashCode() : 0);
      return result;
    }
  }
}
