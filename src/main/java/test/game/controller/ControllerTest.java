package test.game.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import main.common.util.Events.GameLost;
import main.common.util.Events.GameWon;
import main.game.controller.GameController;
import main.game.model.GameModel;
import main.game.model.world.World;
import main.game.model.world.pathfinder.DefaultPathFinder;
import main.game.view.events.MouseClick;
import main.common.util.Events.MainGameTick;
import main.common.util.MapPoint;
import org.junit.Test;
import test.game.model.world.WorldTestUtils;

public class ControllerTest {

  GameModel gameModel = null;
  GameController controller = null;

  @Test
  public void checkSelectOneUnit() {
    GameWon gc = new GameWon();
    gc.registerListener(parameter -> {});
    GameLost gl = new GameLost();
    gl.registerListener(parameter -> {});
    gameModel = new GameModel(new World(
        WorldTestUtils.createLevels(WorldTestUtils.createLevelWith(
            WorldTestUtils.createUnit(new MapPoint(1, 0)),
            WorldTestUtils.createUnit(new MapPoint(1, 0))
        )),
        WorldTestUtils.createHeroUnit(new MapPoint(1,0)),
        new DefaultPathFinder()),
        new MainGameTick(), gc, gl);
    controller = new GameController(gameModel);
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
    assertEquals(1,gameModel.getUnitSelection().size());
  }

  @Test
  public void checkSelectNoUnit() {
    GameWon gc = new GameWon();
    gc.registerListener(parameter -> {});
    GameLost gl = new GameLost();
    gl.registerListener(parameter -> {});
    gameModel = new GameModel(new World(
        WorldTestUtils.createLevels(WorldTestUtils.createLevelWith(
            WorldTestUtils.createUnit(new MapPoint(1, 0)),
            WorldTestUtils.createUnit(new MapPoint(1, 0))
        )),
        WorldTestUtils.createHeroUnit(new MapPoint(1,0)),
        new DefaultPathFinder()),
        new MainGameTick(), gc, gl);
    controller = new GameController(gameModel);
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
    assertEquals(0,gameModel.getUnitSelection().size());
  }



}
