package main.common;

import java.util.List;
import main.common.entity.Unit;
import main.common.entity.usable.Ability;
import main.common.entity.usable.Item;
import main.common.util.MapRect;
import main.game.view.EntityView;

/**
 * Interface for Game View.
 *
 * @author Andrew McGhie
 */
public interface GameView {

  /**
   * Gets a list of the renderables.
   *
   * <p><b>N.B.</b> sorts the list so try to minimise calls.</p>
   * @param currentTime the time stap for the render iteration
   * @return unmodifiable sorted list
   */
  List<EntityView> getRenderables(long currentTime);

  Renderable getFogOfWarView();

  Renderable getBackGroundView();

  /**
   * Ticks the gameView and also checks if game is completed.
   * @param timeSinceLastTick time since last tick
   */
  void onTick(Long timeSinceLastTick);

  MapRect getViewBox();

  void updateMousePosition(int x, int y);

  /**
   * Triggers event for when Game View is clicked.
   */
  void onLeftClick(int x, int y, boolean wasShiftDown, boolean wasCtrlDown);

  /**
   * Triggers the drag event.
   */
  void onDrag(int x1, int y1, int x2, int y2, boolean wasShiftDown, boolean wasCtrlDown);

  /**
   * Triggers event for when Game View is clicked.
   */
  void onRightClick(int x, int y, boolean wasShiftDown, boolean wasCtrlDown);

  /**
   * Triggers the double click event.
   */
  void onDbClick(int x, int y, boolean wasShiftDown, boolean wasCtrlDown);

  /**
   * Triggers a key event.
   */
  void onKeyDown(char key, boolean wasShiftDown, boolean wasCtrlDown);

  /**
   * Triggers the event when a units icon is clicked.
   */
  void unitClick(
      Unit unit,
      boolean wasShiftDown,
      boolean wasCtrlDown,
      boolean wasLeftClick
  );

  /**
   * Triggers the event when a abilities icon is clicked.
   */
  void abilityClick(
      Ability ability,
      boolean wasShiftDown,
      boolean wasCtrlDown,
      boolean wasLeftClick
  );

  /**
   * Triggers the event when a item icon is clicked.
   */
  void itemClick(
      Item item,
      boolean wasShiftDown,
      boolean wasCtrlDown,
      boolean wasLeftClick
  );

  /**
   * Pauses the main game loop inside model.
   */
  void pauseGame();

  /**
   * Resumes the main game loop inside model.
   */
  void resumeGame();

  /**
   * Stops the main game loop inside model.
   */
  void stopGame();
}
