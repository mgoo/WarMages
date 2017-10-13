package main.game.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;
import main.common.GameController;
import main.game.model.GameModel;
import main.game.model.entity.Team;
import main.game.model.entity.Unit;
import main.game.view.GameView;
import main.game.view.events.KeyEvent;
import main.game.view.events.MouseClick;
import main.game.view.events.MouseDrag;

/**
 * Allows the user to control the game. Listens to user actions on the view {@link GameView}, e.g.
 * mouse and keyboard input, and calls methods on the model to respond to the user input.
 *
 * @author Hrshikesh Arora
 */
public class DefaultGameController implements GameController {

  private final GameModel gameModel;

  public DefaultGameController(GameModel model) {
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
        //select all units owned  by the player
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
      //TODO need to do each of qwerasdfzxcv events
    }
  }

  /**
   * Responds to user's mouse click actions by calling the appropriate method on the model. The
   * following scenarios are taken care of:
   *
   * <ul>
   * <li>LEFT click => Deselect all previously selected units and select only the clicked unit</li>
   * <li>LEFT click + SHIFT => Add the clicked unit to the previously selected units</li>
   * <li>LEFT click + CTRL => If the clicked unit is already selected, deselect it. Otherwise,
   *   select that unit.</li>
   * <li>RIGHT click => All selected units move to the clicked location</li>
   * <li>RIGHT click on an enemy => All selected units will attack the enemy unit</li>
   * </ul>
   *
   * @param mouseEvent -- the MouseClick object for the current mouse click
   */
  public void onMouseEvent(MouseClick mouseEvent) {

    //select the unit under the click if there is one
    Unit selectedUnit = gameModel.getAllUnits()
        .stream()
        .filter(u -> u.getCentre().distanceTo(mouseEvent.getLocation())
            <= Math.max(u.getSize().width, u.getSize().height))
        .filter(u -> u.getTeam() == (mouseEvent.wasLeft() ? Team.PLAYER : Team.ENEMY))
        .sorted(Comparator.comparingDouble(
            s -> s.getCentre().distanceTo(mouseEvent.getLocation())))
        .findFirst().orElse(null);

    //If it was a left click
    if (mouseEvent.wasLeft()) {

      if (mouseEvent.wasShiftDown()) {
        //add the new selected unit to the previously selected ones
        Collection<Unit> updatedUnitSelection = new ArrayList<>(gameModel.getUnitSelection());
        if (selectedUnit != null) {
          updatedUnitSelection.add(selectedUnit);
        }
        gameModel.setUnitSelection(updatedUnitSelection);

      } else if (mouseEvent.wasCtrlDown()) {
        //if clicked unit already selected, deselect it. otherwise, select it
        Collection<Unit> updatedUnits = new ArrayList<>(gameModel.getUnitSelection());

        if (updatedUnits.contains(selectedUnit)) {
          updatedUnits.remove(selectedUnit);
        } else {
          if (selectedUnit != null) {
            updatedUnits.add(selectedUnit);
          }
        }
        gameModel.setUnitSelection(updatedUnits);

      } else {
        //deselect all previous selected units and select the clicked unit
        gameModel.setUnitSelection(new ArrayList<>());
        if (selectedUnit != null) {
          gameModel.setUnitSelection(Collections.singletonList(selectedUnit));
        }
      }
    } else { //otherwise, it must have been a right click
      if (selectedUnit != null) {
        //attack an enemy
        for (Unit unit : gameModel.getUnitSelection()) {
          unit.setTarget(selectedUnit, gameModel.getWorld());
        }
      } else {
        // move all selected units to the clicked location
        for (Unit unit : gameModel.getUnitSelection()) {
          unit.setPath(gameModel.getWorld().findPath(unit.getTopLeft(),mouseEvent.getLocation()));
        }
      }
    }
  }

  /**
   * Responds to user's mouse drag actions by calling the appropriate method on the model. The
   * following scenarios are taken care of:
   *
   * <ul>
   * <li>LEFT drag => Deselect all previously selected units and select only the units under the
   *   drag rectangle</li>
   * <li>LEFT drag + SHIFT => Add all units in the drag rectangle to the selected units</li>
   * <li>LEFT drag + CTRL => Toggle all units in the drag rectangle. If the unit was selected,
   *   deselect it. Otherwise, select it.</li>
   * </ul>
   * @param mouseEvent -- the MouseClick object for the current mouse click
   */
  public void onMouseDrag(MouseDrag mouseEvent) {
    Collection<Unit> selectedUnits = gameModel.getAllUnits()
        .stream()
        .filter(u -> mouseEvent.getMapShape().contains(u.getCentre()))
        .filter(u -> u.getTeam() == Team.PLAYER)
        .collect(Collectors.toSet());

    if (mouseEvent.wasShiftDown()) {
      //add all units in the drag rectangle to the currently selected units
      Collection<Unit> updatedUnitSelection = new ArrayList<>(gameModel.getUnitSelection());
      updatedUnitSelection.addAll(selectedUnits);
      gameModel.setUnitSelection(updatedUnitSelection);

    } else if (mouseEvent.wasCtrlDown()) {
      //toggle all units under the drag rectangle

      Collection<Unit> updatedUnits = new ArrayList<>(gameModel.getUnitSelection());

      for (Unit unit : selectedUnits) {
        if (updatedUnits.contains(unit)) {
          updatedUnits.remove(unit);
        } else {
          updatedUnits.add(unit);
        }
      }
      gameModel.setUnitSelection(updatedUnits);

    } else {
      //deselect all units then select all units in the drag rectangle
      gameModel.setUnitSelection(selectedUnits); // may be empty
    }

  }
}