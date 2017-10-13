package test.game.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import main.common.GameController;
import main.game.controller.DefaultGameController;
import main.game.model.DefaultGameModel;
import main.game.model.GameModel;
import main.game.model.world.World;
import main.game.model.world.pathfinder.DefaultPathFinder;
import main.game.view.events.MouseClick;
import main.common.util.Events.MainGameTick;
import main.common.util.MapPoint;
import org.junit.Test;
import test.game.model.world.WorldTestUtils;

/**
 * Tests for the GameController API.
 *
 * @author Hrshikesh Arora
 * @author Eric Diputado (External Tester)
 */
public class GameControllerTest {

  GameModel model = null;
  GameController controller = null;

  @Test
  public void checkSelectOneUnit() {
    model = new DefaultGameModel(new World(
        WorldTestUtils.createLevels(WorldTestUtils.createLevelWith(
            WorldTestUtils.createDefaultUnit(new MapPoint(1, 0)),
            WorldTestUtils.createDefaultUnit(new MapPoint(1, 0))
        )),
        WorldTestUtils.createDefaultHeroUnit(new MapPoint(1,0)),
        new DefaultPathFinder()),
        new MainGameTick());
    controller = new DefaultGameController(model);
    controller.onMouseEvent(new MouseClick() {
      @Override
      public boolean wasLeft() {
        return true;
      }

      @Override
      public boolean wasShiftDown() {
        return false;
      }

      @Override
      public boolean wasCtrlDown() {
        return false;
      }

      @Override
      public MapPoint getLocation() {
        return new MapPoint(1, 0);
      }
    });
    assertEquals(1, model.getUnitSelection().size());
  }

  @Test
  public void checkSelectNoUnit() {
    model = new DefaultGameModel(new World(
        WorldTestUtils.createLevels(WorldTestUtils.createLevelWith(
            WorldTestUtils.createDefaultUnit(new MapPoint(1, 0)),
            WorldTestUtils.createDefaultUnit(new MapPoint(1, 0))
        )),
        WorldTestUtils.createDefaultHeroUnit(new MapPoint(1,0)),
        new DefaultPathFinder()),
        new MainGameTick());
    controller = new DefaultGameController(model);
    controller.onMouseEvent(new MouseClick() {
      @Override
      public boolean wasLeft() {
        return true;
      }

      @Override
      public boolean wasShiftDown() {
        return false;
      }

      @Override
      public boolean wasCtrlDown() {
        return false;
      }

      @Override
      public MapPoint getLocation() {
        return new MapPoint(20, 0);
      }
    });
    assertEquals(0, model.getUnitSelection().size());
  }



}
