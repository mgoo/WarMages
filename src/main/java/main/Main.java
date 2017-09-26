package main;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.TimerTask;
import java.util.Timer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyCombination.Modifier;
import javafx.scene.input.KeyCombination.ModifierValue;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javax.imageio.ImageIO;
import menu.MainMenuController;
import netscape.javascript.JSObject;

/**
 * Should just instantiate the needed classes to run the app. This should not contain any app
 * logic.
 */
public class Main extends Application {
  private static String pwd;
  private Robot robot = new Robot();

  public Main() throws AWTException {
  }

  /**
   * Start the app.
   */
  public static void main(String[] args) {
     Main.pwd = System.getProperty("user.dir");
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    int size = 0;
    for (Screen screen : Screen.getScreens()) {
      int newSize = (int)(screen.getBounds().getHeight() + screen.getBounds().getWidth());
      if (newSize <= size) continue;
      primaryStage.setX(screen.getBounds().getMinX() + 100);
      size = newSize;
    }

    primaryStage.setTitle("Marco The Mage");
    primaryStage.setFullScreen(true);
    primaryStage.setFullScreenExitHint("");
    primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    primaryStage.setAlwaysOnTop(true);

    primaryStage.initStyle(StageStyle.TRANSPARENT);

    Scene scene = new Scene(new Group());
    StackPane root = new StackPane();

    root.setPrefHeight(1080);
    root.setPrefWidth(1920);

    WebView browser = new WebView();
    browser.setPrefHeight(1920);
    browser.setPrefWidth(1080);
    WebEngine webEngine = browser.getEngine();

    //        URL url = getClass().getResource("../index.html");
    URL url = new URL("file:///"+pwd+"/src/main/resources/html/main_menu.html");

    webEngine.load(url.toExternalForm());
//    webEngine.load("http://css3test.com");
    final boolean[] reloaded = {false};
    webEngine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
      if (newState == Worker.State.SUCCEEDED) {
        // @HACK to get the css animations to render properly
        if (!reloaded[0]) {
          webEngine.reload();
          reloaded[0] = true;
        } else {
            com.sun.javafx.webkit.Accessor.getPageFor(webEngine)
                .setBackgroundColor((new java.awt.Color(0, 0, 0, 0)).getRGB());

          JSObject window = (JSObject) webEngine.executeScript("window");
          window.setMember("controller", new MainMenuController(webEngine));
        }
      }
    });

    ImageView imageView = new ImageView();
    imageView.setImage(new Image("html/download.jpg", 1920, 1080, true, true));
    imageView.setFitWidth(1000);
    imageView.setFitHeight(1000);

    // Mouse events will be handled in html
    root.setOnKeyReleased(event -> {

      System.out.println("ALT: " + event.isAltDown());
      System.out.println("SHF: " + event.isShiftDown());
      System.out.println("CTTL: " + event.isControlDown());
      System.out.println("Code: " + event.getCode().toString());
    });

    browser.setOnMouseExited(event -> {
      System.out.println("Mouse Exited");
      Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
      Window viewBounds = scene.getWindow();

      if (mouseLocation.x < (int)viewBounds.getX()) {
        Main.this.robot.mouseMove((int)viewBounds.getX(), mouseLocation.y);
      }
      if (mouseLocation.x >= (int)(viewBounds.getX() + viewBounds.getWidth() - 1)) {
        Main.this.robot.mouseMove((int)(viewBounds.getX() + viewBounds.getWidth() - 1), mouseLocation.y);
      }
      if (mouseLocation.y < (int)viewBounds.getY()) {
        Main.this.robot.mouseMove(mouseLocation.x, (int)viewBounds.getY());
      }
      if (mouseLocation.y >= (int)(viewBounds.getY() + viewBounds.getHeight())) {
        Main.this.robot.mouseMove(mouseLocation.x, (int)(viewBounds.getY() + viewBounds.getHeight()));
      }
    });

    browser.setOnMouseMoved(event -> {
      Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
      Window viewBounds = scene.getWindow();

      if (mouseLocation.x < (int)viewBounds.getX()) {
        Main.this.robot.mouseMove((int)viewBounds.getX(), mouseLocation.y);
      }
      if (mouseLocation.x >= (int)(viewBounds.getX() + viewBounds.getWidth() - 1)) {
        Main.this.robot.mouseMove((int)(viewBounds.getX() + viewBounds.getWidth() - 1), mouseLocation.y);
      }
      if (mouseLocation.y < (int)viewBounds.getY()) {
        Main.this.robot.mouseMove(mouseLocation.x, (int)viewBounds.getY());
      }
      if (mouseLocation.y >= (int)(viewBounds.getY() + viewBounds.getHeight())) {
        Main.this.robot.mouseMove(mouseLocation.x, (int)(viewBounds.getY() + viewBounds.getHeight()));
      }
    });

    root.getChildren().setAll(imageView, browser);
    scene.setRoot(root);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
