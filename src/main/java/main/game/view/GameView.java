package main.game.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import main.game.controller.GameController;
import main.game.model.GameModel;
import main.game.model.entity.Entity;
import main.game.view.EntityView.EntityRenderableComparator;
import main.renderer.Renderable;
import main.util.Config;
import main.util.MapPoint;
import main.util.MapRect;

/**
 * A View of the Game.
 * Is responsible for creating all the EntityView objects and keeping their
 * locations up-to-date with the locations in the models.
 *
 * @author Andrew McGhie
 */
public class GameView {

  private final Config config;

  private final GameController gameController;
  private final GameModel gameModel;

  private MapRect viewBox;
  private MapPoint mousePosition;

  private List<EntityView> renderablesCache =
      Collections.synchronizedList(new ArrayList<>());

  /**
   * Constructor for game view sets the viewBox to start at origin 0,0.
   * @param config screen width and height must be set here
   */
  public GameView(Config config, GameController gameController, GameModel gameModel) {
    this.config = config;
    this.gameController = gameController;
    this.gameModel = gameModel;
    this.viewBox = new MapRect(0, 0,
        this.config.getContextScreenWidth(), this.config.getContextScreenHeight());
  }

  /**
   * Gets a list of the renderables.
   *
   * <p><b>N.B.</b> sorts the list so try to minimise calls.</p>
   * @param currentTime the time stap for the render iteration
   * @return unmodifiable sorted list
   */
  public synchronized List<Renderable> getRenderables(long currentTime) {
    this.renderablesCache.sort(new EntityRenderableComparator(currentTime));
    return Collections.unmodifiableList(this.renderablesCache);
  }

  /**
   * called when the Main Game Loop ticks. It updates the current renderables.
   * @param tickTime the time that the tick happened.
   */
  public synchronized void updateRenderables(long tickTime) {
    final Set<EntityView> toRemove = new HashSet<>();
    final Set<Entity> enitiesToCheck = new HashSet<>(this.gameModel.getAllEntities());

    this.renderablesCache.forEach(renderable -> {
      if (!enitiesToCheck.contains(renderable.getEntity())) {
        toRemove.add(renderable);
      } else {
        enitiesToCheck.remove(renderable.getEntity());
      }
    });

    this.renderablesCache.removeAll(toRemove);

    enitiesToCheck.forEach(entity -> {
      this.renderablesCache.add(new EntityView(this.config, entity));
    });

    this.renderablesCache.forEach(entityView -> {
      entityView.update(tickTime);
    });


  }

  public MapRect getViewBox() {
    return this.viewBox;
  }

  public void moveViewBox(double x, double y) {
    this.viewBox = this.viewBox.move(x, y);
  }

  public void updateMousePosition(int x, int y) {
    this.mousePosition = new MapPoint(x, y);
  }
}
