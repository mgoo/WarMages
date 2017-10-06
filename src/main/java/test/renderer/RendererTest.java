package test.renderer;

import static javafx.application.Application.launch;
import static junit.framework.TestCase.fail;
import static test.renderer.RendererTestUtils.createConfig;
import static test.renderer.RendererTestUtils.createGameController;
import static test.renderer.RendererTestUtils.createGameView;
import static test.renderer.RendererTestUtils.createImageView;

import java.awt.Desktop;
import java.util.List;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.game.model.GameModel;
import main.game.model.Level;
import main.game.model.entity.HeroUnit;
import main.game.view.GameView;
import main.renderer.Renderable;
import main.renderer.Renderer;
import main.util.Config;
import main.util.Events.MainGameTick;
import main.util.MapPoint;
import org.junit.Before;
import org.junit.Test;
import test.game.model.world.WorldTestUtils;

/**
 * Created by diputagabr on 27/09/17. Test classes which focuses on testing the Renderer. At the
 * moment it will only mock the view and model to draw it.
 */
public class RendererTest {

  static Level level = null;
  static HeroUnit hero = null;

  @Before
  public void init() {
    level = null;
  }

  @Test
  public void checkIfRendererDrawsEntities() {
    if (!Desktop.isDesktopSupported()) {
      return;
    }
    hero = WorldTestUtils.createHeroUnit(new MapPoint(0, 0));
    level = WorldTestUtils.createLevelWith(
        WorldTestUtils.createUnit(new MapPoint(1, 0)),
        WorldTestUtils.createUnit(new MapPoint(2, 0)),
        WorldTestUtils.createUnit(new MapPoint(3, 0))
    );
    TestApplication.main(new String[0]);
  }


  /**
   * Creates the gameviews and imageviews based on a level and hero unit
   */
  public static Image testImage() {
    GameModel model = RendererTestUtils.createGameModel(WorldTestUtils
        .createWorld(
            WorldTestUtils.createLevels(level),
            hero
        ), new MainGameTick());
    Config config = createConfig();
    GameView gv = createGameView(config, createGameController(model), model);
    ImageView iv = createImageView(config);
//    Renderer.drawAll(config.getGameModelDelay(), gv, iv);
    return iv.getImage();
  }


  /**
   * A mock application class
   */
  public static class TestApplication extends Application {

    @Override
    public void start(Stage stage) {
      final Image image = testImage();
      final Canvas canvas = new Canvas(image.getWidth(), image.getHeight());
      final GraphicsContext gc = canvas.getGraphicsContext2D();
      gc.drawImage(image, 0, 0);
      Group group = new Group(canvas);
      stage.setScene(new Scene(group));
      stage.show();
      PauseTransition delay = new PauseTransition(Duration.seconds(3));
      delay.setOnFinished(vent -> stage.close());
      delay.play();
    }

    public static void main(String[] args) {
      launch(args);
    }
  }
}
