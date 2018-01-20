package main.menu.controller;

import javafx.scene.image.ImageView;
import main.Main;
import main.game.controller.DefaultGameController;
import main.game.controller.GameController;
import main.game.model.DefaultGameModel;
import main.game.model.GameModel;
import main.game.model.world.World;
import main.game.model.world.saveandload.WorldLoader;
import main.game.model.world.saveandload.WorldSaveModel;
import main.game.view.GameView;
import main.images.DefaultImageProvider;
import main.images.ImageProvider;
import main.menu.GameEndMenu;
import main.menu.Hud;
import main.menu.MainMenu;
import main.renderer.DefaultRenderer;
import main.renderer.Renderer;
import main.util.Config;
import main.util.Events.GameLost;
import main.util.Events.GameWon;
import main.util.Events.MainGameTick;
import main.util.Looper;

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
      World world = this.worldSaveModel.load(filename);
      this.startGame(world);
    } catch (Exception e) {
      if (this.config.isDebugMode()) {
        e.printStackTrace();
      } else {
        throw new RuntimeException(e); // fails silently in production
      }
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
      if (this.config.isDebugMode()) {
        e.printStackTrace();
      } else {
        throw e; // fails silently in production
      }
    }
  }

  private void startGame(World world) {
    ImageProvider imageProvider = new DefaultImageProvider();
    MainGameTick tickEvent = new MainGameTick();
    GameWon wonEvent = new GameWon();
    GameLost lostEvent = new GameLost();
    GameModel gameModel = new DefaultGameModel(world, tickEvent, wonEvent, lostEvent);
    GameController gameController = new DefaultGameController(gameModel);
    GameView gameView = new GameView(this.config,
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
        filename -> this.worldSaveModel.save(world, filename),
        config
    );
    tickEvent.registerListener(parameter -> hud.updateIcons());
    tickEvent.registerListener(parameter -> hud.updateGoal(world.getCurrentGoalDescription()));
    tickEvent.registerListener(parameter -> world.tick(config.getGameModelDelay()));
    wonEvent.registerListener(parameter -> {
      gameModel.stopGame();
      renderer.stop();
      this.main.loadMenu(new GameEndMenu(this.main,
          this.mainMenu,
          "You have Won",
          config));
    });
    lostEvent.registerListener(parameter -> {
      gameModel.stopGame();
      renderer.stop();
      this.main.loadMenu(new GameEndMenu(this.main,
          this.mainMenu,
          "You have lost",
          config));
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
