package main.menu.controller;

import main.Main;
import main.common.WorldSaveModel;
import main.menu.MainMenu;

/**
 * Controls the load from save menu.
 *
 * @author Andrew McGhie
 */
public class LoadMenuController extends MenuController {

  private final Main main;
  private final MainMenu mainMenu;
  private final WorldSaveModel worldSaveModel;

  /**
   * Inject the dependencies.
   * @param main the webengine wrapper to load stuff to
   * @param mainMenu the menu to go back to
   * @param worldSaveModel model to load from
   */
  public LoadMenuController(Main main, MainMenu mainMenu, WorldSaveModel worldSaveModel) {
    this.main = main;
    this.mainMenu = mainMenu;
    this.worldSaveModel = worldSaveModel;
  }

  /**
   * Loads a game from the file.
   * @param filename the path to the file to load from.
   */
  public void loadFile(String filename) {
    try {
      this.worldSaveModel.load(filename);
      // TODO handle loading a game from a file from here
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * When the button for going back to the main menu is pressed.
   */
  public void backBtn() {
    this.main.loadMenu(mainMenu);
  }
}
