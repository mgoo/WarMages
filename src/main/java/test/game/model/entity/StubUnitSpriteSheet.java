package test.game.model.entity;

import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import main.common.entity.Direction;
import main.common.images.GameImage;
import main.common.images.UnitSpriteSheet;

public class StubUnitSpriteSheet implements UnitSpriteSheet {

  @Override
  public List<GameImage> getImagesForSequence(Sequence sequence, Direction unitDirection) {
    return Stream.generate(() -> mock(GameImage.class))
        .limit(sequence.numberOfColumns)
        .collect(Collectors.toList());
  }
}
