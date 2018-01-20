package test.renderer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javafx.scene.image.ImageView;
import main.game.controller.GameController;
import main.game.model.DefaultGameModel;
import main.game.model.GameModel;
import main.game.model.entity.HeroUnit;
import main.game.model.world.World;
import main.game.view.GameView;
import main.images.DefaultImageProvider;
import main.menu.controller.events.AbilityIconClick;
import main.menu.controller.events.ItemIconClick;
import main.menu.controller.events.KeyEvent;
import main.menu.controller.events.MouseClick;
import main.menu.controller.events.MouseDrag;
import main.menu.controller.events.UnitIconClick;
import main.util.Config;
import main.util.Events.GameLost;
import main.util.Events.GameWon;
import main.util.Events.MainGameTick;
import main.util.MapPoint;

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
    GameLost gl = new GameLost();
    return new DefaultGameModel(world, mainGameTick, gc, gl);
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
   * Creates a mock DefaultGameView used for testing.
   *
   * @return mock DefaultGameView
   */
  public static GameView createGameView(Config c, GameController gc, GameModel gm) {
    World world = mock(World.class);
    HeroUnit hero = mock(HeroUnit.class);
    when(hero.getCentre()).thenReturn(new MapPoint(20, 0));
    when(world.getHeroUnit()).thenReturn(hero);
    GameView gv = new GameView(c, gc, gm, new DefaultImageProvider(), world);
    gv.onTick((long)c.getGameModelDelay());
    return gv;
  }
}
