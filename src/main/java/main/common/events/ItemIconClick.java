package main.common.events;

import main.common.entity.usable.Item;

/**
 * Data class for when a Item Icon is clicked.
 *
 * @author Andrew McGhie
 */
public interface ItemIconClick extends IconClick {

  Item getItem();
}
