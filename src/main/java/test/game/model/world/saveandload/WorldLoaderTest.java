package test.game.model.world.saveandload;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import main.common.WorldLoader;
import main.common.entity.MapEntity;
import main.common.util.MapPoint;
import main.common.util.MapRect;
import main.common.util.MapSize;
import main.game.model.entity.DefaultMapEntity;
import main.game.model.world.World;
import main.game.model.world.saveandload.DefaultWorldLoader;
import org.junit.Test;

/**
 * Test names here follow the test naming convention:
 * unitOfWorkUnderTest_typeOfInput_expectedResult.
 * @author chongdyla
 */
public class WorldLoaderTest {

  @Test
  public void load_noInputs_returnsNonNullAndDoesNotCrash() {
    WorldLoader worldLoader = new DefaultWorldLoader();
    World load = worldLoader.load();
    assertNotNull(load);
  }

  @Test
  public void newSingleLevelTest_noInputs_returnsNonNullAndDoesNotCrash() {
    World load = new DefaultWorldLoader().loadSingleLevelTestWorld();
    assertNotNull(load);
  }

  @Test
  public void loadMultilevelWorld_noInputs_returnsNonNullAndDoesNotCrash() {
    World load = new DefaultWorldLoader().loadMultilevelWorld();
    assertNotNull(load);
  }

  // TODO test bounds generators soz dylan deleted more of your tests

}
