package test.renderer;

import javafx.scene.image.ImageView;
import main.game.controller.GameController;
import main.game.model.GameModel;
import main.game.model.world.World;
import main.game.view.GameView;
import main.images.DefaultImageProvider;
import main.util.Config;
import main.util.Events.MainGameTick;

public class RendererTestUtils {

  /**
   * Creates config and sets its width/height.
   * @return a config with a set width/height
   */
  public static Config createConfig() {
    Config c = new Config();
    c.setScreenDim(1000,1000);
    return c;
  }

  /**
   * Creates a model
   */
  public static GameModel createGameModel(World world, MainGameTick mainGameTick) {
    return new GameModel(world, mainGameTick);
  }

  /**
   * Creates a controller.
   *
   * @return a controller
   */
  public static GameController createGameController(GameModel model) {
    return new GameController(model);
  }

  /**
   * Creates a mock ImageView used for testing.
   *
   * @return mock ImageView
   */
  public static ImageView createImageView(Config c) {
    ImageView iv = new ImageView();
    iv.setFitHeight(c.getContextScreenHeight());
    iv.setFitWidth(c.getContextScreenWidth());
    return iv;
  }

  /**
   * Creates a mock GameView used for testing.
   *
   * @return mock GameView
   */
  public static GameView createGameView(Config c, GameController gc, GameModel gm) {
    GameView gv = new GameView(c, gc, gm, new DefaultImageProvider());
    gv.updateRenderables(c.getGameModelDelay());
    return gv;
  }
}
