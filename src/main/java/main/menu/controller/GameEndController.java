package main.menu.controller;

import main.Main;
import main.common.menu.MenuController;
import main.menu.MainMenu;

/**
 * This is the controller for the menu displayed at the end of the game.
 * @author Andrew McGhie
 */
public class GameEndController extends MenuController {

  private final Main main;
  private final MainMenu mainMenu;

  public GameEndController(Main main, MainMenu mainMenu) {
    this.main = main;
    this.mainMenu = mainMenu;
  }

  /**
   * Loads the mainMenu.
   * TO be called from javascript
   */
  public void mainMenu() {
    try {
      this.main.loadMenu(mainMenu);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
