package main.game.model.entity;

import java.util.Arrays;
import main.common.images.UnitSpriteSheet;
import main.common.util.MapPoint;

/**
 * Represents one of the 90 degree directions that a {@link Unit} can move in.
 * <p>
 * Note that the order of these values should not be changed. Because they are depended upon by
 * {@link UnitSpriteSheet.Sequence}.
 * </p>
 */
public enum Direction {
  /**
   * Unit is facing away from the camera.
   */
  UP(180, 270, false),
  LEFT(90, 180, false),
  /**
   * Unit is facing away a towards the camera.
   */
  DOWN(0, 90, true),
  RIGHT(270, 360, true);

  private final boolean validFor0;

  /**
   * Calculate distance between start and end point. The result represents which way a unit should
   * face when walking from start to end on the diagonal representation.
   */
  public static Direction between(MapPoint start, MapPoint end) {
    double dx = end.x - start.x;
    double dy = end.y - start.y;

    // (dx=1, dy=0) ==> 0 degrees.
    // Increasing degrees means going anticlockwise.
    // Values are from -180 to 180.
    double degrees = Math.toDegrees(Math.atan2(dy, dx));

    return Arrays.stream(values())
        .filter(direction -> direction.isValidFor(degrees))
        .findAny()
        .orElseThrow(() -> new AssertionError("Direction angle fields musn't be correct"));
  }

  private final double minDegrees;
  private final double maxDegrees;

  Direction(double minDegrees, double maxDegrees, boolean validFor0) {
    this.validFor0 = validFor0;

    if (minDegrees >= maxDegrees || minDegrees < 0 || maxDegrees > 360) {
      throw new IllegalArgumentException();
    }

    this.minDegrees = minDegrees;
    this.maxDegrees = maxDegrees;
  }

  /**
   * True if degrees is within the range defined by {@link Direction#minDegrees} and {@link
   * Direction#maxDegrees}.
   */
  public boolean isValidFor(double degrees) {
    double modifiedDegrees = (degrees + 360) % 360;

    return (validFor0 && modifiedDegrees == 0)
        || (modifiedDegrees >= minDegrees && modifiedDegrees < maxDegrees);
  }
}
