package main.menu;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Main;
import main.common.WorldLoader;
import main.common.WorldSaveModel;
import main.menu.controller.MainMenuController;
import main.common.util.Config;
import main.menu.generators.SaveFilesScriptGenerator;
import main.menu.generators.ScriptFileGenerator;

/**
 * The definitions of the file paths to the html file for the Main Menu.
 *
 * @author Andrew McGhie
 */
public class MainMenu extends Menu {

  private final WorldSaveModel worldSaveModel;
  private final ImageView imageView;
  private final Config config;

  /**
   * Injects the dependencies.
   */
  public MainMenu(Main main,
                  WorldLoader worldLoader,
                  WorldSaveModel worldSaveModel,
                  ImageView imageView,
                  Config config) {
    super(main);
    this.worldSaveModel = worldSaveModel;
    this.imageView = imageView;
    this.config = config;
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
   * TODO use dependancy injection to pass in the jquery script generator
   * so it only has to load once .
   */
  @Override
  public String[] getScripts() {
    return new String[]{
        new ScriptFileGenerator()
            .setFile(MenuFileResources.JQUERY_JS.getPath())
            .getScript(),
        new ScriptFileGenerator()
            .setFile(MenuFileResources.BOOTSTRAP_JS.getPath())
            .getScript(),
        new ScriptFileGenerator()
            .setFile(MenuFileResources.FILE_SCRIPTS.getPath())
            .getScript(),
        new SaveFilesScriptGenerator()
            .setData(this.worldSaveModel.getExistingGameSaves())
            .getScript()
    };
  }

  @Override
  public void onLoad() {
    this.imageView.setImage(new Image(
        new File("resources/images/menu/titleArt.jpg").toURI().toString(),
        config.getContextScreenWidth(),
        config.getContextScreenHeight(),
        false,
        true
    ));
  }


  @Override
  public void onExit() {
    BufferedImage empty = new BufferedImage(config.getContextScreenWidth(),
        config.getContextScreenHeight(),
        BufferedImage.TYPE_4BYTE_ABGR);
    Graphics g = empty.getGraphics();
    g.setColor(config.getBaseFogOfWarColor());
    g.drawRect(0,0, empty.getWidth(), empty.getHeight());
    this.imageView.setImage(SwingFXUtils.toFXImage(empty, null));
  }
}
