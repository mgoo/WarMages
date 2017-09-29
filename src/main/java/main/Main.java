package main;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.io.File;
import java.util.Arrays;
import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import main.game.model.saveandload.WorldLoader;
import main.game.model.saveandload.WorldLoader.DefaultFileLoader;
import main.game.model.saveandload.WorldSaveModel;
import main.game.model.saveandload.WorldSaveModel.DefaultFilesystem;
import main.menu.MainMenu;
import main.menu.Menu;
import netscape.javascript.JSObject;

/**
 * Should just instantiate the needed classes to run the app. This should not contain any app
 * logic.
 */
public class Main extends Application {

  private Robot robot = new Robot();
  private WebEngine webEngine;
  private Menu currentMenu;

  public Main() throws AWTException {
  }

  /**
   * Start the app.
   */
  public static void main(String[] args) {
    launch(args);
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
      Main.this.robot.mouseMove((int)(bounds.getMinX() + bounds.getWidth() / 2),
          (int)(bounds.getMinY() + bounds.getHeight() / 2));
      size = newSize;
    }

    primaryStage.setTitle("Marco The Mage");
    primaryStage.setFullScreen(true);
    primaryStage.setFullScreenExitHint("");
    primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    primaryStage.setAlwaysOnTop(true);

    primaryStage.initStyle(StageStyle.TRANSPARENT);

    final Scene scene = new Scene(new Group());
    final StackPane root = new StackPane();
    final WebView browser = new WebView();
    final ImageView imageView = new ImageView();
    final MainMenu mainMenu = new MainMenu(
        this,
        new WorldLoader(new DefaultFileLoader()),
        new WorldSaveModel(new DefaultFilesystem()),
        imageView
    );

    root.setPrefWidth(1920);
    root.setPrefHeight(1080);

    browser.setPrefWidth(1920);
    browser.setPrefHeight(1080);

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

    this.loadMenu(mainMenu);

    imageView.setImage(new Image(
        new File("resources/images/units/archer.png").toURI().toString(),
        1920,
        1080,
        true,
        true
    ));

    imageView.setFitWidth(scene.getWidth());
    imageView.setFitHeight(scene.getHeight());

    // Mouse events will be handled in html
    root.setOnKeyReleased(event -> {
      System.out.println("SHF: " + event.isShiftDown());
      System.out.println("CTTL: " + event.isControlDown());
      System.out.println("Code: " + event.getCode().toString());
    });

    browser.setOnMouseExited(event -> {
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
    });

    browser.setOnMouseMoved(event -> {

    });

    browser.setOnMouseClicked(event -> {
      System.out.println("SHF: " + event.isShiftDown());
      System.out.println("CTTL: " + event.isControlDown());
      System.out.println("Code: " + event.getButton());
    });

    root.getChildren().setAll(imageView, browser);
    scene.setRoot(root);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public Object executeScript(String script) {
    return this.webEngine.executeScript(script);
  }

  /**
   * Loads a menu into the view and sets the controller.
   */
  public boolean loadMenu(Menu menu) {
    this.currentMenu = menu;
    webEngine.setUserStyleSheetLocation(menu.getStyleSheetLocation());
    webEngine.loadContent(menu.getHtml());
    return true;
  }

}
