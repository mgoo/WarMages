package test.renderer;

import javafx.scene.image.ImageView;
import main.game.controller.GameController;
import main.game.model.GameModel;
import main.game.view.GameView;
import main.renderer.Renderer;
import org.junit.Test;

/**
 * Created by diputagabr on 27/09/17.
 * Test classes which focuses on testing the Renderer.
 * At the moment it will only mock the view and model to draw it.
 */
public class RendererTest {

  @Test
  public void checkIfRendererDrawsEntities(){
//    Renderer renderer = new Renderer(mockGameView(), mockImageView());
  }

  /**
   * Creates a mock ImageView used for testing.
   * @return mock ImageView
   */
  private ImageView mockImageView() {
    return new ImageView();
  }

  /**
   * Creates a mock GameView used for testing.
   * @return mock GameView
   */
  private GameView mockGameView(GameController gc, GameModel gm) {
    return new GameView(gc, gm);
  }

}
