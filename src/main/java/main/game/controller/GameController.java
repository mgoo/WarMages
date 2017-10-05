package main.game.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;
import main.game.model.GameModel;
import main.game.model.entity.Unit;
import main.game.view.GameView;
import main.game.view.events.KeyEvent;
import main.game.view.events.MouseClick;
import main.game.view.events.MouseDrag;
import main.util.MapPoint;
import main.util.MapRect;
import main.util.MapSize;

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
    }
  }

  /**
   * Calls the appropriate method in the model depending on the user' mouse click.
   *
   * @param mouseEvent -- the MouseClick object for the current mouse click
   */
  public void onMouseEvent(MouseClick mouseEvent) {

    if (mouseEvent.wasLeft()) {
      if (mouseEvent.wasShiftDown()) {
        leftShiftClick(gameModel, mouseEvent.getLocation());
      } else if (mouseEvent.wasCtrlDown()) {
        leftCtrlClick(gameModel, mouseEvent.getLocation());
      } else {
        onlyLeftClick(gameModel, mouseEvent.getLocation());
      }
    }
  }

  /**
   * TODO javadoc.
   *
   * @param mouseEvent -- the MouseClick object for the current mouse click
   */
  public void onMouseDrag(MouseDrag mouseEvent) {

    if (mouseEvent.wasLeft()) {
      if (mouseEvent.wasShiftDown()) {
        leftShiftDrag(gameModel, mouseEvent);
      } else if (mouseEvent.wasCtrlDown()) {
        leftCtrlDrag(gameModel, mouseEvent);
      } else {
        onlyLeftDrag(gameModel, mouseEvent);
      }
    }
  }

  /**
   * TODO javadoc.
   *
   * @param gameModel
   * @param mouseClick
   */
  private static Unit getUnitUnderMouse(GameModel gameModel, MapPoint mouseClick) {
    //select the unit under the click if there is one
    return gameModel.getAllUnits()
        .stream()
        .filter(u -> u.getCentre().distance(mouseClick) <= Math
            .max(u.getSize().width, u.getSize().height))
        .sorted(Comparator.comparingDouble(s -> s.getCentre().distance(mouseClick)))
        .findFirst().orElse(null);
  }

  /**
   * TODO javaodc.
   *
   * @param gameModel
   * @param mouseClick
   */
  private static void onlyLeftClick(GameModel gameModel, MapPoint mouseClick) {
    //CASE 3 => ONLY LEFT CLICK

    Unit selectedUnit = getUnitUnderMouse(gameModel, mouseClick);

    //deselect all previous selected units
    gameModel.setUnitSelection(new ArrayList<>());

    if (selectedUnit != null) {
      gameModel.setUnitSelection(Collections.singletonList(selectedUnit));
    }
  }

  /**
   * TODO add javadoc.
   *
   * @param gameModel
   * @param mouseClick
   */
  private static void leftCtrlClick(GameModel gameModel, MapPoint mouseClick) {
    //CASE 2 => LEFT + CTRL
    Unit selectedUnit = getUnitUnderMouse(gameModel, mouseClick);

    Collection<Unit> updatedUnits = new ArrayList<>(gameModel.getUnitSelection());

    //if unit already selected, deselct it
    if (updatedUnits.contains(selectedUnit)) {
      updatedUnits.remove(selectedUnit);
    } else { //if not, select it
      updatedUnits.add(selectedUnit);
    }

    gameModel.setUnitSelection(updatedUnits);
  }

  /**
   * TODO javadoc.
   *
   * @param gameModel
   * @param mouseClick
   */
  private static void leftShiftClick(GameModel gameModel, MapPoint mouseClick) {
    //CASE 1 => LEFT + SHIFT

    Unit selectedUnit = getUnitUnderMouse(gameModel, mouseClick);

    //add the new selected units to previously selected ones
    Collection<Unit> updatedUnitSelection = new ArrayList<>(gameModel.getUnitSelection());
    if (selectedUnit != null) {
      updatedUnitSelection.add(selectedUnit);
    }

    gameModel.setUnitSelection(updatedUnitSelection);
  }

  /**
   * TODO javadoc.
   *
   * @param gameModel
   * @param mouseEvent
   * @return
   */
  private static Collection<Unit> getAllUnitsInArea(GameModel gameModel, MouseDrag mouseEvent) {

    MapRect dragArea = new MapRect(
        mouseEvent.getTopLeft(),
        mouseEvent.getTopLeft().translate(mouseEvent.getSize().width, mouseEvent.getSize().height)
    );

    return gameModel.getAllUnits()
        .stream()
        .filter(u -> dragArea.contains(u.getRect()))
        .collect(Collectors.toSet());
  }

  /**
   * TODO javaodc.
   *
   * @param gameModel
   * @param mouseEvent
   */
  private static void onlyLeftDrag(GameModel gameModel, MouseDrag mouseEvent) {

    Collection<Unit> selectedUnits = getAllUnitsInArea(gameModel, mouseEvent);

    //deselect all previous selected units
    gameModel.setUnitSelection(new ArrayList<>());

    if (selectedUnit != null) {
      gameModel.setUnitSelection(Collections.singletonList(selectedUnit));
    }
  }

  /**
   * TODO javadoc.
   */
  private static void leftCtrlDrag(GameModel gameModel, MouseDrag mouseEvent) {
    Collection<Unit> selectedUnits = getAllUnitsInArea(gameModel, mouseEvent);

    Collection<Unit> updatedUnits = new ArrayList<>(gameModel.getUnitSelection());

    //if unit already selected, deselct it
    if (updatedUnits.contains(selectedUnit)) {
      updatedUnits.remove(selectedUnit);
    } else { //if not, select it
      updatedUnits.add(selectedUnit);
    }

    gameModel.setUnitSelection(updatedUnits);
  }

  /**
   * TODO javadoc.
   *
   * @param gameModel
   * @param mouseEvent
   */
  private static void leftShiftDrag(GameModel gameModel, MouseDrag mouseEvent) {

    Collection<Unit> selectedUnits = getAllUnitsInArea(gameModel, mouseEvent);

    //add the new selected units to previously selected ones
    Collection<Unit> updatedUnitSelection = new ArrayList<>(gameModel.getUnitSelection());
    if (selectedUnit != null) {
      updatedUnitSelection.add(selectedUnit);
    }

    gameModel.setUnitSelection(updatedUnitSelection);
  }
}