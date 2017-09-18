package menu;

import java.net.URL;
import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import netscape.javascript.JSObject;

/**
 * Loads a menu to the webEngine
 */
public abstract class Menu {

  /**
   * Loads a menu into the webEngine and binds the contorller to the js
   */
  public void load(WebEngine webEngine) {
    webEngine.load(this.getURL().toExternalForm());
    webEngine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
      if (newState == Worker.State.SUCCEEDED) {
        JSObject window = (JSObject) webEngine.executeScript("window");
        window.setMember("controller", this.getMenuController());
      }
    });
  }

  /**
   * Gets the URL of the html file to load
   */
  abstract URL getURL();

  /**
   * Gets an instance of the object to bind to javascript
   */
  abstract MenuController getMenuController();
}
