package main.menu;

import java.io.File;
import main.Main;
import main.menu.controller.HudController;
import main.menu.generators.ScriptFileGenerator;

/**
 * The definitions of the file paths to the html file for the Heads Up Display.
 *
 * @author Andrew McGhie
 */
public class Hud extends Menu {

  public Hud(Main main, MainMenu mainMenu) {
    super(main);
    this.menuController = new HudController(main, mainMenu);
  }

  @Override
  public String getHtml() {
    return this.fileToString("resources/html/hud.html");
  }

  @Override
  public String getStyleSheetLocation() {
    return new File("resources/html/css/hud.css").toURI().toString();
  }

  @Override
  public String[] getScripts() {
    return new String[]{
      new ScriptFileGenerator().setFile("resources/html/js/jquery-3.2.1.min.js").getScript(),
      new ScriptFileGenerator().setFile("resources/html/js/hud.js").getScript()
    };
  }
}
