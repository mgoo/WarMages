package test.game.view;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import main.game.controller.GameController;
import main.game.model.DefaultGameModel;
import main.game.model.GameModel;
import main.game.model.entity.Entity;
import main.game.model.entity.unit.DefaultHeroUnit;
import main.game.model.world.DefaultWorld;
import main.game.model.world.World;
import main.game.view.GameView;
import main.images.GameImage;
import main.images.ImageProvider;
import main.util.Config;
import main.util.MapPoint;
import main.util.MapRect;
import org.junit.Before;
import org.junit.Test;


/**
 * tests for the DefaultGameView class.
 *
 * <p>checks position, initial load and image size.</p>
 *
 * @author Andrew McGhie
 */
public class GameViewTest {

  GameView gameView;
  GameModel gameModel;
  GameController gameController;
  Config config;
  World world;

  /**
   * sets up the class variables that are used in every test.
   */
  @Before
  public void setUp() {
    final ImageProvider imageProvider = new ImageProvider() {
      @Override
      protected BufferedImage load(String filePath) throws IOException {
        return new BufferedImage(1,1, BufferedImage.TYPE_4BYTE_ABGR);
      }

      @Override
      protected void storeInCache(GameImage gameImage, BufferedImage image) {

      }

      @Override
      protected BufferedImage getFromCache(GameImage gameImage) {
        return new BufferedImage(1,1, BufferedImage.TYPE_4BYTE_ABGR);
      }
    };
    gameController = mock(GameController.class);
    this.gameModel = mock(DefaultGameModel.class);
    world = mock(DefaultWorld.class);
    when(this.gameModel.getWorld()).thenReturn(world);
    DefaultHeroUnit hero = mock(DefaultHeroUnit.class);
    when(hero.getCentre()).thenReturn(new MapPoint(1, 1));
    when(world.getHeroUnit()).thenReturn(hero);

    this.config = new Config();
    this.config.setScreenDim(1000, 1000);
    this.config.setEntityViewTilePixelsX(50);
    this.config.setEntityViewTilePixelsY(50);
    when(world.getCurrentLevelBounds()).thenReturn(new MapRect(-100, -100, 200, 200));
    this.gameView = new GameView(
        config,
        gameController,
        gameModel,
        imageProvider,
        world
    );
  }

  @Test
  public void testMovingViewBox() {
    MapRect originalView = this.gameView.getViewBox();
    this.gameView.updateMousePosition(0, 0);
    assertEquals(originalView.topLeft.x + config.getGameViewScrollSpeed(),
        this.gameView.getViewBox().topLeft.x + config.getGameViewScrollSpeed());
    assertEquals(originalView.topLeft.y + config.getGameViewScrollSpeed(),
        this.gameView.getViewBox().topLeft.y + config.getGameViewScrollSpeed());
    assertEquals(originalView.bottomRight.x + config.getGameViewScrollSpeed(),
        this.gameView.getViewBox().bottomRight.x + config.getGameViewScrollSpeed());
    assertEquals(originalView.bottomRight.y + config.getGameViewScrollSpeed(),
        this.gameView.getViewBox().bottomRight.y + config.getGameViewScrollSpeed());
  }

  @Test
  public void test_eventPassThroughs() {
    this.gameView.onLeftClick(0, 0, true, true);
    verify(this.gameController).onMouseEvent(any());
    this.gameView.onRightClick(0 ,0,false, false);
    verify(this.gameController, times(2)).onMouseEvent(any());
    this.gameView.onDbClick(0 , 0, true, true);
    verify(this.gameController).onDbClick(any());
    this.gameView.onDrag(0, 0, 5, 5, true, false);
    verify(this.gameController).onMouseDrag(any());
    this.gameView.onKeyDown('z', true, false);
    verify(this.gameController).onKeyPress(any());

    this.gameView.unitClick(null, true, true, true);
    verify(this.gameController).onUnitIconClick(any());
    this.gameView.abilityClick(null,false, false, true);
    verify(this.gameController).onAbilityIconClick(any());
    this.gameView.itemClick(null, true, true, true);
    verify(this.gameController).onItemIconClick(any());
  }
}
