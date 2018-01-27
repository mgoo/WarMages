package main.menu.controller.events;

import main.game.model.entity.usable.Ability;

/**
 * Data class for when a Item Icon is clicked.
 *
 * @author Andrew McGhie
 */
public interface ItemIconClick extends IconClick {

  Ability getItemAbility();
}
