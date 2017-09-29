package main.menu;

import java.io.File;
import javafx.scene.image.ImageView;
import main.Main;
import main.game.model.world.saveandload.WorldLoader;
import main.game.model.world.saveandload.WorldSaveModel;
import main.menu.controller.MainMenuController;

/**
 * The definitions of the file paths to the html file for the Main Menu.
 *
 * @author Andrew McGhie
 */
public class MainMenu extends Menu {

  private final WorldLoader worldLoader;
  private final WorldSaveModel worldSaveModel;

  /**
   * Injects the decencies.
   */
  public MainMenu(Main main,
                  WorldLoader worldLoader,
                  WorldSaveModel worldSaveModel,
                  ImageView imageView) {
    super(main);
    this.worldLoader = worldLoader;
    this.worldSaveModel = worldSaveModel;
    this.menuController = new MainMenuController(this.main,
        this, worldLoader,
        worldSaveModel,
        imageView);
  }

  @Override
  public String getHtml() {
    return this.fileToString("resources/html/main_menu.html");
  }

  @Override
  public String getStyleSheetLocation() {
    return new File("resources/html/css/main_menu.css").toURI().toString();

  }

  /**
   * The main menu has no scripts.
   */
  @Override
  public String[] getScripts() {
    return new String[0];
  }
}
