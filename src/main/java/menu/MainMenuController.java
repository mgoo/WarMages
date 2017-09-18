package menu;

/**
 * Created by mgoo on 18/09/17.
 */
public class MainMenuController implements MenuController {
  private final GameModelLoader gameModelLoader;
  private final GameSaveModel gameSaveModel;

  MainMenuController(GameModelLoader gameModelLoader, GameSaveModel gameSaveModel) {
    this.gameModelLoader = gameModelLoader;
    this.gameSaveModel = gameSaveModel;
  }
}
