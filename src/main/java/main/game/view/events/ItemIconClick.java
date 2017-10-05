package main.game.view.events;

import main.game.model.entity.usable.Item;

/**
 * Data class for when a Item Icon is clicked.
 *
 * @author Andrew McGhie
 */
public interface ItemIconClick {

  Item getItem();
}
