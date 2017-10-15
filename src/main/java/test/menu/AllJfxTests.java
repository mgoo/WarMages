package test.menu;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static test.renderer.RendererTestUtils.createConfig;
import static test.renderer.RendererTestUtils.createGameController;
import static test.renderer.RendererTestUtils.createGameView;
import static test.renderer.RendererTestUtils.createImageView;

import java.awt.Color;
import java.awt.Desktop;
import java.util.Arrays;
import java.util.Collections;
import javafx.animation.PauseTransition;
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
import javafx.util.Duration;
import main.Main;
import main.common.WorldLoader;
import main.common.WorldSaveModel;
import main.common.entity.HeroUnit;
import main.common.entity.Team;
import main.common.images.GameImageResource;
import main.common.util.Config;
import main.common.util.Events.MainGameTick;
import main.common.util.Looper;
import main.common.util.MapPoint;
import main.common.GameModel;
import main.common.util.MapSize;
import main.game.model.Level;
import main.game.model.entity.unit.DefaultHeroUnit;
import main.game.model.entity.unit.DefaultUnit;
import main.game.model.entity.unit.UnitType;
import main.game.view.GameView;
import main.images.DefaultUnitSpriteSheet;
import main.menu.MainMenu;
import main.menu.Menu;
import main.renderer.Renderer;
import netscape.javascript.JSObject;
import org.junit.Test;
import test.game.model.world.WorldTestUtils;
import test.renderer.RendererTestUtils;

/**
 * This contains visual tests.
 * <p>
 * NOTE TO TUTORS: This requires classes from multiple APIs, e.g. the
 * {@link DefaultUnitSpriteSheet} from the images API and the {@link Renderer} from the Renderer API
 * in order for this test to display things properly. It does not make sense to test a single API
 * in isolation here.
 * </p>
 * <p>
 * Also JavaFx only allows one {@link Application} to be instantiated in a single run of the
 * program, so only one JavaFx test can be used.
 * </p>
 * @author Andrew McGhie
 * @author Dylan Chong (fixing a bug)
 * @author Eric Diputado (Renderer tests)
 */
public class AllJfxTests {

  private static Level level = null;
  private static HeroUnit hero = null;

  private static DefaultUnit createDefaultUnit(MapPoint point) {
    return new DefaultUnit(
        point,
        new MapSize(1, 1),
        Team.ENEMY,
        new DefaultUnitSpriteSheet(GameImageResource.ORC_SPEARMAN_SPRITE_SHEET),
        UnitType.SPEARMAN
    );
  }

  @Test
  public void testMainMenu() {
    if (!Desktop.isDesktopSupported()) {
      return;
    }
    WorldSaveModel worldSaveModel = mock(WorldSaveModel.class);
    when(worldSaveModel.getExistingGameSaves()).thenReturn(Collections.emptyList());
    TestApplication.show(new MainMenu(
        mock(Main.class),
        mock(WorldLoader.class),
        worldSaveModel,
        mock(ImageView.class),
        mock(Config.class)
    ));
    assertTrue(true);
  }

  /**
   * Tests whether gameView, imageView and Renderer can work together to draw.
   *
   * @author Eric Diputado
   */
  public static Image testImage() {
    hero = new DefaultHeroUnit(
        new MapPoint(0, 0),
        new MapSize(1, 1),
        new DefaultUnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.SWORDSMAN,
        Collections.emptyList(),
        0
    );
    level = WorldTestUtils.createLevelWith(
        createDefaultUnit(new MapPoint(1, 0)),
        createDefaultUnit(new MapPoint(2, 0)),
        createDefaultUnit(new MapPoint(3, 0))
    );
    GameModel model = RendererTestUtils.createGameModel(WorldTestUtils
        .createWorld(
            WorldTestUtils.createLevels(level),
            hero
        ), new MainGameTick());
    Config config = createConfig();
    GameView gv = createGameView(config, createGameController(model), model);
    ImageView iv = createImageView(config);
    new Renderer(gv, iv, config, mock(Looper.class)).drawAll(config.getGameModelDelay(), gv, iv);
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
      PauseTransition delay = new PauseTransition(Duration.seconds(3));
      delay.setOnFinished(event -> primaryStage.close());
      delay.play();

    }
  }
}
