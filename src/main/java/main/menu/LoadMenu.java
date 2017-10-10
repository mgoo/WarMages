package main.menu;

import java.io.File;
import main.Main;
import main.common.WorldSaveModel;
import main.menu.controller.LoadMenuController;
import main.menu.generators.SaveFilesScriptGenerator;
import main.menu.generators.ScriptFileGenerator;

/**
 * The definitions of the file paths to the html file for the Load Game Menu.
 *
 * @author Andrew McGhie
 */
public class LoadMenu extends Menu {

  private final WorldSaveModel worldSaveModel;

  /**
   * Inject the dependencies.
   */
  public LoadMenu(Main main, MainMenu mainMenu, WorldSaveModel worldSaveModel) {
    super(main);
    this.worldSaveModel = worldSaveModel;
    this.menuController = new LoadMenuController(this.main, mainMenu, worldSaveModel);
  }

  @Override
  public String getHtml() {
    return this.fileToString(MenuFileResources.LOAD_MENU_HTML.getPath());
  }

  @Override
  public String getStyleSheetLocation() {
    return new File(MenuFileResources.LOAD_MENU_CSS.getPath()).toURI().toString();

  }

  /**
   * TODO use dependancy injection to pass in the jquery script generator
   * so it only has to load once .
   */
  @Override
  public String[] getScripts() {
    return new String[]{
        new ScriptFileGenerator()
            .setFile(MenuFileResources.JQUERY_JS.getPath())
            .getScript(),
        new SaveFilesScriptGenerator()
            .setData(this.worldSaveModel.getExistingGameSaves())
            .getScript()
    };
  }
}
