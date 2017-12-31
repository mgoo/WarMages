package main.menu.controller.events;

import main.util.MapPoint;

/**
 * Data class for when a Mouse Button is clicked on the DefaultGameView rather than the HUD.
 *
 * @author Andrew McGhie
 */
public interface MouseClick {

  boolean wasLeft();

  boolean wasShiftDown();

  boolean wasCtrlDown();

  MapPoint getLocation();
}
