package main.game.view.events;

import main.common.entity.Entity;
import main.common.entity.Unit;

/**
 * Data class for when a Entity Icon is clicked.
 *
 * @author Andrew McGhie
 */
public interface UnitIconClick extends IconClick {

  Unit getUnit();
}
