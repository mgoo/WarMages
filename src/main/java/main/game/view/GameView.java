package main.game.view;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import main.game.controller.GameController;
import main.game.model.GameModel;
import main.game.model.entity.Entity;
import main.renderer.Renderable;

/**
 * A View of the Game. Is responsible for creating all the EntityRenderable objects and keeping their
 * locations uptodate with the locations in the models.
 *
 * @author Andrew McGhie
 */
public class GameView {

  private final GameController gameController;
  private final GameModel gameModel;

  private Collection<EntityRenderable> renderablesCache =
      new PriorityQueue<>(new RenderableComparator());

  public GameView(GameController gameController, GameModel gameModel) {
    this.gameController = gameController;
    this.gameModel = gameModel;

  }

  public Collection<Renderable> getRenderables() {
    // TODO
    return null;
  }

  private void updateRenderables() {
    final Set<EntityRenderable> toRemove = new HashSet<>();
    final Set<Entity> enitiesToCheck = new HashSet<>(this.gameModel.getAllEntities());

    EntityRenderable.lastTickTime = System.currentTimeMillis();
    EntityRenderable.nextTickTime = EntityRenderable.lastTickTime + 20;

    this.renderablesCache.forEach(renderable -> {
      if (!enitiesToCheck.contains(renderable.getEntity())) {
        toRemove.add(renderable);
      } else {
        enitiesToCheck.remove(renderable.getEntity());
      }
    });
    toRemove.forEach(renderable -> {
      this.renderablesCache.remove(renderable);
    });

    enitiesToCheck.forEach(entity -> {
      this.renderablesCache.add(new EntityRenderable(entity));
    });

    this.renderablesCache.forEach(entityRenderable -> {
      entityRenderable.update();
    });


  }


  private class RenderableComparator implements Comparator<Renderable> {

    @Override
    public int compare(Renderable r1, Renderable r2) {
      return (int) ((r1.getEffectiveEntityPosition().x + r1.getEffectiveEntityPosition().y) -
                (r2.getEffectiveEntityPosition().x + r2.getEffectiveEntityPosition().y));
    }
  }
}
