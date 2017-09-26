package main.game.controller;

import main.game.view.events.KeyEvent;
import main.game.view.events.MouseClick;

/**
 * Allows the user to control the game. Listens to user actions on the view {@link GameView}, e.g.
 * mouse and keyboard input, and calls methods on the model to respond to the user input.
 *
 * @author Hrshikesh Arora
 */
public class GameController {

  public GameController() {
    //TODO
    throw new Error("NYI");
  }

  /**
   * Calls the appropriate method in the model depending on the user's key press.
   *
   * @param keyevent -- the KeyEvent object for the current key press
   */
  public void onKeyPress(KeyEvent keyevent) {
    //TODO
    throw new Error("NYI");
  }

  /**
   * Calls the appropriate method in the model depending on the user' mouse click.
   *
   * @param mouseevent -- the MouseClick object for the current mouse click
   */
  public void onMouseEvent(MouseClick mouseevent) {
    if (mouseevent.wasLeft()) {
      //TODO
      throw new Error("NYI");
    } else {
      //TODO
      throw new Error("NYI");
    }
  }
}