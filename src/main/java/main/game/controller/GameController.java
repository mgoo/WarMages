package main.game.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import main.game.model.GameModel;
import main.game.model.entity.Unit;
import main.game.view.GameView;
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
   * Calls the appropriate method in the model depending on the user's key press.
   *
   * @param keyevent -- the KeyEvent object for the current key press
   */
  public void onKeyPress(KeyEvent keyevent) {
    char key = keyevent.getKey();

    switch (key) {
      case '.':
        gameModel.setUnitSelection(gameModel.getAllUnits());
        break;
      case 'w': //up
        if (keyevent.wasCtrlDown()) {
          throw new Error("Not yet implemented!"); //TODO
        }
        if (keyevent.wasShiftDown()) {
          throw new Error("Not yet implemented!"); //TODO
        }
        break;
      case 'a': //left
        if (keyevent.wasCtrlDown()) {
          throw new Error("Not yet implemented!"); //TODO
        }
        if (keyevent.wasShiftDown()) {
          throw new Error("Not yet implemented!"); //TODO
        }
        break;
      case 's': //down
        if (keyevent.wasCtrlDown()) {
          throw new Error("Not yet implemented!"); //TODO
        }
        if (keyevent.wasShiftDown()) {
          throw new Error("Not yet implemented!"); //TODO
        }
        break;
      case 'd': //right
        if (keyevent.wasCtrlDown()) {
          throw new Error("Not yet implemented!"); //TODO
        }
        if (keyevent.wasShiftDown()) {
          throw new Error("Not yet implemented!"); //TODO
        }
        break;
      default:
        break;
    }
  }

  /**
   * Calls the appropriate method in the model depending on the user' mouse click.
   *
   * @param mouseEvent -- the MouseClick object for the current mouse click
   */
  public void onmouseEvent(MouseClick mouseEvent) {

    if (mouseEvent.wasLeft()) {

      //select the unit under the click if there is one
      Unit selectedUnit = gameModel.getAllUnits().stream().filter(
          u -> u.getCentre().distance(mouseEvent.getLocation()) <= Math
              .max(u.getSize().width, u.getSize().height))
          .sorted(
              Comparator.comparingDouble(s -> s.getCentre().distance(mouseEvent.getLocation())))
          .findFirst().orElse(null);

      if (mouseEvent.wasShiftDown()) {
        //CASE 1 => LEFT + SHIFT

        //add the new selected units to previously selected ones
        Collection<Unit> updatedUnitSelection = new ArrayList<>(gameModel.getUnitSelection());
        if (selectedUnit != null) {
          updatedUnitSelection.add(selectedUnit);
        }

        gameModel.setUnitSelection(updatedUnitSelection);

      } else if (mouseEvent.wasCtrlDown()) {
        //CASE 2 => LEFT + CTRL

        Collection<Unit> updatedUnits = new ArrayList<>(gameModel.getUnitSelection());

        //if unit already selected, deselct it
        if (updatedUnits.contains(selectedUnit)) {
          updatedUnits.remove(selectedUnit);
        } else { //if not, select it
          updatedUnits.add(selectedUnit);
        }

        gameModel.setUnitSelection(updatedUnits);

      } else {
        //CASE 3 => ONLY LEFT CLICK

        //deselect all previous selected units
        gameModel.setUnitSelection(new ArrayList<>());

        if (selectedUnit != null) {
          gameModel.setUnitSelection(Arrays.asList(selectedUnit));
        }
      }

    } else {
      throw new Error("Not yet implemented!"); //TODO
    }
  }
}