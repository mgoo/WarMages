package main.menu;

import java.net.MalformedURLException;
import java.net.URL;
import main.game.model.saveandload.GameModelLoader;
import main.game.model.saveandload.GameSaveModel;

/**
 * Wrapper for the MainMenu of the game. Includes play load and exit buttons
 *
 * @author Andrew McGhie
 */
public class MainMenu extends Menu {

  private final GameModelLoader gameModelLoader;
  private final GameSaveModel gameSaveModel;

  public MainMenu(GameModelLoader gameModelLoader, GameSaveModel gameSaveModel) {
    this.gameModelLoader = gameModelLoader;
    this.gameSaveModel = gameSaveModel;
  }

  @Override
  URL getUrl() {
    try {
      return new URL("resources/html/main_menu.html");
    } catch (MalformedURLException e) {
      throw new Error(e);
    }
  }

  @Override
  MenuController getMenuController() {
    return new MainMenuController(gameModelLoader, gameSaveModel);
  }
}
