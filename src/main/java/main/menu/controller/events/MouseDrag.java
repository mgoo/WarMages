package main.menu.controller.events;

import main.util.MapPolygon;

/**
 * Data class for when the mouse has finished a drag on the game view.
 *
 * @author Andrew McGhie
 */
public interface MouseDrag {
  boolean wasShiftDown();

  boolean wasCtrlDown();

  MapPolygon getMapShape();
}
