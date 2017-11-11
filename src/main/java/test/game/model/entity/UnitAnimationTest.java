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
import main.game.model.entity.unit.UnitAnimation;
import main.game.model.entity.unit.DefaultUnit;
import org.junit.Test;

/**
 * Some tests.
 * @author chongdyla
 */
public class UnitAnimationTest {

  @Test
  public void numberOfTicksShouldBeTheSameForEachFrame() {
    // Given a sequence
    Sequence sequence = Sequence.SHOOT;
    // and a stub unit
    DefaultUnit unit = mock(DefaultUnit.class);
    when(unit.getSpriteSheet()).thenReturn(new StubUnitSpriteSheet());
    when(unit.getCurrentDirection()).thenReturn(Direction.DOWN);
    // and a UnitAnimation
    UnitAnimation unitAnimation = new UnitAnimation(unit, Sequence.SHOOT, 10);

    // when we tick many times (for one whole cycle)
    Map<GameImage, Integer> ticksPerFrameCounts = new HashMap<>();
    while (!unitAnimation.isFinished()) {
      ticksPerFrameCounts.compute(
          unitAnimation.getImage(),
          (image, count) -> count == null ? 1 : count + 1
      );
      unitAnimation.tick();
    }

    // then each image should have been shown for the right number of frames
    for (Entry<GameImage, Integer> entry : ticksPerFrameCounts.entrySet()) {
      assertEquals(
          "Failed for " + entry.getKey(),
          unit.getAttackSpeedModifier() / unit.getSpriteSheet().getImagesForSequence(sequence, Direction.DOWN).size(),
          (int) entry.getValue()
      );
    }
  }
}
