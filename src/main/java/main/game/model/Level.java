package main.game.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import main.game.model.entity.Entity;
import main.game.model.entity.Item;
import main.game.model.entity.MapEntity;
import main.game.model.entity.Team;
import main.game.model.entity.Unit;
import main.game.model.world.World;
import main.util.MapRect;

/**
 * Represent the {@link World} state.
 */
public class Level implements Serializable {

  private static final long serialVersionUID = 1L;

  private final Goal goal;
  private final MapRect bounds;
  private final Collection<Unit> units;
  private final Collection<Item> items;
  private final Collection<MapEntity> mapEntities;
  private final String goalDescription;

  /**
   * Creates a new level with data which should not change overtime.
   *
   * @param bounds All entities must be contained within this levelBounds.
   * @param borderEntities Entities that should be removed when the level is complete. They
   *     only there to stop the user from moving out of bounds.
   */
  public Level(
      MapRect bounds,
      Collection<Unit> units,
      Collection<Item> items,
      Collection<MapEntity> mapEntities,
      Collection<MapEntity> borderEntities,
      Goal goal,
      String goalDescription
  ) {
    this.bounds = bounds;
    this.units = units;
    this.items = items;
    this.mapEntities = mapEntities;
    this.goal = goal;
    this.goalDescription = goalDescription;

    Collection<Entity> outOfBoundsEntities = findOutOfBoundsEntities(bounds);
    if (!outOfBoundsEntities.isEmpty()) {
      throw new IllegalArgumentException("Items out of bounds: " + outOfBoundsEntities);
    }
  }

  public MapRect getBounds() {
    return bounds;
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

  public String getGoalDescription() {
    return goalDescription;
  }

  /**
   * See {@link Goal#isCompleted(Level)}.
   */
  public boolean areGoalsCompleted() {
    return goal.isCompleted(this);
  }

  private Collection<Entity> findOutOfBoundsEntities(MapRect bounds) {
    return Stream.of(units, items, mapEntities)
        .flatMap(Collection::stream)
        .filter((entity) -> !bounds.contains(new MapRect(
            entity.getPosition(),
            entity.getSize()
        )))
        .collect(Collectors.toList());
  }

  /**
   * Strategy for checking if the level is complete.
   * <p>
   * NOTE: No anonymous classes should be
   * created for this as this would break that the serialisation in {@link
   * main.game.model.world.saveandload.WorldSaveModel}.
   * </p>
   */
  public interface Goal extends Serializable {

    /**
     * Checks if the user has achieved the goals to finish this level (for example by killing all
     * the enemies).
     *
     * @param level The level that contains this {@link Goal}.
     */
    boolean isCompleted(Level level);

    class AllEnemiesKilled implements Goal {

      private static final long serialVersionUID = 1L;

      @Override
      public boolean isCompleted(Level level) {
        // TODO write tests
        Collection<Team> enemies = Team.PLAYER.getEnemies();
        return level.getUnits()
            .stream()
            .filter(Unit.class::isInstance)
            .map(Unit.class::cast)
            .filter(unit -> enemies.contains(unit.getTeam())) // find enemy units
            .allMatch(unit -> unit.getHealth() == 0);
      }

    }
  }
}
