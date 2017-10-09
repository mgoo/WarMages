package main.game.view.events;

import main.util.MapDiamond;
import main.util.MapPoint;
import main.util.MapSize;

/**
 * Data class for when the mouse has finished a drag on the game view.
 *
 * @author Andrew McGhie
 */
public interface MouseDrag {
  boolean wasShiftDown();

  boolean wasCtrlDown();

  MapDiamond getMapShape();
}
