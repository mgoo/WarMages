package main.game.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import main.common.WorldSaveModel;
import main.common.entity.Entity;
import main.common.exceptions.EntityOutOfBoundsException;
import main.common.exceptions.OverlappingMapEntitiesException;
import main.game.model.entity.DefaultMapEntity;
import main.game.model.entity.usable.Item;
import main.common.entity.MapEntity;
import main.common.entity.Team;
import main.common.entity.Unit;
import main.game.model.world.World;
import main.common.util.MapRect;

/**
 * Represent the {@link World} state.
 * @author chongdyla
 */
public class Level implements Serializable {

  private static final long serialVersionUID = 1L;

  private final Collection<DefaultMapEntity> borderEntities;
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
   * @param mapEntities E.g. trees, rocks, etc, that are in the middle of the map.
   * @param borderEntities Entities that should be removed when the level is complete. They
   *     only there to stop the user from moving out of bounds.
   */
  public Level(
      MapRect bounds,
      Collection<Unit> units,
      Collection<Item> items,
      Collection<MapEntity> mapEntities,
      Collection<DefaultMapEntity> borderEntities,
      Goal goal,
      String goalDescription
  ) {
    this.bounds = bounds;
    this.units = units;
    this.items = items;
    this.mapEntities = mapEntities;
    this.borderEntities = borderEntities;
    this.goal = goal;
    this.goalDescription = goalDescription;

    ensureNoMapEntitiesOverlap();
    ensureNoEntitiesOutOfBounds();
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

  public Collection<DefaultMapEntity> getBorderEntities() {
    return borderEntities;
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

  public Stream<Entity> allEntities() {
    return Stream.of(units, items, mapEntities, borderEntities)
        .flatMap(Collection::stream);
  }

  private void ensureNoMapEntitiesOverlap() {
    List<MapEntity> allMapEntities = Stream.of(mapEntities, borderEntities, items)
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
    List<MapEntity[]> overlappingPairs = new ArrayList<>();

    for (int i = 0; i < allMapEntities.size() - 1; i++) {
      MapEntity currentEntity = allMapEntities.get(i);

      for (int j = i + 1; j < allMapEntities.size(); j++) {
        MapEntity entityToCompareWith = allMapEntities.get(j);

        MapRect rectA = currentEntity.getRect();
        MapRect rectB = entityToCompareWith.getRect();

        if (rectA.overlapsWith(rectB)) {
          overlappingPairs.add(new MapEntity[]{currentEntity, entityToCompareWith});
        }
      }
    }

    if (!overlappingPairs.isEmpty()) {
      throw new OverlappingMapEntitiesException("Some MapEntities overlap: " + overlappingPairs);
    }
  }

  private void ensureNoEntitiesOutOfBounds() {
    Collection<Entity> outOfBoundsEntities = allEntities()
        .filter((entity) -> !bounds.contains(entity.getRect()))
        .collect(Collectors.toList());
    if (!outOfBoundsEntities.isEmpty()) {
      throw new EntityOutOfBoundsException("Entities out of bounds: " + outOfBoundsEntities);
    }
  }

  /**
   * Strategy for checking if the level is complete.
   * <p>
   * NOTE: No anonymous classes should be
   * created for this as this would break that the serialisation in {@link
   * WorldSaveModel}.
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
        // TODO ERIC write tests
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
