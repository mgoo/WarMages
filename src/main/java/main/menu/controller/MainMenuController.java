package main.menu.controller;

import javafx.scene.image.ImageView;
import main.Main;
import main.game.controller.GameController;
import main.game.model.GameModel;
import main.game.model.world.World;
import main.game.model.world.saveandload.WorldLoader;
import main.game.model.world.saveandload.WorldSaveModel;
import main.game.view.GameView;
import main.images.DefaultImageProvider;
import main.menu.Hud;
import main.menu.LoadMenu;
import main.menu.MainMenu;
import main.renderer.Renderer;
import main.util.Config;
import main.util.Events.MainGameTick;

/**
 * Controller for the Main Menu. Responsible for making a new game loading a game and exiting.
 *
 * @author Andrew McGhie
 */
public class MainMenuController implements MenuController {

  private final WorldLoader worldLoader;
  private final WorldSaveModel worldSaveModel;
  private final MainMenu mainMenu;
  private final Main main;
  private final ImageView imageView;
  private final Config config;

  /**
   * inject the dependencies.
   */
  public MainMenuController(Main main,
                            MainMenu mainMenu,
                            WorldLoader worldLoader,
                            WorldSaveModel worldSaveModel,
                            ImageView imageView,
                            Config config) {
    this.main = main;
    this.mainMenu = mainMenu;
    this.worldLoader = worldLoader;
    this.worldSaveModel = worldSaveModel;
    this.imageView = imageView;
    this.config = config;
  }

  /**
   * Starts a new game from the beginning.
   * TODO do integration properly
   */
  public void startBtn() {
    try {
      World world = this.worldLoader.load();
      GameModel gameModel = new GameModel(world, new MainGameTick());
      GameController gameController = new GameController(gameModel);
      GameView gameView = new GameView(this.config,
          gameController,
          gameModel,
          new DefaultImageProvider());
      new Renderer(gameView, this.imageView);
      // TODO start the game properly

      this.main.loadMenu(new Hud(this.main, this.mainMenu));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Loads the load menu.
   */
  public void loadBtn() {
    try {
      this.main.loadMenu(new LoadMenu(this.main, this.mainMenu, this.worldSaveModel));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Exits the program imediatly.
   */
  public void exitBtn() {
    System.exit(0);
  }
}
