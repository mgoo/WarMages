package main.game.view.events;

import main.game.model.entity.Ability;

/**
 * Data class for when a Ability Icon is clicked.
 *
 * @author Andrew McGhie
 */
public interface AbilityIconClick {

  Ability getAbility();
}
