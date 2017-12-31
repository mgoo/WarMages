package test.game.controller;

import static org.junit.Assert.assertEquals;

import main.game.controller.DefaultGameController;
import main.game.controller.GameController;
import main.game.model.DefaultGameModel;
import main.game.model.GameModel;
import main.game.model.world.DefaultWorld;
import main.game.model.world.pathfinder.DefaultPathFinder;
import main.menu.controller.events.MouseClick;
import main.util.Events.GameLost;
import main.util.Events.GameWon;
import main.util.Events.MainGameTick;
import main.util.MapPoint;
import org.junit.Test;
import test.game.model.world.WorldTestUtils;

/**
 * Tests for the GameController API.
 *
 * @author Hrshikesh Arora
 * @author Eric Diputado (External Tester)
 */
public class GameControllerTest {

  private GameModel gameModel = null;
  private GameController controller = null;

  @Test
  public void checkSelectOneUnit() {
    GameWon gc = new GameWon();
    GameLost gl = new GameLost();
    gameModel = new DefaultGameModel(
        new DefaultWorld(
          WorldTestUtils.createLevels(
              WorldTestUtils.createLevelWith(
                WorldTestUtils.createDefaultUnit(new MapPoint(3, 0)),
                WorldTestUtils.createDefaultUnit(new MapPoint(2, 0))
              )
          ),
          WorldTestUtils.createDefaultHeroUnit(new MapPoint(1,0)),
          new DefaultPathFinder()
        ),
        new MainGameTick(),
        gc,
        gl
    );
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
    GameLost gl = new GameLost();
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
