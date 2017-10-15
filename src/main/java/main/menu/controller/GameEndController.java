package main.menu.controller;

import main.Main;
import main.common.util.Config;
import main.menu.MainMenu;

/**
 * This is the controller for the menu displayed at the end of the game.
 * @author Andrew McGhie
 */
public class GameEndController extends MenuController {

  private final Main main;
  private final MainMenu mainMenu;
  private final Config config;

  public GameEndController(Main main, MainMenu mainMenu, Config config) {
    this.main = main;
    this.mainMenu = mainMenu;
    this.config = config;
  }

  /**
   * Loads the mainMenu.
   * TO be called from javascript
   */
  public void mainMenu() {
    try {
      this.main.loadMenu(mainMenu);
    } catch (Exception e) {
      if (this.config.isDebugMode()) {
        e.printStackTrace();
      } else {
        throw e; // fails silently in production
      }
    }
  }
}
