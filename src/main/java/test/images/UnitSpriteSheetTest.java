package test.images;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import main.game.model.entity.Direction;
import main.images.DefaultImageProvider;
import main.images.DefaultUnitSpriteSheet;
import main.images.GameImage;
import main.images.GameImageResource;
import main.images.UnitSpriteSheet;
import main.images.UnitSpriteSheet.Sequence;
import org.junit.Test;

/**
 * Test names here follow the test naming convention:
 * unitOfWorkUnderTest_typeOfInput_expectedResult.
 *
 * @author chongdyla
 */
public class UnitSpriteSheetTest {

  /**
   * This test mainly checks that the loading doesn't cause a crash (which can happen if
   * a sprite sheet is too small).
   */
  @Test
  public void loading_forAllSequencesAndDirections_shouldBeCorrectSize() throws IOException {
    UnitSpriteSheet unitSpriteSheet =
        new DefaultUnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET);
    DefaultImageProvider imageProvider = new DefaultImageProvider();

    for (Sequence sequence : Sequence.values()) {
      for (Direction direction : Direction.values()) {
        List<GameImage> gameImagesForSequence = unitSpriteSheet
            .getImagesForSequence(sequence, direction);

        for (GameImage gameImage : gameImagesForSequence) {
          BufferedImage image = gameImage.load(imageProvider);
          assertEquals(Sequence.UNIT_WIDTH, image.getWidth());
          assertEquals(Sequence.UNIT_HEIGHT, image.getHeight());
        }

      }
    }
  }
}
