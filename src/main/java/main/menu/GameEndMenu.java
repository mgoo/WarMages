package main.menu;

import java.io.File;
import main.Main;
import main.common.menu.Menu;
import main.common.menu.MenuFileResources;
import main.common.util.Config;
import main.menu.controller.GameEndController;
import main.menu.generators.ScriptFileGenerator;

/**
 * This menu is displayed when the game finishes.
 *
 * @author Andrew McGhie
 */
public class GameEndMenu extends Menu {

  private final MainMenu mainMenu;
  private final Main main;
  private final String message;



  public GameEndMenu(Main main,  MainMenu mainMenu, String message, Config config) {
    this.main = main;
    this.message = message;
    this.mainMenu = mainMenu;
    this.menuController = new GameEndController(main, mainMenu, config);
  }

  @Override
  public String getHtml() {
    return this.fileToString(MenuFileResources.GAME_END_HTML.getPath());
  }

  @Override
  public String getStyleSheetLocation() {
    return new File(MenuFileResources.GAME_END_CSS.getPath()).toURI().toString();
  }

  @Override
  public String[] getScripts() {
    return new String[] {
        new ScriptFileGenerator()
            .setFile(MenuFileResources.JQUERY_JS.getPath())
            .getScript(),
        new ScriptFileGenerator()
            .setFile(MenuFileResources.BOOTSTRAP_JS.getPath())
            .getScript(),
        new ScriptFileGenerator()
            .setFile(MenuFileResources.GAME_END_JS.getPath())
            .getScript(),
        "setTitle('" + message + "');"
    };
  }

  @Override
  public void onLoad() {
    // Do Nothing
  }
}
