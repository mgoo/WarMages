package main.game.model;

import java.util.Collection;
import java.util.Collections;
import main.game.model.entity.Item;
import main.game.model.entity.MapEntity;
import main.game.model.entity.Unit;
import main.game.model.world.World;
import main.util.MapRect;

/**
 * Represent the {@link World} state.
 */
public class Level {

  private final CompletionChecker completionChecker;
  private final Collection<Unit> units;
  private final Collection<Item> items;
  private final Collection<MapEntity> mapEntities;
  private final MapRect mapBounds;
  private final String goalDescription;

  /**
   * Creates a new level with data which should not change overtime.
   */
  public Level(
      Collection<Unit> units,
      Collection<Item> items,
      Collection<MapEntity> mapEntities,
      CompletionChecker completionChecker,
      MapRect mapBounds,
      String goalDescription
  ) {
    this.units = units;
    this.items = items;
    this.mapEntities = mapEntities;
    this.completionChecker = completionChecker;
    this.mapBounds = mapBounds;
    this.goalDescription = goalDescription;
    throw new Error("NYI");
  }

  public Collection<Unit> getUnits() {
    return Collections.unmodifiableCollection(units);
  }

  public Collection<Item> getItems() {
    return Collections.unmodifiableCollection(items);
  }

  public Collection<MapEntity> getMapEntities() {
    return Collections.unmodifiableCollection(mapEntities);
  }

  public MapRect getMapBounds() {
    return mapBounds;
  }

  public String getGoalDescription() {
    return goalDescription;
  }

  /**
   * See CompletionChecker#areGoalsCompleted(GameModel).
   */
  public boolean areGoalsCompleted(GameModel gameModel) {
    throw new Error("NYI");
  }

  /**
   * Strategy for checking if the level is complete.
   */
  public interface CompletionChecker {

    /**
     * Checks if the user has achieved the goals to finish this level (for example by killing all
     * the enemies).
     */
    boolean isCompleted(GameModel gameModel);
  }
}
