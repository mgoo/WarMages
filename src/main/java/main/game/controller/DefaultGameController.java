package main.game.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import main.exceptions.UsableStillInCoolDownException;
import main.game.model.GameModel;
import main.game.model.entity.Entity;
import main.game.model.entity.Team;
import main.game.model.entity.Unit;
import main.game.model.entity.unit.state.TargetItem;
import main.game.model.entity.unit.state.TargetMapPoint;
import main.game.model.entity.unit.state.TargetToAttack;
import main.game.model.entity.usable.Ability;
import main.game.model.entity.usable.Item;
import main.game.view.GameView;
import main.menu.controller.events.AbilityIconClick;
import main.menu.controller.events.ItemIconClick;
import main.menu.controller.events.KeyEvent;
import main.menu.controller.events.MouseClick;
import main.menu.controller.events.MouseDrag;
import main.menu.controller.events.UnitIconClick;

/**
 * Allows the user to control the game. Listens to user actions on the view
 * {@link GameView}, e.g.
 * mouse and keyboard input, and calls methods on the model to respond to the user input.
 *
 * @author Hrshikesh Arora
 */
public class DefaultGameController implements GameController {

  private final GameModel model;

  private Ability selectedAbility = null;

  private void selectUsable(Ability ability) {
    if (!ability.isReadyToBeUsed()) {
      throw new UsableStillInCoolDownException();
    }
    if (this.selectedAbility != null) {
      this.selectedAbility.setSelected(false);
    }
    ability.setSelected(true);
    this.selectedAbility = ability;
  }

  private void deselectUsable() {
    if (this.selectedAbility == null) {
      return;
    }

    this.selectedAbility.setSelected(false);
    this.selectedAbility = null;
  }

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
   * <li>
   *   LEFT click => Deselect all previously selectedAbility units and select only the clicked unit
   * </li>
   * <li>
   *   LEFT click + SHIFT => Add the clicked unit to the previously selectedAbility units
   * </li>
   * <li>
   *   LEFT click + CTRL => If the clicked unit is already selectedAbility, deselect it. Otherwise,
   *   select that unit.</li>
   * <li>
   *   RIGHT click => All selectedAbility units move to the clicked location
   * </li>
   * <li>
   *   RIGHT click on an enemy => All selectedAbility units will attack the enemy unit
   * </li>
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
        .sorted(Comparator.comparingDouble(
            s -> s.getCentre().distanceTo(mouseEvent.getLocation())))
        .findFirst().orElse(null);

    if (this.selectedAbility != null) {
      if (mouseEvent.wasLeft()) {
        if (selectedUnit != null
            && this.selectedAbility.canApplyTo(selectedUnit, model.getWorld())) {
          this.selectedAbility.use(model.getWorld(), selectedUnit);
        } else if (this.selectedAbility.canApplyTo(mouseEvent.getLocation(), model.getWorld())) {
          this.selectedAbility.use(model.getWorld(), mouseEvent.getLocation());
        }
      }
      this.deselectUsable();
      return;
    }

    //If it was a left click
    if (mouseEvent.wasLeft()) {
      if (selectedUnit != null && selectedUnit.getTeam() == Team.PLAYER) {
        if (mouseEvent.wasShiftDown()) {
          //add the new selectedAbility unit to the previously selectedAbility ones
          model.addToUnitSelection(selectedUnit);
        } else if (mouseEvent.wasCtrlDown()) {
          //if clicked unit already selectedAbility, deselect it. otherwise, select it
          Collection<Unit> updatedUnits = new ArrayList<>(model.getUnitSelection());

          if (updatedUnits.contains(selectedUnit)) {
            updatedUnits.remove(selectedUnit);
          } else {
            updatedUnits.add(selectedUnit);
          }
          model.setUnitSelection(updatedUnits);

        } else {
          //deselect all previous selectedAbility units and select the clicked unit
          model.setUnitSelection(Collections.singletonList(selectedUnit));
        }
      } else {
        model.setUnitSelection(new ArrayList<>());
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
          if (unit
              .getUnitType()
              .getBaseAttack()
              .getEffectedUnits(unit, model.getWorld(), selectedUnit)
              .size() != 0) {
            unit.setTarget(
                new TargetToAttack(unit, selectedUnit, unit.getUnitType().getBaseAttack()));
          }
        }
      } else if (selectedItem != null && closest instanceof Item) {
        // move all selectedAbility units to the clicked location
        for (Unit unit : model.getUnitSelection()) {
          unit.setTarget(new TargetItem(unit, selectedItem));
        }
        if (model.getUnitSelection().contains(model.getHeroUnit())) {
          model.getHeroUnit().setTarget(new TargetItem(model.getHeroUnit(), selectedItem));
        }
      } else {
        // move all selectedAbility units to the clicked location
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
   * <li>
   *   LEFT drag => Deselect all previously selectedAbility units and select only the units under
   *   the drag rectangle</li>
   * <li>
   *   LEFT drag + SHIFT => Add all units in the drag rectangle to the selectedAbility units
   * </li>
   * <li>
   *   LEFT drag + CTRL => Toggle all units in the drag rectangle. If the unit was selectedAbility,
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
      //add all units in the drag rectangle to the currently selectedAbility units
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
   * When a selectedAbility units icon is clicked in from the hud.
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
    this.selectUsable(clickEvent.getAbility());
  }

  /**
   * When a items icon that has being picked up by the hero is clicked in from the hud.
   */
  public void onItemIconClick(ItemIconClick clickEvent) {
    this.selectUsable(clickEvent.getItem().getAbility());
  }
}