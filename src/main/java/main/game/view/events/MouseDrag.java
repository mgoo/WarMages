package main.game.view.events;

import main.common.util.MapPoint;
import main.common.util.MapSize;

/**
 * Data class for when the mouse has finished a drag on the game view.
 *
 * @author Andrew McGhie
 */
public interface MouseDrag {

  boolean wasLeft();

  boolean wasShiftDown();

  boolean wasCtrlDown();

  MapPoint getTopLeft();

  MapSize getSize();
}
