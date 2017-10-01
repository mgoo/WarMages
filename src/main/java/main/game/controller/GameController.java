package main.game.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
      case 'w': //up
        if (keyevent.wasCtrlDown()) {
          throw new Error("Not yet implemented!"); //TODO
        }
        if (keyevent.wasShiftDown()) {
          throw new Error("Not yet implemented!"); //TODO
        }
      case 'a': //left
        if (keyevent.wasCtrlDown()) {
          throw new Error("Not yet implemented!"); //TODO
        }
        if (keyevent.wasShiftDown()) {
          throw new Error("Not yet implemented!"); //TODO
        }
      case 's': //down
        if (keyevent.wasCtrlDown()) {
          throw new Error("Not yet implemented!"); //TODO
        }
        if (keyevent.wasShiftDown()) {
          throw new Error("Not yet implemented!"); //TODO
        }
      case 'd': //right
        if (keyevent.wasCtrlDown()) {
          throw new Error("Not yet implemented!"); //TODO
        }
        if (keyevent.wasShiftDown()) {
          throw new Error("Not yet implemented!"); //TODO
        }
    }
  }

  /**
   * Calls the appropriate method in the model depending on the user' mouse click.
   *
   * @param mouseevent -- the MouseClick object for the current mouse click
   */
  public void onMouseEvent(MouseClick mouseevent) {

    //note to code reviewer : there is some code duplication here. will refactor this code later.

    if (mouseevent.wasLeft()) {
      if (mouseevent.wasShiftDown()) {
        //CASE 1 => LEFT + SHIFT

        //select the unit under the click if there is one
        List<Unit> selectedUnits = gameModel.getAllUnits().stream().filter(
            u -> u.getPosition().distance(mouseevent.getLocation()) <= Math
                .max(u.getSize().width, u.getSize().height))
            .collect(Collectors.toList());

        Unit selectedUnit = (selectedUnits.isEmpty()) ? null : selectedUnits.get(0);

        //add the new selected units to previously selected ones
        Collection<Unit> updatedUnitSelection = new ArrayList<>(gameModel.getUnitSelection());
        if (!selectedUnits.isEmpty()) {
          updatedUnitSelection.add(selectedUnit);
        }

        gameModel.setUnitSelection(updatedUnitSelection);

      } else if (mouseevent.wasCtrlDown()) {
        //CASE 2 => LEFT + CTRL

        //select the unit under the click if there is one
        List<Unit> selectedUnits = gameModel.getAllUnits().stream().filter(
            u -> u.getPosition().distance(mouseevent.getLocation()) <= Math
                .max(u.getSize().width, u.getSize().height))
            .collect(Collectors.toList());

        Unit selectedUnit = (selectedUnits.isEmpty()) ? null : selectedUnits.get(0);

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

        //select the unit under the click if there is one
        List<Unit> selectedUnits = gameModel.getAllUnits().stream().filter(
            u -> u.getPosition().distance(mouseevent.getLocation()) <= Math
                .max(u.getSize().width, u.getSize().height))
            .collect(Collectors.toList());

        Unit selectedUnit = (selectedUnits.isEmpty()) ? null : selectedUnits.get(0);

        if (!selectedUnits.isEmpty()) {
          gameModel.setUnitSelection(new ArrayList<>(Arrays.asList(selectedUnit)));
        }
      }

    } else {
      throw new Error("Not yet implemented!"); //TODO
    }
  }
}