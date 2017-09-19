package view;

/**
 * A View of the Game.
 * Is responsible for creating all the Renderable objects and keeping their
 * locations uptodate with the locations in the models.
 * @author Andrew McGhie
 */
public class GameView {
  private final GameController gameController;
  private final GameModel gameModel;

  public GameView(GameController gameController, GameModel gameModel) {
    this.gameController = gameController;
    this.gameModel = gameModel;
  }

  public Collection<Renderable> getRenderables() {
    // TODO
    return null;
  }
}
