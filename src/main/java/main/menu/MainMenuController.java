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
}
