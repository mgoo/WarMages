package main.common.images;

import java.io.Serializable;
import java.util.List;
import main.common.entity.Direction;

public interface ProjectileSpriteSheet extends Serializable {

  List<GameImage> getImagesForSequence(Sequence sequence, Direction direction);

  enum Sequence {
    FIREBALL_FLY(0, 3, true),
    FIREBALL_IMPACT(4, 15, true),
    WHITEMISSILE_FLY(0, 1, true),
    WHITEMISSILE_IMPACT(4, 6, true),
    ICE_FLY(0, 2, true),
    ICE_IMPACT(4, 7, true),
    ARROW(0, 1, true);

    public static final int PROJECTILE_WIDTH = 150;
    public static final int PROJECTILE_HEIGHT = 150;

    public final int row;
    public final int frames;
    public final boolean supportsDirections;

    Sequence(int row, int frames, boolean supportsDirection) {
      this.row = row;
      this.frames = frames;
      this.supportsDirections = supportsDirection;
    }

    public int firstColumn() {
      return 0;
    }
  }
}
