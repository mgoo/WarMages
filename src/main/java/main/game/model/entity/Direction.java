package main.game.model.entity;

import java.io.Serializable;
/**
 * Represents one of the 90 degree directions that a {@link Unit} can move in.
 * <p>
 * Note that the order of these values should not be changed. Because they are depended upon by
 * {@link main.images.UnitSpriteSheet.Sequence}.
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
}
