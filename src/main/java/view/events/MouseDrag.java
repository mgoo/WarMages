package view.events;

/**
 * Data class for when the mouse has finished a drag on the game view.
 * @author Andrew McGhie
 */
public interface MouseDrag {

  boolean wasLeft();

  MapPoint getTopLeft();

  MapSize getSize();
}
