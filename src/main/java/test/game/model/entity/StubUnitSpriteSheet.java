package test.game.model.entity;

import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import main.game.model.entity.Direction;
import main.images.GameImage;
import main.images.UnitSpriteSheet;

public class StubUnitSpriteSheet implements UnitSpriteSheet {

  private final Map<MapKey, List<GameImage>> sequenceToImages = new HashMap<>();

  @Override
  public List<GameImage> getImagesForSequence(Sequence sequence, Direction unitDirection) {
    return sequenceToImages.computeIfAbsent(
        new MapKey(sequence, unitDirection),
        mapKey -> Stream
            .generate(() -> mock(GameImage.class))
            .limit(sequence.frames)
            .collect(Collectors.toList())
    );
  }

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
