package main.menu;

import main.game.model.saveandload.WorldLoader;
import main.game.model.saveandload.GameSaveModel;

/**
 * Controller for the Main Menu. Responsible for makeing a new game loading a game and exiting.
 *
 * @author Andrew McGhie
 */
public class MainMenuController implements MenuController {

  private final WorldLoader worldLoader;
  private final GameSaveModel gameSaveModel;

  MainMenuController(WorldLoader worldLoader, GameSaveModel gameSaveModel) {
    this.worldLoader = worldLoader;
    this.gameSaveModel = gameSaveModel;
  }
}
