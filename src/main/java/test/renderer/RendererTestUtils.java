package test.renderer;

import javafx.scene.image.ImageView;
import main.common.util.Events.GameLost;
import main.common.util.Events.GameWon;
import main.common.GameController;
import main.game.model.GameModel;
import main.game.model.world.World;
import main.game.view.GameView;
import main.game.view.events.AbilityIconClick;
import main.game.view.events.ItemIconClick;
import main.game.view.events.KeyEvent;
import main.game.view.events.MouseClick;
import main.game.view.events.MouseDrag;
import main.game.view.events.UnitIconClick;
import main.images.DefaultImageProvider;
import main.common.util.Config;
import main.common.util.Event;
import main.common.util.Events.MainGameTick;

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
   * Creates a model.
   */
  public static GameModel createGameModel(World world, MainGameTick mainGameTick) {
    GameWon gc = new GameWon();
    gc.registerListener(parameter -> {});
    GameLost gl = new GameLost();
    gl.registerListener(parameter -> {});
    return new GameModel(world, mainGameTick, gc, gl);
  }

  /**
   * Creates a controller (note: returns an empty GameController and the methods do nothing).
   *
   * @return a controller
   */
  public static GameController createGameController(GameModel model) {
    return new GameController() {
      @Override
      public void onKeyPress(KeyEvent keyevent) {

      }

      @Override
      public void onMouseEvent(MouseClick mouseEvent) {

      }

      @Override
      public void onMouseDrag(MouseDrag mouseEvent) {

      }

      @Override
      public void onDbClick(MouseClick mouseClick) {

      }

      @Override
      public void onUnitIconClick(UnitIconClick clickEvent) {

      }

      @Override
      public void onAbilityIconClick(AbilityIconClick clickEvent) {

      }

      @Override
      public void onItemIconClick(ItemIconClick clickEvent) {

      }
    };
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
    GameView gv = new GameView(c, gc, gm, new DefaultImageProvider(), new Event<>(), null);
    gv.updateRenderables(c.getGameModelDelay());
    return gv;
  }
}
