package test.game.model.world.saveandload;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import main.game.model.entity.MapEntity;
import main.game.model.entity.UninteractableEntity;
import main.game.model.world.saveandload.WorldLoader;
import main.game.model.world.World;
import main.util.MapPoint;
import main.util.MapRect;
import main.util.MapSize;
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
    World load = new WorldLoader().loadSingleLevelTestWorld();
    assertNotNull(load);
  }

  @Test
  public void loadMultilevelWorld_noInputs_returnsNonNullAndDoesNotCrash() {
    World load = new WorldLoader().loadMultilevelWorld();
    assertNotNull(load);
  }

  @Test
  public void generateBoundEntities_3x4Bounds_borderGeneratedProperly() {
    // The method should generate the edges as well, but only top-left and bottom-right corners
    // are checked.

    MapRect bounds = new MapRect(new MapPoint(1, 2), new MapSize(3, 4));
    Collection<UninteractableEntity> boundEntities = WorldLoader.generateBorderEntities(
        bounds,
        WorldLoader::newBorderEntityAt
    );

    assertEquals(boundEntities.size(), 10); // 10 edge squares inside a 3x4 grid
    assertTrue(boundEntities.stream().anyMatch(
        entity -> entity.getTopLeft().equals(bounds.topLeft)
    ));
    assertTrue(boundEntities.stream().anyMatch(
        entity -> entity.getTopLeft().equals(new MapPoint(
            bounds.bottomRight.x - 1,
            bounds.bottomRight.y - 1
        ))
    ));
  }

}
