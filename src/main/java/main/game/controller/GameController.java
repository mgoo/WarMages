package main.game.controller;

import main.game.model.GameModel;
import main.game.view.events.KeyEvent;
import main.game.view.events.MouseClick;

/**
 * Allows the user to control the game. Listens to user actions on the view {@link GameView}, e.g.
 * mouse and keyboard input, and calls methods on the model to respond to the user input.
 *
 * @author Hrshikesh Arora
 */
public class GameController {

  private final GameModel gameModel;

  public GameController(GameModel model) {
    this.gameModel = model;
  }

  /**
   * TODO javadoc.
   */
  public void onKeyPress(KeyEvent keyevent) {
    //TODO
    throw new Error("NYI");
  }

  /**
   * TODO javadoc.
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