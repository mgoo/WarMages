package main.game.view.events;

import main.common.util.MapPoint;

/**
 * Data class for when a Mouse Button is clicked on the GameView rather than the HUD.
 *
 * @author Andrew McGhie
 */
public interface MouseClick {

  boolean wasLeft();

  boolean wasShiftDown();

  boolean wasCtrlDown();

  MapPoint getLocation();
}
