package main.menu;

import java.io.File;
import java.io.IOException;
import main.Main;
import main.game.model.world.saveandload.WorldSaveModel;
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
    return this.fileToString("resources/html/load_menu.html");
  }

  @Override
  public String getStyleSheetLocation() {
    return new File("resources/html/css/load_menu.css").toURI().toString();

  }

  /**
   * TODO use dependancy injection to pass in the jquery script generator
   * so it only has to load once .
   */
  @Override
  public String[] getScripts() {
    try {
      return new String[]{
          new ScriptFileGenerator().setFile("resources/html/js/jquery-3.2.1.min.js").getScript(),
          new SaveFilesScriptGenerator()
              .setData(this.worldSaveModel.getExistingGameSaves())
              .getScript()
      };
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new String[0];
  }
}
