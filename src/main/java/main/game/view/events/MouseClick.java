package main.game.view.events;

import main.util.MapPoint;

/**
 * Data class for when a Mouse Button is clicked on the GameView rather than the HUD.
 * @author Andrew McGhie
 */
public interface MouseClick {

  boolean wasLeft();

  MapPoint getLocation();
}
