package main.game.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import main.game.controller.GameController;
import main.game.model.GameModel;
import main.game.model.entity.Entity;
import main.game.view.EntityRenderable.EntityRenderableComparator;
import main.renderer.Renderable;
import main.util.MapPoint;
import main.util.MapRect;

/**
 * A View of the Game. Is responsible for creating all the EntityRenderable objects and keeping their
 * locations uptodate with the locations in the models.
 *
 * @author Andrew McGhie
 */
public class GameView {

  private final GameController gameController;
  private final GameModel gameModel;

  private MapRect viewBox;

  private List<EntityRenderable> renderablesCache =
      Collections.synchronizedList(new ArrayList<>());

  public GameView(GameController gameController, GameModel gameModel) {
    this.gameController = gameController;
    this.gameModel = gameModel;
  }

  /**
   * Gets a sorted unmodifible list of renderables.
   * @param currentTime the time stap for the render iteration
   * @return
   */
  public synchronized List<Renderable> getRenderables(long currentTime) {
    this.renderablesCache.sort(new EntityRenderableComparator(currentTime));
    return Collections.unmodifiableList(this.renderablesCache);
  }

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
}
