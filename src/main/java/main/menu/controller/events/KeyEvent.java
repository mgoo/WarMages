package main.menu.controller.events;

/**
 * Data class for when a Key is pressed.
 *
 * @author Andrew McGhie
 */
public interface KeyEvent {

  char getKey();

  boolean wasShiftDown();

  boolean wasCtrlDown();
}
