package main.menu;

/**
 * Controller for the Main Menu.
 * Responsible for makeing a new game loading a game and exiting.
 * @author Andrew McGhie
 */
public class MainMenuController implements MenuController {
  private final GameModelLoader gameModelLoader;
  private final GameSaveModel gameSaveModel;

  MainMenuController(GameModelLoader gameModelLoader, GameSaveModel gameSaveModel) {
    this.gameModelLoader = gameModelLoader;
    this.gameSaveModel = gameSaveModel;
  }
}
