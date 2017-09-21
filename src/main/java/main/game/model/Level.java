package main.game.model;

import java.util.Collection;
import main.game.model.entity.Item;
import main.game.model.entity.Unit;
import main.game.model.world.World;
import main.util.MapRect;

/**
 * Represent the {@link World} state.
 */
public class Level {

  public Level(
      Collection<Unit> units,
      Collection<Item> items,
      CompletionChecker completionChecker,
      MapRect mapBounds
  ) {
    throw new Error("NYI");
  }

  // TODO add getters

  /**
   * See CompletionChecker#isCompleted(GameModel).
   */
  public boolean isCompleted(GameModel gameModel) {
    throw new Error("NYI");
  }

  /**
   * Strategy pattern for checking if the level is complete.
   */
  public interface CompletionChecker {

    /**
     * Checks if the user has achieved the goals to finish this level (for example by killing all
     * the enemies).
     */
    boolean isCompleted(GameModel gameModel);
  }
}
