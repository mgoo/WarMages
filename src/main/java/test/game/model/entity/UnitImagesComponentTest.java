package test.game.model.entity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import main.common.entity.Direction;
import main.common.images.GameImage;
import main.common.images.UnitSpriteSheet.Sequence;
import main.game.model.GameModel;
import main.game.model.entity.unit.DefaultUnit;
import main.game.model.entity.unit.UnitImagesComponent;
import org.junit.Test;

/**
 * Some tests.
 * @author chongdyla
 */
public class UnitImagesComponentTest {

  @Test
  public void numberOfTicksShouldBeTheSameForEachFrame() {
    // Given a sequence
    Sequence sequence = Sequence.SHOOT;
    // and a fixed number of ticks per frame
    int ticksPerFrame = UnitImagesComponent.TICKS_PER_FRAME;
    // and a stub unit
    DefaultUnit unit = mock(DefaultUnit.class);
    when(unit.getSpriteSheet()).thenReturn(new StubUnitSpriteSheet());
    when(unit.getCurrentDirection()).thenReturn(Direction.DOWN);
    // and a UnitImagesComponent
    UnitImagesComponent unitImagesComponent = new UnitImagesComponent(sequence, unit);

    // when we tick many times (for one whole cycle)
    Map<GameImage, Integer> ticksPerFrameCounts = new HashMap<>();
    do {
      ticksPerFrameCounts.compute(
          unitImagesComponent.getImage(),
          (image, count) -> count == null ? 1 : count + 1
      );
      unitImagesComponent.tick(GameModel.DELAY);
    } while (unitImagesComponent._getCurrentTick() > 0);

    // then each image should have been shown for the right number of frames
    for (Entry<GameImage, Integer> entry : ticksPerFrameCounts.entrySet()) {
      assertEquals(
          "Failed for " + entry.getKey(),
          ticksPerFrame,
          (int) entry.getValue()
      );
    }
  }
}
