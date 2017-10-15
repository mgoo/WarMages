package test.game.view;

import static org.junit.Assert.assertTrue;

import java.util.Collections;
import main.common.util.MapPoint;
import main.common.util.MapSize;
import main.game.view.UnitView;
import org.junit.Before;
import org.junit.Test;
import test.game.view.FogOfWarViewTest.UnitMock;

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
    this.gameModelMock.setEntities(
        Collections.singletonList(new UnitMock(new MapPoint(0, 0), new MapSize(0.5, 0.5))
    ));
    this.gameView.onTick(0L);

    assertTrue(this.gameView.getRenderables(0L).get(0) instanceof UnitView);
  }
}
