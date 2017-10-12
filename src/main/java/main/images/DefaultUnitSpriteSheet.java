package main.images;

import static java.util.Objects.requireNonNull;
import static main.common.images.UnitSpriteSheet.Sequence.UNIT_HEIGHT;
import static main.common.images.UnitSpriteSheet.Sequence.UNIT_WIDTH;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import main.common.images.GameImage;
import main.common.images.GameImageBuilder;
import main.common.images.GameImageResource;
import main.common.images.UnitSpriteSheet;
import main.common.Direction;

public class DefaultUnitSpriteSheet implements UnitSpriteSheet {

  private static final long serialVersionUID = 1L;

  private final GameImageResource resource;
  private final transient Map<MapKey, List<GameImage>> sequenceToImages = new HashMap<>();

  /**
   * Default constructor.
   * @param baseImageResource A sprite sheet image.
   */
  public DefaultUnitSpriteSheet(GameImageResource baseImageResource) {
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
    int row = sequence.firstRow
        + (sequence.supportsDirections ? direction.ordinal() : 0);

    return IntStream.range(0, sequence.numberOfColumns)
        .map(col -> col + sequence.firstColumn())
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

  /**
   * Key in {@link DefaultUnitSpriteSheet#sequenceToImages}.
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
