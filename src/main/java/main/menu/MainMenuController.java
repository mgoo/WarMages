package main.menu;

import main.game.model.world.saveandload.WorldLoader;
import main.game.model.world.saveandload.WorldSaveModel;

/**
 * Controller for the Main Menu. Responsible for makeing a new game loading a game and exiting.
 *
 * @author Andrew McGhie
 */
public class MainMenuController implements MenuController {

  private final WorldLoader worldLoader;
  private final WorldSaveModel worldSaveModel;

  MainMenuController(WorldLoader worldLoader, WorldSaveModel worldSaveModel) {
    this.worldLoader = worldLoader;
    this.worldSaveModel = worldSaveModel;
  }

  public void exit() {
    System.exit(0);
  }

  public void start() {

  }

  public void load(String filename) {

  }

  /**
   * Starts a new game from the begining.
   */
  public void start() {
    // TODO integration stuff here linking everything
  }

  /**
   * Starts a new game from the save file that was choosen.
   * @param fileName
   */
  public void load(String fileName) {
    // TODO integration stuff here linking everything
  }

  /**
   * Exits the program imediatly.
   */
  public void exit() {
    System.exit(0);
  }
}
