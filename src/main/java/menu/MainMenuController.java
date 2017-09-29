package menu;

import main.game.model.world.saveandload.WorldLoader;
import main.game.model.world.saveandload.WorldSaveModel;
import main.menu.controller.MenuController;

/**
 * Controller for the Main Menu.
 * Responsible for makeing a new game loading a game and exiting.
 * @author Andrew McGhie
 */
public class MainMenuController implements MenuController {
  private final WorldLoader gameModelLoader;
  private final WorldSaveModel gameSaveModel;

  MainMenuController(WorldLoader gameModelLoader, WorldSaveModel gameSaveModel) {
    this.gameModelLoader = gameModelLoader;
    this.gameSaveModel = gameSaveModel;
  }
}
