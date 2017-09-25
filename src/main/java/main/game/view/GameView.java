package main.game.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import main.game.controller.GameController;
import main.game.model.GameModel;
import main.game.model.entity.Entity;
import main.game.view.EntityRenderable.EntityRenderableComparator;
import main.renderer.Renderable;
import main.util.MapPoint;
import main.util.MapRect;

/**
 * A View of the Game.
 * Is responsible for creating all the EntityRenderable objects and keeping their
 * locations up-to-date with the locations in the models.
 *
 * @author Andrew McGhie
 */
public class GameView {

  public final static double scrollSpeed = 1;

  private final GameController gameController;
  private final GameModel gameModel;

  private MapRect viewBox;
  private MapPoint mousePosition;

  private List<EntityRenderable> renderablesCache =
      Collections.synchronizedList(new ArrayList<>());

  public GameView(GameController gameController, GameModel gameModel) {
    this.gameController = gameController;
    this.gameModel = gameModel;
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
    final Set<EntityRenderable> toRemove = new HashSet<>();
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
      this.renderablesCache.add(new EntityRenderable(entity));
    });

    this.renderablesCache.forEach(entityRenderable -> {
      entityRenderable.update(tickTime);
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
