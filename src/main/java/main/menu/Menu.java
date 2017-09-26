package main.menu;

import java.net.URL;
import java.util.Scanner;
import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import netscape.javascript.JSObject;

/**
 * Esssentually a wrapper that brings the html and the controller together.
 *
 * @author Andrew McGhie
 */
public abstract class Menu {

  /**
   * Loads a menu into the webEngine and binds the controller to the js.
   */
  public void load(WebEngine webEngine) {
    webEngine.loadContent(this.getHtml());
    webEngine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
      if (newState == Worker.State.SUCCEEDED) {
        JSObject window = (JSObject) webEngine.executeScript("window");
        window.setMember("controller", this.getMenuController());
      }
    });
  }

  /**
   * Turns a file into a string.
   */
  String fileToString(String fileName) {
    try (Scanner scanner = new Scanner(fileName)) {
      scanner.useDelimiter("\\A");
      return scanner.next();
    }
  }

  /**
   * Gets the HTML to display on the page.
   */
  abstract String getHtml();

  /**
   * Gets an instance of the object to bind to javascript.
   */
  abstract MenuController getMenuController();
}
