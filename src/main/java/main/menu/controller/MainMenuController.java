package main.menu.controller;

import java.io.IOException;
import javafx.scene.image.ImageView;
import main.Main;
import main.common.util.Looper;
import main.common.GameController;
import main.game.controller.DefaultGameController;
import main.game.model.GameModel;
import main.game.model.world.World;
import main.common.WorldLoader;
import main.common.WorldSaveModel;
import main.game.view.GameView;
import main.game.view.events.MouseClick;
import main.images.DefaultImageProvider;
import main.common.images.ImageProvider;
import main.menu.Hud;
import main.menu.MainMenu;
import main.renderer.Renderer;
import main.common.util.Config;
import main.common.util.Event;
import main.common.util.Events.MainGameTick;

/**
 * Controller for the Main Menu. Responsible for making a new game loading a game and exiting.
 *
 * @author Andrew McGhie
 */
public class MainMenuController extends MenuController {

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
   * Loads a game from the file.
   * @param filename the path to the file to load from.
   */
  public void loadBtn(String filename) {
    try {
      System.out.println(filename);
      World world = this.worldSaveModel.load(filename);
      this.startGame(world);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Starts a new game from the beginning.
   */
  public void startBtn() {
    try {
      World world = this.worldLoader.load();
      this.startGame(world);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void startGame(World world) {
    ImageProvider imageProvider = new DefaultImageProvider();
    MainGameTick tickEvent = new MainGameTick();
    Event<MouseClick> mouseClickEvent = new Event<>();
    GameModel gameModel = new GameModel(world, tickEvent);
    GameController gameController = new DefaultGameController(gameModel);
    GameView gameView = new GameView(this.config,
        gameController,
        gameModel,
        imageProvider,
        mouseClickEvent,
        world);
    tickEvent.registerListener(parameter -> gameView.onTick(parameter));
    mouseClickEvent.registerListener(parameter -> gameController.onMouseEvent(parameter));
    Renderer renderer = new Renderer(gameView, this.imageView, config, new Looper());
    Hud hud = new Hud(this.main,
        this.mainMenu,
        gameView,
        renderer,
        gameModel,
        imageProvider,
        filename -> {
          this.worldSaveModel.save(world, filename);
        });
    tickEvent.registerListener(parameter -> hud.updateIcons());
    tickEvent.registerListener(parameter -> hud.updateGoal(world.getCurrentGoalDescription()));
    tickEvent.registerListener(parameter -> world.tick(config.getGameModelDelay()));
    renderer.start();
    gameModel.startGame();

    this.main.loadMenu(hud);
  }

  /**
   * Exits the program imediatly.
   */
  public void exitBtn() {
    System.exit(0);
  }
}
