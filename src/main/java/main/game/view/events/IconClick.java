package main.game.view.events;

/**
 * Parent for dataclasses when an icon is clicked.
 *
 * @author Andrew McGhie
 */
public interface IconClick {

  boolean wasShiftDown();

  boolean wasCtrlDown();
}
