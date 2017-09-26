package main.menu;

import java.net.URL;
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
    webEngine.load(this.getUrl().toExternalForm());
    webEngine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
      if (newState == Worker.State.SUCCEEDED) {
        System.out.println("Bound javaObj");
        JSObject window = (JSObject) webEngine.executeScript("window");
        window.setMember("controller", this.getMenuController());
        webEngine.executeScript(this.getListeners());
        System.out.println(this.getListeners());
      }
    });
  }

  /**
   * Gets the URL of the html file to load.
   */
  abstract URL getUrl();

  /**
   * Gets an instance of the object to bind to javascript.
   */
  abstract MenuController getMenuController();

  /**
   * gets the javascript that adds the listeners to the buttons
   */
  abstract String getListeners();
}
