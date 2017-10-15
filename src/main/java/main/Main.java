package main;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.io.File;
import java.util.Arrays;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import main.common.util.Config;
import main.common.util.Event;
import main.game.model.world.saveandload.DefaultWorldLoader;
import main.game.model.world.saveandload.DefaultWorldSaveModel;
import main.game.model.world.saveandload.DefaultWorldSaveModel.DefaultFilesystem;
import main.game.view.events.MouseClick;
import main.menu.MainMenu;
import main.menu.Menu;
import netscape.javascript.JSObject;

/**
 * Should just instantiate the needed classes to run the app. This should not contain any app
 * logic.
 */
public class Main extends Application {
  private static boolean isDebugging = false;

  /**
   * Start the app.
   */
  public static void main(String[] args) {
    System.setProperty("sun.java2d.opengl", "true");
    System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
    launch(args);
  }

  private Robot robot = new Robot();
  private WebEngine webEngine;
  private Menu currentMenu;

  private final Event<MouseClick> mouseClickEvent = new Event<>();

  public Scene scene;

  public Main() throws AWTException {
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    int size = 0;
    for (Screen screen : Screen.getScreens()) {
      javafx.geometry.Rectangle2D bounds = screen.getBounds();
      int newSize = (int) (bounds.getHeight() + bounds.getWidth());
      if (newSize <= size) {
        continue;
      }
      primaryStage.setX(screen.getBounds().getMinX() + 1);
      primaryStage.setY(screen.getBounds().getMinY() + 1);
      primaryStage.setWidth(screen.getBounds().getWidth());
      primaryStage.setHeight(screen.getBounds().getHeight());
      Main.this.robot.mouseMove(
          (int) (bounds.getMinX() + bounds.getWidth() / 2),
          (int) (bounds.getMinY() + bounds.getHeight() / 2)
      );
      size = newSize;
    }

    primaryStage.setTitle("Marco The Mage");
    primaryStage.setFullScreen(true);
    primaryStage.setFullScreenExitHint("");
    primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    primaryStage.setAlwaysOnTop(true);

    primaryStage.initStyle(StageStyle.TRANSPARENT);

    this.scene = new Scene(new Group());
    final StackPane root = new StackPane();
    final WebView browser = new WebView();
    final ImageView imageView = new ImageView();
    final Config config = new Config();
    if (getParameters().getUnnamed().contains("--debug")) {
      config.enableDebugMode();
    }
    config.setScreenDim((int) primaryStage.getWidth(), (int) primaryStage.getHeight());
    final MainMenu mainMenu = new MainMenu(
        this,
        new DefaultWorldLoader(),
        new DefaultWorldSaveModel(new DefaultFilesystem()),
        imageView,
        config
    );

    root.setPrefWidth(config.getContextScreenWidth());
    root.setPrefHeight(config.getContextScreenHeight());

    browser.setPrefHeight(config.getContextScreenWidth());
    browser.setPrefHeight(config.getContextScreenHeight());

    imageView.setFitWidth(config.getContextScreenWidth());
    imageView.setFitHeight(config.getContextScreenHeight());

    this.webEngine = browser.getEngine();

    webEngine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
      if (newState != Worker.State.SUCCEEDED) {
        return;
      }
      com.sun.javafx.webkit.Accessor.getPageFor(webEngine)
          .setBackgroundColor(new Color(0, 0, 0, 0).getRGB());

      JSObject window = (JSObject) webEngine.executeScript("window");
      window.setMember("controller", this.currentMenu.getMenuController());
      Arrays.stream(this.currentMenu.getScripts()).forEach(script -> {
        webEngine.executeScript(script);
      });
    });
    webEngine.setOnAlert((WebEvent<String> event) -> {
      System.out.println("JS Alert: " + event.getData());
    });

    this.loadMenu(mainMenu);

    imageView.setFitWidth(scene.getWidth());
    imageView.setFitHeight(scene.getHeight());

    // Mouse events will be handled in html
    root.setOnKeyPressed(event -> {
      this.currentMenu.getMenuController().onKeyDown(event);
    });

    browser.setOnMouseExited(event -> keepMouseInWindow());
    browser.setOnMouseMoved(event -> {
      this.currentMenu.getMenuController().onMouseMove(event);
    });

    primaryStage.setOnCloseRequest(e -> System.exit(0));

    root.getChildren().setAll(imageView, browser);
    scene.setRoot(root);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  /**
   * Executes a script on the current browser view.
   */
  public void executeScript(String script) {
    Platform.runLater(() -> webEngine.executeScript(script));
  }

  /**
   * Calls a javascript function on the current browser view.
   */
  public void callJsFunction(String function, Object... args) {
    Platform.runLater(() -> {
      JSObject window = (JSObject) webEngine.executeScript("window");
      window.call(function, args);
    });
  }

  /**
   * Loads a menu into the view and sets the controller.
   */
  public boolean loadMenu(Menu menu) {
    Platform.runLater(() -> {
      if (this.currentMenu != null) {
        this.currentMenu.onExit();
      }
      this.currentMenu = menu;
      this.currentMenu.onLoad();
      webEngine.setUserStyleSheetLocation(menu.getStyleSheetLocation());
      webEngine.loadContent(menu.getHtml());
    });
    return true;
  }

  /**
   * Move the mouse back into the window.
   */
  private void keepMouseInWindow() {
    Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
    Window viewBounds = scene.getWindow();

    if (mouseLocation.x < (int) viewBounds.getX()) {
      Main.this.robot.mouseMove((int) viewBounds.getX(), mouseLocation.y);
    }
    if (mouseLocation.x >= (int) (viewBounds.getX() + viewBounds.getWidth() - 1)) {
      Main.this.robot
          .mouseMove((int) (viewBounds.getX() + viewBounds.getWidth() - 1), mouseLocation.y);
    }
    if (mouseLocation.y < (int) viewBounds.getY()) {
      Main.this.robot.mouseMove(mouseLocation.x, (int) viewBounds.getY());
    }
    if (mouseLocation.y >= (int) (viewBounds.getY() + viewBounds.getHeight())) {
      Main.this.robot
          .mouseMove(mouseLocation.x, (int) (viewBounds.getY() + viewBounds.getHeight()));
    }
  }

}
