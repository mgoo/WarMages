package test.game.view;

import static org.junit.Assert.assertEquals;

import main.game.model.GameModel;
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
    int startX = -(config.getContextScreenWidth() - (int)-gameView.getViewBox().x());
    int startY = -(config.getContextScreenHeight() - (int)-gameView.getViewBox().y());

    // Test initial position
    assertEquals(startX, bg.getImagePosition().x, 0.001);
    assertEquals(startY, bg.getImagePosition().y, 0.001);
    assertEquals(config.getContextScreenWidth() * 2, bg.getImageSize().width, 0.001);
    assertEquals(config.getContextScreenHeight() * 2, bg.getImageSize().height, 0.001);

    gameView.updateMousePosition(0 ,0);
    gameView.onTick(GameModel.DELAY);

    // Test Up to the left movement
    assertEquals(startX + config.getGameViewScrollSpeed(),
        bg.getImagePosition().x, 0.001);
    assertEquals(startY + config.getGameViewScrollSpeed(),
        bg.getImagePosition().y, 0.001);

    gameView.updateMousePosition(config.getContextScreenWidth() ,config.getContextScreenHeight());
    gameView.onTick(GameModel.DELAY * 2);
    gameView.onTick(GameModel.DELAY * 3);

    // Test Down to the right movement
    assertEquals(startX - config.getGameViewScrollSpeed(), bg.getImagePosition().x, 0.001);
    assertEquals(startY - config.getGameViewScrollSpeed(), bg.getImagePosition().y, 0.001);

  }

}
