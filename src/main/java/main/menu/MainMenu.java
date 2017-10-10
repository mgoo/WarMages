package main.menu;

import java.io.File;
import java.util.function.Supplier;
import javafx.scene.image.ImageView;
import main.Main;
import main.common.WorldSaveModel;
import main.common.util.Config;
import main.common.util.looper.Looper;
import main.game.model.world.saveandload.WorldLoader;
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
   * Injects the dependencies.
   */
  public MainMenu(
      Main main,
      WorldLoader worldLoader,
      WorldSaveModel worldSaveModel,
      ImageView imageView,
      Config config
  ) {
    super(main);
    this.worldLoader = worldLoader;
    this.worldSaveModel = worldSaveModel;
    this.menuController = new MainMenuController(
        this.main,
        this,
        worldLoader,
        worldSaveModel,
        imageView,
        config
    );
  }

  @Override
  public String getHtml() {
    return this.fileToString(MenuFileResources.MAIN_MENU_HTML.getPath());
  }

  @Override
  public String getStyleSheetLocation() {
    return new File(MenuFileResources.MAIN_MENU_CSS.getPath()).toURI().toString();
  }

  /**
   * The main menu has no scripts.
   */
  @Override
  public String[] getScripts() {
    return new String[0];
  }
}
