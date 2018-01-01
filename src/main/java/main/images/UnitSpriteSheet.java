package main.images;

import java.io.Serializable;
import java.util.List;
import main.game.model.entity.Direction;
import main.game.model.entity.Unit;

/**
 * Represents an image with multiple images on it.
 * @author chongdyla
 */
public interface UnitSpriteSheet extends Serializable {

  /**
   * Gets a list of images for the sequence for when a {@link Unit}
   * is in a given direction.
   */
  List<GameImage> getImagesForSequence(Sequence sequence, Direction unitDirection);

  /**
   * Represents rows of images in this sprite sheet. If a value {@link
   * UnitSpriteSheet.Sequence#supportsDirections} then it generates all four rows of images.
   *
   * @see <a href="http://gaurav.munjal.us/Universal-LPC-Spritesheet-Character-Generator/"></a> for
   *     animations examples.
   */
  enum Sequence {
    SPELL_CAST(0, 7, true),
    THRUST(4, 8, true),
    WALK(8, 9, true),
    SLASH(12, 6, true),
    SHOOT(16, 13, true),
    DYING(20, 6, false),
    DEAD(20, 1, false) {
      @Override
      public int firstColumn() {
        return 5;
      }
    },
    PICKUP(20, 4, false), // Uses the first 4 frames of dying
    /**
     * Just reuses the first few frames of the {@link UnitSpriteSheet.Sequence#WALK} sequence.
     */
    IDLE(8, 1, true);

    public static final int UNIT_WIDTH = 128;
    public static final int UNIT_HEIGHT = 128;

    public final int firstRow;
    public final int frames;
    public final boolean supportsDirections;

    Sequence(int firstRow, int frames, boolean supportsDirections) {
      this.firstRow = firstRow;
      this.frames = frames;
      this.supportsDirections = supportsDirections;
    }

    public int firstColumn() {
      return 0;
    }
  }
}
