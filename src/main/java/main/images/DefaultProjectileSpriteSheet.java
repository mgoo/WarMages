package main.images;

import static java.util.Objects.requireNonNull;
import static main.common.images.ProjectileSpriteSheet.Sequence.PROJECTILE_HEIGHT;
import static main.common.images.ProjectileSpriteSheet.Sequence.PROJECTILE_WIDTH;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import main.common.entity.Direction;
import main.common.images.GameImage;
import main.common.images.GameImageBuilder;
import main.common.images.GameImageResource;
import main.common.images.ProjectileSpriteSheet;

public class DefaultProjectileSpriteSheet implements ProjectileSpriteSheet {

  private static final long serialVersionUID = 1L;

  private final GameImageResource resource;
  private transient Map<MapKey, List<GameImage>> sequenceToImages = new HashMap<>();


  public DefaultProjectileSpriteSheet(GameImageResource baseImageResource) {
    this.resource = baseImageResource;
  }

  /**
  * Called during deserialisation.
  */
  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject();

    sequenceToImages = new HashMap<>();
  }

  @Override
  public List<GameImage> getImagesForSequence(Sequence sequence, Direction unitDirection) {
    return sequenceToImages.computeIfAbsent(
        new MapKey(requireNonNull(sequence), requireNonNull(unitDirection)),
        mapKey -> getImages(
            sequence,
            mapKey.direction,
            resource.getGameImage().getFilePath()
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
                .setStartX(col * PROJECTILE_WIDTH)
                .setStartY(row * PROJECTILE_HEIGHT)
                .setWidth(PROJECTILE_WIDTH)
                .setHeight(PROJECTILE_HEIGHT)
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
