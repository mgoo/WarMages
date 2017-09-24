package main.menu;

import java.net.MalformedURLException;
import java.net.URL;
import main.game.model.saveandload.WorldLoader;
import main.game.model.saveandload.WorldSaveModel;

/**
 * Wrapper for the MainMenu of the game. Includes play load and exit buttons
 *
 * @author Andrew McGhie
 */
public class MainMenu extends Menu {

  private final WorldLoader worldLoader;
  private final WorldSaveModel worldSaveModel;

  public MainMenu(WorldLoader worldLoader, WorldSaveModel worldSaveModel) {
    this.worldLoader = worldLoader;
    this.worldSaveModel = worldSaveModel;
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
    return new MainMenuController(worldLoader, worldSaveModel);
  }
}
