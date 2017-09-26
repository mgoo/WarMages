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

  private final GameModel gamemodel;

  public GameController(GameModel m) {
    this.gamemodel = m;
  }

  /**
   * Calls the appropriate method in the model depending on the user's key press.
   *
   * @param keyevent -- the KeyEvent object for the current key press
   */
  public void onKeyPress(KeyEvent keyevent) {
    char key = keyevent.getKey();

    switch(key){
      case '.':
        gamemodel.setEntitySelection(gamemodel.getAllEntities());
      case 'w': //up
        if(keyevent.wasCtrlDown()){
          throw new Error("NYI"); //TODO
        }
        if (keyevent.wasShiftDown()){
          throw new Error("NYI"); //TODO
        }
      case 'a': //left
        if(keyevent.wasCtrlDown()){
          throw new Error("NYI"); //TODO
        }
        if (keyevent.wasShiftDown()){
          throw new Error("NYI"); //TODO
        }
      case 's': //down
        if(keyevent.wasCtrlDown()){
          throw new Error("NYI"); //TODO
        }
        if (keyevent.wasShiftDown()){
          throw new Error("NYI"); //TODO
        }
      case 'd': //right
        if(keyevent.wasCtrlDown()){
          throw new Error("NYI"); //TODO
        }
        if (keyevent.wasShiftDown()){
          throw new Error("NYI"); //TODO
        }
    }
  }

  /**
   * Calls the appropriate method in the model depending on the user' mouse click.
   *
   * @param mouseevent -- the MouseClick object for the current mouse click
   */
  public void onMouseEvent(MouseClick mouseevent) {
    if (mouseevent.wasLeft()) {
      //deselects all previous units then select the unit under the click if there is one
      gamemodel.getEntitySelection().clear();
      gamemodel.
    } else {
      throw new Error("NYI"); //TODO
    }
  }
}