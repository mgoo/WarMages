package main.game.view.events;

import main.common.entity.usable.Ability;

/**
 * Data class for when a Ability Icon is clicked.
 *
 * @author Andrew McGhie
 */
public interface AbilityIconClick extends IconClick {

  Ability getAbility();
}
