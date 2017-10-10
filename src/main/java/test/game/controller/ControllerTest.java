package test.game.controller;

import static org.junit.Assert.assertEquals;

import main.common.util.Events.MainGameTick;
import main.common.util.MapPoint;
import main.game.controller.GameController;
import main.game.model.GameModel;
import main.game.model.world.World;
import main.game.view.events.MouseClick;
import org.junit.Test;
import test.common.util.StubLooper;
import test.game.model.world.WorldTestUtils;

public class ControllerTest {

  GameModel gameModel = null;
  GameController controller = null;

  @Test
  public void checkSelectOneUnit() {
    gameModel = new GameModel(
        new World(
            WorldTestUtils.createLevels(WorldTestUtils.createLevelWith(
                WorldTestUtils.createUnit(new MapPoint(1, 0)),
                WorldTestUtils.createUnit(new MapPoint(1, 0))
            )),
            WorldTestUtils.createHeroUnit(new MapPoint(1,0))
        ),
        new MainGameTick(),
        StubLooper::new
    );
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
    gameModel = new GameModel(
        new World(
            WorldTestUtils.createLevels(WorldTestUtils.createLevelWith(
                WorldTestUtils.createUnit(new MapPoint(1, 0)),
                WorldTestUtils.createUnit(new MapPoint(1, 0))
            )),
            WorldTestUtils.createHeroUnit(new MapPoint(1, 0))
        ),
        new MainGameTick(),
        StubLooper::new
    );
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
