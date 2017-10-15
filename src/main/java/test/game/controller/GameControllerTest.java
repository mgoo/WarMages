package test.game.controller;

import static org.junit.Assert.assertEquals;

import main.common.util.Events.GameLost;
import main.common.util.Events.GameWon;
import main.common.GameController;
import main.game.controller.DefaultGameController;
import main.game.model.DefaultGameModel;
import main.common.GameModel;
import main.game.model.world.DefaultWorld;
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

  GameModel gameModel = null;
  GameController controller = null;

  @Test
  public void checkSelectOneUnit() {
    GameWon gc = new GameWon();
    gc.registerListener(parameter -> {});
    GameLost gl = new GameLost();
    gl.registerListener(parameter -> {});
    gameModel = new DefaultGameModel(new DefaultWorld(
        WorldTestUtils.createLevels(WorldTestUtils.createLevelWith(
            WorldTestUtils.createDefaultUnit(new MapPoint(1, 0)),
            WorldTestUtils.createDefaultUnit(new MapPoint(1, 0))
        )),
        WorldTestUtils.createDefaultHeroUnit(new MapPoint(1,0)),
        new DefaultPathFinder()),
        new MainGameTick(), gc, gl);
    controller = new DefaultGameController(gameModel);
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
    assertEquals(1, gameModel.getUnitSelection().size());
  }

  @Test
  public void checkSelectNoUnit() {
    GameWon gc = new GameWon();
    gc.registerListener(parameter -> {});
    GameLost gl = new GameLost();
    gl.registerListener(parameter -> {});
    gameModel = new DefaultGameModel(new DefaultWorld(
        WorldTestUtils.createLevels(WorldTestUtils.createLevelWith(
            WorldTestUtils.createDefaultUnit(new MapPoint(1, 0)),
            WorldTestUtils.createDefaultUnit(new MapPoint(1, 0))
        )),
        WorldTestUtils.createDefaultHeroUnit(new MapPoint(1,0)),
        new DefaultPathFinder()),
        new MainGameTick(), gc, gl);
    controller = new DefaultGameController(gameModel);
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
    assertEquals(0, gameModel.getUnitSelection().size());
  }



}
