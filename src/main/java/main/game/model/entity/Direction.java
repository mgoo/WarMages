package main.game.model.entity;

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
  UP,
  LEFT,
  /**
   * Unit is facing away a towards the camera.
   */
  DOWN,
  RIGHT;

  public static Direction between(MapPoint start, MapPoint end) {
    double gradient = (end.y - start.y) / (end.x - start.x);
    if (gradient < 1) {
      if (end.y < start.y) {
        return Direction.UP;
      } else {
        return Direction.DOWN;
      }
    } else {
      if (end.x < start.x) {
        return Direction.LEFT;
      } else {
        return Direction.RIGHT;
      }
    }
  }
}
