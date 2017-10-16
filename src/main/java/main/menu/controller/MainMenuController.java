package main.menu.controller;

import javafx.scene.image.ImageView;
import main.Main;
import main.common.GameView;
import main.common.menu.MenuController;
import main.common.util.Events.GameLost;
import main.common.util.Events.GameWon;
import main.common.util.Looper;
import main.common.GameController;
import main.game.controller.DefaultGameController;
import main.game.model.DefaultGameModel;
import main.common.GameModel;
import main.common.World;
import main.common.WorldLoader;
import main.common.WorldSaveModel;
import main.common.events.MouseClick;
import main.game.view.DefaultGameView;
import main.images.DefaultImageProvider;
import main.common.images.ImageProvider;
import main.menu.GameEndMenu;
import main.menu.Hud;
import main.menu.MainMenu;
import main.common.Renderer;
import main.common.util.Config;
import main.common.util.Event;
import main.common.util.Events.MainGameTick;
import main.renderer.DefaultRenderer;

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
      System.out.println(filename); //TODO remove this?
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
    GameWon wonEvent = new GameWon();
    GameLost lostEvent = new GameLost();
    GameModel gameModel = new DefaultGameModel(world, tickEvent, wonEvent, lostEvent);
    GameController gameController = new DefaultGameController(gameModel);
    GameView gameView = new DefaultGameView(this.config,
        gameController,
        gameModel,
        imageProvider,
        world);
    tickEvent.registerListener(gameView::onTick);
    Renderer renderer = new DefaultRenderer(gameView, this.imageView, config, new Looper());
    Hud hud = new Hud(this.main,
        this.mainMenu,
        gameView,
        renderer,
        gameModel,
        imageProvider,
        filename -> this.worldSaveModel.save(world, filename)
    );
    tickEvent.registerListener(parameter -> hud.updateIcons());
    tickEvent.registerListener(parameter -> hud.updateGoal(world.getCurrentGoalDescription()));
    tickEvent.registerListener(parameter -> world.tick(config.getGameModelDelay()));
    wonEvent.registerListener(parameter -> {
      gameModel.stopGame();
      renderer.stop();
      this.main.loadMenu(new GameEndMenu(this.main, this.mainMenu, "You have Won"));
    });
    lostEvent.registerListener(parameter -> {
      gameModel.stopGame();
      renderer.stop();
      this.main.loadMenu(new GameEndMenu(this.main, this.mainMenu, "YOU LOST YOUR BAD HAHA"));
    });
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
