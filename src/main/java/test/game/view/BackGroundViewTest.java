package test.game.view;

import static org.junit.Assert.assertEquals;

import main.common.GameModel;
import main.common.Renderable;
import main.game.view.BackGroundView;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the background view class.
 *
 * @author Andrew Mcghie
 */
public class BackGroundViewTest extends GameViewTest {

  @Override
  @Before
  public void setUp() {
    super.setUp();
  }

  @Test
  public void testBackgroundView_move() {
    this.gameView.onTick(0L);

    BackGroundView bg = gameView.getBackGroundView();

    // Test initial position
    assertEquals(0, bg.getImagePosition(0).x, 0.001);
    assertEquals(0, bg.getImagePosition(0).y, 0.001);
    assertEquals(config.getContextScreenWidth() * 2, bg.getImageSize().width, 0.001);
    assertEquals(config.getContextScreenHeight() * 2, bg.getImageSize().height, 0.001);

    gameView.updateMousePosition(0 ,0);
    gameView.onTick(GameModel.DELAY);

    // Test Up to the left movement
    assertEquals(-config.getContextScreenWidth() + config.getGameViewScrollSpeed(),
        bg.getImagePosition(0).x, 0.001);
    assertEquals(-config.getContextScreenHeight() + config.getGameViewScrollSpeed(),
        bg.getImagePosition(0).y, 0.001);

    gameView.updateMousePosition(config.getContextScreenWidth() ,config.getContextScreenHeight());
    gameView.onTick(GameModel.DELAY * 2);
    gameView.onTick(GameModel.DELAY * 3);

    // Test Down to the right movement
    assertEquals(-config.getGameViewScrollSpeed(), bg.getImagePosition(0).x, 0.001);
    assertEquals(-config.getGameViewScrollSpeed(), bg.getImagePosition(0).y, 0.001);

  }

}
