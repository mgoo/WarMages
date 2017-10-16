package main.common.menu;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * Makes sure all the MenuControllers have the same type.
 *
 * @author Andrew McGhie
 */
public abstract class MenuController {

  /**
   * This is called when a click event happens on the menu.
   * To be called from java not javascript.
   */
  public void onClick(MouseEvent event) {

  }

  /**
   * This is called when the mouse is moved.
   * To be called from java not javascript.
   */
  public void onMouseMove(MouseEvent event) {

  }

  /**
   * This is called when a key is pressed.
   * TO be called from java not javascript.
   */
  public void onKeyDown(KeyEvent event) {

  }
}
