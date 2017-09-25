package test.game.model.world.saveandload;

import static org.junit.Assert.assertNotNull;

import main.game.model.world.saveandload.WorldLoader;
import main.game.model.world.World;
import org.junit.Test;

/**
 * Test names here follow the test naming convention:
 * unitOfWorkUnderTest_typeOfInput_expectedResult.
 */
public class WorldLoaderTest {

  @Test
  public void load_noInputs_returnsNonNullAndDoesNotCrash() {
    WorldLoader worldLoader = new WorldLoader();
    World load = worldLoader.load();
    assertNotNull(load);
  }

  @Test
  public void newSingleLevelTest_noInputs_returnsNonNullAndDoesNotCrash() {
    World load = WorldLoader.newSingleLevelTestWorld();
    assertNotNull(load);
  }

}
