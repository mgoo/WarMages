package test.menu;

import static org.junit.Assert.assertTrue;
import static test.renderer.RendererTestUtils.createConfig;
import static test.renderer.RendererTestUtils.createGameController;
import static test.renderer.RendererTestUtils.createGameView;
import static test.renderer.RendererTestUtils.createImageView;

import java.awt.Color;
import java.awt.Desktop;
import java.util.Arrays;
import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import main.game.model.GameModel;
import main.game.model.Level;
import main.game.model.entity.HeroUnit;
import main.game.view.GameView;
import main.menu.MainMenu;
import main.menu.Menu;
import main.renderer.Renderer;
import main.util.Config;
import main.util.Events.MainGameTick;
import main.util.MapPoint;
import netscape.javascript.JSObject;
import org.junit.Test;
import test.game.model.world.WorldTestUtils;
import test.renderer.RendererTestUtils;

/**
 * This contains visual tests.
 * @author Andrew McGhie
 */
public class AllJfxTests {

  static Level level = null;
  static HeroUnit hero = null;


  @Test
  public void testMainMenu() {
    if (!Desktop.isDesktopSupported()) {
      return;
    }
    TestApplication.show(new MainMenu(null, null, null, null, null));
    assertTrue(true);
  }

  /**
   * Creates the gameviews and imageviews based on a level and hero unit.
   */
  public static Image testImage() {
    hero = WorldTestUtils.createHeroUnit(new MapPoint(0, 0));
    level = WorldTestUtils.createLevelWith(
        WorldTestUtils.createUnit(new MapPoint(1, 0)),
        WorldTestUtils.createUnit(new MapPoint(2, 0)),
        WorldTestUtils.createUnit(new MapPoint(3, 0)));
    GameModel model = RendererTestUtils.createGameModel(WorldTestUtils
        .createWorld(
            WorldTestUtils.createLevels(level),
            hero
        ), new MainGameTick());
    Config config = createConfig();
    GameView gv = createGameView(config, createGameController(model), model);
    ImageView iv = createImageView(config);
    new Renderer(gv, iv).drawAll(config.getGameModelDelay(), gv, iv);
    return iv.getImage();
  }

  /**
   * A mock application class.
   */
  public static class TestApplication extends Application {

    private static Menu currentMenu;

    public static void show(Menu menu) {
      currentMenu = menu;
      launch(new String[0]);
    }

    @Override
    public void start(Stage primaryStage) {
      final Scene scene = new Scene(new Group());
      final StackPane root = new StackPane();
      final WebView browser = new WebView();
      final ImageView imageView = new ImageView();

      WebEngine webEngine = browser.getEngine();

      webEngine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
        if (newState != Worker.State.SUCCEEDED) {
          return;
        }
        com.sun.javafx.webkit.Accessor.getPageFor(webEngine)
            .setBackgroundColor(new Color(0, 0, 0, 0).getRGB());

        JSObject window = (JSObject) webEngine.executeScript("window");
        window.setMember("controller", currentMenu.getMenuController());
        Arrays.stream(currentMenu.getScripts()).forEach(script -> {
          webEngine.executeScript(script);
        });
      });

      webEngine.setUserStyleSheetLocation(currentMenu.getStyleSheetLocation());
      webEngine.loadContent(currentMenu.getHtml());

      imageView.setImage(testImage());

      root.getChildren().setAll(imageView, browser);
      scene.setRoot(root);
      primaryStage.setScene(scene);
      primaryStage.show();
    }
  }
}
