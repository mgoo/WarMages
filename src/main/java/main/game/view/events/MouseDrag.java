package main.game.view.events;

import main.util.MapPoint;
import main.util.MapSize;

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
