package main.common.images;

import java.io.Serializable;
import java.util.List;
import main.game.model.entity.Direction;

/**
 * Represents an image with multiple images on it.
 * @author chongdyla
 */
public interface UnitSpriteSheet extends Serializable {

  /**
   * Gets a list of images for the sequence for when a {@link main.game.model.entity.Unit}
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
    SPELL_CAST(0, 7, true, 6),
    THRUST(4, 8, true, 5),
    WALK(8, 9, true),
    SLASH(12, 6, true, 4),
    SHOOT(16, 13, true, 9),
    DYING(20, 6, false),
    DEAD(20, 1, false) {
      @Override
      public int firstColumn() {
        return 5;
      }
    },
    /**
     * Just reuses the first few frames of the {@link UnitSpriteSheet.Sequence#WALK} sequence.
     */
    IDLE(8, 2, true);

    public static final int UNIT_WIDTH = 64;
    public static final int UNIT_HEIGHT = 64;
    public static final int NOT_AN_ATTACK_SEQUENCE = -1;

    public final int firstRow;
    public final int numberOfColumns;
    public final boolean supportsDirections;
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

    /**
     * Returns the index of the frame where the attack action should occur (whether it be launching
     * a projectile or applying damage with the sword attack.
     */
    public int getAttackFrame() {
      if (attackFrame == NOT_AN_ATTACK_SEQUENCE) {
        throw new IllegalStateException("This is not an attack sequence" + name());
      }

      return attackFrame;
    }

    public int firstColumn() {
      return 0;
    }
  }
}
