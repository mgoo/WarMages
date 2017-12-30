package main.common.images;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import main.common.entity.Direction;

public interface SpriteSheet extends Serializable {

  List<GameImage> getImagesForSequence(Sequence sequence, Direction direction);

  enum Sequence {
    FIREBALL_FLY(0, 3, true),
    FIREBALL_IMPACT(4, 15, true),
    WHITEMISSILE_FLY(0, 1, true),
    WHITEMISSILE_IMPACT(4, 6, true),
    ICE_FLY(0, 2, true),
    ICE_IMPACT(4, 7, true),
    ARROW(0, 1, true),
    HEAL(0, 5, false);

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

  enum Sheet {
    HEAL_EFFECT(GameImageResource.HEAL_SPRITESHEET,
        150, 150,
        new Sequence[]{Sequence.HEAL}),
    FIREBALL_PROJECTILE(GameImageResource.FIREBALL_PROJECTILE,
        150, 150,
        new Sequence[]{Sequence.FIREBALL_FLY, Sequence.FIREBALL_IMPACT}),
    WHITEMISSILE_PROJECTILE(GameImageResource.WHITE_PROJECTILE,
        150, 150,
        new Sequence[]{Sequence.WHITEMISSILE_FLY, Sequence.WHITEMISSILE_IMPACT}),
    ICEMISSSILE_PROJECTILE(GameImageResource.ICE_PROJECTILE,
        150, 150,
        new Sequence[]{Sequence.ICE_FLY, Sequence.ICE_IMPACT}),
    ARROW_PROJECTILE(GameImageResource.ARROW_PROJECTILE,
        150, 150,
        new Sequence[]{Sequence.ARROW});

    public final GameImageResource baseImage;
    public final int tileWidth;
    public final int tileHeight;
    public final Sequence[] sequences;

    private transient Map<MapKey, List<GameImage>> sequenceToImages = new HashMap<>();

    Sheet(GameImageResource baseimage, int tileWidth, int tileHeight, Sequence[] sequences) {
      this.baseImage = baseimage;
      this.tileWidth = tileWidth;
      this.tileHeight = tileHeight;
      this.sequences = sequences;
    }

    /**
     * Called during deserialisation.
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      in.defaultReadObject();

      sequenceToImages = new HashMap<>();
    }

    public List<GameImage> getImagesForSequence(Sequence sequence) {
      return this.getImagesForSequence(sequence, Direction.UP);
    }

    public List<GameImage> getImagesForSequence(Sequence sequence, Direction unitDirection) {
      assert Arrays.asList(this.sequences).contains(sequence)
          : "The spritesheet does not contain the sequence do errors may occour";
      return sequenceToImages.computeIfAbsent(
          new MapKey(requireNonNull(sequence), requireNonNull(unitDirection)),
          mapKey -> getImages(
              sequence,
              mapKey.direction,
              this.baseImage.getGameImage().getFilePath()
          )
      );
    }

    private List<GameImage> getImages(Sequence sequence, Direction direction, String filePath) {
      int row = sequence.row
          + (sequence.supportsDirections ? direction.ordinal() : 0);

      return IntStream.range(0, sequence.frames)
          .map(col -> col + sequence.firstColumn())
          .mapToObj(col ->
              new GameImageBuilder(filePath)
                  .setStartX(col * this.tileWidth)
                  .setStartY(row * this.tileHeight)
                  .setWidth(this.tileWidth)
                  .setHeight(this.tileHeight)
                  .create()
          )
          .collect(Collectors.toList());
    }

    static class MapKey {

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
}
