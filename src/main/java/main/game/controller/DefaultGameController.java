package main.game.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import main.common.GameController;
import main.common.GameView;
import main.common.entity.Entity;
import main.common.entity.HeroUnit;
import main.common.entity.usable.Item;
import main.common.entity.Team;
import main.common.entity.Unit;
import main.common.GameModel;
import main.common.exceptions.UsableStillInCoolDownException;
import main.common.events.AbilityIconClick;
import main.common.events.ItemIconClick;
import main.common.events.KeyEvent;
import main.common.events.MouseClick;
import main.common.events.MouseDrag;
import main.common.events.UnitIconClick;
import main.game.model.entity.unit.state.TargetEnemyUnit;
import main.game.model.entity.unit.state.TargetItem;
import main.game.model.entity.unit.state.TargetMapPoint;

/**
 * Allows the user to control the game. Listens to user actions on the view
 * {@link GameView}, e.g.
 * mouse and keyboard input, and calls methods on the model to respond to the user input.
 *
 * @author Hrshikesh Arora
 */
public class DefaultGameController implements GameController {

  private final GameModel model;

  private AbilityIconClick ability = null;
  private ItemIconClick item = null;

  public DefaultGameController(GameModel model) {
    this.model = model;
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
        model.setUnitSelection(model.getAllUnits());
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
    Unit selectedUnit = model.getAllUnits()
        .stream()
        .filter(u -> u.getCentre().distanceTo(mouseEvent.getLocation())
            <= Math.max(u.getSize().width, u.getSize().height))
        .filter(u -> u.getTeam() == (mouseEvent.wasLeft() ? Team.PLAYER : Team.ENEMY))
        .sorted(Comparator.comparingDouble(
            s -> s.getCentre().distanceTo(mouseEvent.getLocation())))
        .findFirst().orElse(null);

    //If it was a left click
    if (mouseEvent.wasLeft()) {

      //check if clicked on a valid unit so need to activate ability
      if (selectedUnit != null && ability != null
          && ability.getAbility().canApplyTo(selectedUnit)) {
        ability.getAbility().use(model.getWorld(), Collections.singletonList(selectedUnit));
        ability = null;
        return;
      } else if (selectedUnit != null && item != null && item.getItem().isReadyToBeUsed()) {
        item.getItem().use(model.getWorld(), Collections.singletonList(selectedUnit));
        item = null;
        return;
      } else {
        ability = null;
      }

      if (mouseEvent.wasShiftDown()) {
        //add the new selected unit to the previously selected ones
        if (selectedUnit != null) {
          model.addToUnitSelection(selectedUnit);
        }

      } else if (mouseEvent.wasCtrlDown()) {
        //if clicked unit already selected, deselect it. otherwise, select it
        Collection<Unit> updatedUnits = new ArrayList<>(model.getUnitSelection());

        if (updatedUnits.contains(selectedUnit)) {
          updatedUnits.remove(selectedUnit);
        } else if (selectedUnit != null) {
          updatedUnits.add(selectedUnit);
        }
        model.setUnitSelection(updatedUnits);

      } else {
        //deselect all previous selected units and select the clicked unit
        model.setUnitSelection(new ArrayList<>());
        if (selectedUnit != null) {
          model.setUnitSelection(Collections.singletonList(selectedUnit));
        }
      }
    } else { //otherwise, it must have been a right click

      //select the item under the click if there is one
      Item selectedItem = model.getAllEntities()
          .stream()
          .filter(u -> u instanceof Item)
          .map(Item.class::cast)
          .filter(u -> u.getCentre().distanceTo(mouseEvent.getLocation())
              <= Math.max(u.getSize().width, u.getSize().height))
          .sorted(Comparator.comparingDouble(
              s -> s.getCentre().distanceTo(mouseEvent.getLocation())))
          .findFirst().orElse(null);

      //find the closest thing i.e. unit or item
      Entity closest = Stream.of(selectedItem, selectedUnit)
          .filter(Objects::nonNull)
          .sorted(Comparator.comparingDouble(
              s -> s.getCentre().distanceTo(mouseEvent.getLocation())))
          .findFirst().orElse(null);

      if (selectedUnit != null && closest instanceof Unit) {
        //attack an enemy
        for (Unit unit : model.getUnitSelection()) {
          unit.setTarget(new TargetEnemyUnit(unit, selectedUnit, unit.getUnitType().getBaseAttack()));
        }
      } else if (selectedItem != null && closest instanceof Item) {
        // move all selected units to the clicked location
        for (Unit unit : model.getUnitSelection()) {
          unit.setTarget(new TargetItem(unit, selectedItem));
        }
        if (model.getUnitSelection().contains(model.getHeroUnit())) {
          model.getHeroUnit().setTarget(new TargetItem(model.getHeroUnit(), selectedItem));
        }
      } else {
        // move all selected units to the clicked location
        for (Unit unit : model.getUnitSelection()) {
          unit.setTarget(new TargetMapPoint(unit, mouseEvent.getLocation()));
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
    Collection<Unit> selectedUnits = model.getAllUnits()
        .stream()
        .filter(u -> mouseEvent.getMapShape().contains(u.getRect()))
        .filter(u -> u.getTeam() == Team.PLAYER)
        .collect(Collectors.toSet());

    if (mouseEvent.wasShiftDown()) {
      //add all units in the drag rectangle to the currently selected units
      Collection<Unit> updatedUnitSelection = new ArrayList<>(model.getUnitSelection());
      updatedUnitSelection.addAll(selectedUnits);
      model.setUnitSelection(updatedUnitSelection);

    } else if (mouseEvent.wasCtrlDown()) {
      //toggle all units under the drag rectangle

      Collection<Unit> updatedUnits = new ArrayList<>(model.getUnitSelection());

      for (Unit unit : selectedUnits) {
        if (updatedUnits.contains(unit)) {
          updatedUnits.remove(unit);
        } else {
          updatedUnits.add(unit);
        }
      }
      model.setUnitSelection(updatedUnits);

    } else {
      //deselect all units then select all units in the drag rectangle
      model.setUnitSelection(selectedUnits); // may be empty
    }

  }

  /**
   * When the GameView is double clicked it should select all the units of the same type.
   */
  public void onDbClick(MouseClick mouseEvent) {
    //select the unit under the click if there is one
    Unit dbClickedUnit = model.getAllUnits()
        .stream()
        .filter(u -> u.getTeam() == Team.PLAYER)
        .filter(u -> u.getCentre().distanceTo(mouseEvent.getLocation())
            <= Math.max(u.getSize().width, u.getSize().height))
        .sorted(Comparator.comparingDouble(
            s -> s.getCentre().distanceTo(mouseEvent.getLocation())))
        .findFirst().orElse(null);
    if (dbClickedUnit == null) {
      return;
    }
    // Get all units that are of the same Type
    Set<Unit> unitsSelected = model.getAllUnits()
        .stream()
        .filter(u -> u.getTeam() == Team.PLAYER)
        .filter(unit -> dbClickedUnit.getType() == unit.getType())
        .filter(unit -> dbClickedUnit.getClass() == unit.getClass())
        .collect(Collectors.toSet());

    if (mouseEvent.wasShiftDown()) {
      unitsSelected.stream()
          .filter(unit -> !model.getUnitSelection().contains(unit))
          .forEach(model::addToUnitSelection);
    } else {
      model.setUnitSelection(unitsSelected);
    }

  }

  /**
   * When a selected units icon is clicked in from the hud.
   */
  public void onUnitIconClick(UnitIconClick clickEvent) {
    if (clickEvent.wasCtrlDown()) {
      Collection<Unit> newSelection = new ArrayList<>(this.model.getUnitSelection());
      newSelection.remove(clickEvent.getUnit());
      this.model.setUnitSelection(newSelection);
    } else {
      model.setUnitSelection(Collections.singletonList(clickEvent.getUnit()));
    }
  }

  /**
   * When a heros ability icon is clicked in from the hud.
   */
  public void onAbilityIconClick(AbilityIconClick clickEvent) {
    if (clickEvent.getAbility().isReadyToBeUsed()) {
      this.ability = clickEvent;
    } else {
      throw new UsableStillInCoolDownException();
    }
  }

  /**
   * When a items icon that has being picked up by the hero is clicked in from the hud.
   */
  public void onItemIconClick(ItemIconClick clickEvent) {
    if (clickEvent.getItem().isReadyToBeUsed()) {
      this.item = clickEvent;
    } else {
      throw new UsableStillInCoolDownException();
    }
  }
}