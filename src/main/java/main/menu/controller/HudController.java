package main.menu.controller;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import main.Main;
import main.game.model.entity.Unit;
import main.game.model.entity.usable.Ability;
import main.game.model.entity.usable.Item;
import main.game.view.GameView;
import main.menu.MainMenu;
import main.renderer.Renderer;

/**
 * Controls the hud when in game. Receives the click events when in game.
 *
 * @author Andrew McGhie
 */
public class HudController extends MenuController {

  final Main main;
  final MainMenu mainMenu;
  final GameView gameView;
  final Renderer renderer;
  final SaveFunction saveFunction;

  public HudController(Main main, MainMenu mainMenu, GameView gameView, Renderer renderer, SaveFunction saveFunction) {
    this.main = main;
    this.mainMenu = mainMenu;
    this.gameView = gameView;
    this.renderer = renderer;
    this.saveFunction = saveFunction;
  }

  /**
   * Triggers event for when the icon of an entity is clicked.
   */
  public void unitIconBtn(Unit unit) {
    try {
      // TODO event trigger here
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Triggers event for when the icon of an ability is clicked.
   */
  public void abilityIconBtn(Ability ability) {
    try {
      // TODO event trigger here
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Triggers event for when the icon of an item is clicked.
   */
  public void itemIconBtn(Item item) {
    try {
      // TODO event trigger here
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Triggers event for when Game View is clicked.
   */
  public void onLeftClick(int x, int y, boolean wasShiftDown, boolean wasCtrlDown) {
    try {
      gameView.onLeftClick(x, y, wasShiftDown, wasCtrlDown);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Triggers the drag event.
   * called from javascript
   */
  public void onDrag(int x1,
                     int y1,
                     int x2,
                     int y2,
                     boolean wasShiftDown,
                     boolean wasCtrlDown) {
    try {
      gameView.onDrag(x1, y1, x2, y2, wasShiftDown, wasCtrlDown);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Triggers event for when Game View is clicked.
   */
  public void onRightClick(int x, int y, boolean wasShiftDown, boolean wasCtrlDown) {
    try {
      gameView.onRightClick(x, y, wasShiftDown, wasCtrlDown);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onMouseMove(MouseEvent event) {
    gameView.updateMousePosition((int)event.getX(), (int)event.getY());
  }

  @Override
  public void onKeyDown(KeyEvent event) {
    gameView.onKeyDown(event.getCharacter().charAt(0),
        event.isShiftDown(),
        event.isControlDown());
  }

  /**
   * Handles when the ui enters a state that the game should pause.
   */
  public void pause() {
    try {
      gameView.pauseGame();
      renderer.pause();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Handles when the ui enters a state that the game should unpause.
   */
  public void resume() {
    try {
      gameView.resumeGame();
      renderer.resume();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Handles when the quit button was pressed.
   */
  public void quitBtn() {
    try {
      renderer.stop();
      gameView.stopGame();
      this.main.loadMenu(this.mainMenu);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * handles when the save button was pressed.
   */
  public void save(String filename) {
    try {
      System.out.println(filename);
      saveFunction.save(filename);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FunctionalInterface
  public interface SaveFunction {
    void save(String filename);
  }
}
