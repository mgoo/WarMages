package main.game.view.events;

import main.game.model.entity.Entity;

/**
 * Data class for when a Entity Icon is clicked.
 * @author Andrew McGhie
 */
public interface EntityIconClick {
  Entity getEntity();
}
