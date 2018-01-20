package test.game.view;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import main.game.model.entity.Unit;
import main.game.model.entity.unit.DefaultUnit;
import main.game.view.UnitView;
import main.images.GameImageResource;
import main.util.Event;
import main.util.MapPoint;
import main.util.MapSize;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by mgoo on 15/10/17.
 */
public class UnitViewTest extends GameViewTest {

  @Override
  @Before
  public void setUp() {
    super.setUp();
  }

  @Test
  public void test_unitsCreateUnitView() {
    Unit unit = mock(DefaultUnit.class);
    when(unit.getCentre()).thenReturn(new MapPoint(0, 0));
    when(unit.getSize()).thenReturn(new MapSize(0.5, 0.5));
    when(unit.getDamagedEvent()).thenReturn(new Event<>());
    when(unit.getRemovedEvent()).thenReturn(new Event<>());
    when(unit.getImage()).thenReturn(GameImageResource.TEST_IMAGE_1_1.getGameImage());
    when(unit.accept(any(), any())).thenCallRealMethod();

    when(world.recieveRecentlyAddedEntities()).thenReturn(
        Collections.singletonList(unit)
    );
    when(gameModel.getWorld()).thenReturn(world);
    this.gameView.onTick(0L);

    assertTrue(this.gameView.getRenderables(0L).get(0) instanceof UnitView);
  }
}
