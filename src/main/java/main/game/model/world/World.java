package main.game.model.world;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import main.game.model.Level;
import main.common.entity.Entity;
import main.common.entity.HeroUnit;
import main.common.entity.MapEntity;
import main.game.model.entity.Projectile;
import main.common.entity.Unit;
import main.common.entity.usable.Item;
import main.common.util.MapPoint;
import main.common.PathFinder;

/**
 * World class is a representation of all the in-play entities and in-play entities: all entity
 * objects that have been instantiated.
 */
public class World implements Serializable {

  private static final long serialVersionUID = 1L;

  private final List<Level> levels;
  private final HeroUnit heroUnit;
  private final Set<Unit> units;
  private final Set<Unit> recentlyKilledUnits;
  private final Set<Item> items;
  private final Set<MapEntity> mapEntities;
  private final Set<Projectile> projectiles;

  private final PathFinder pathFinder;

  /**
   * Creates the world.
   *
   * @param levels The levels sorted from start to finish. The first level is the initial level.
   * @param heroUnit The hero unit used throughout the whole game.
   */
  public World(List<Level> levels, HeroUnit heroUnit, PathFinder pathfinder) {
    Objects.requireNonNull(levels);
    Objects.requireNonNull(heroUnit);
    if (levels.isEmpty()) {
      throw new IllegalArgumentException("Cannot have empty levels");
    }
    this.heroUnit = heroUnit;
    this.levels = new ArrayList<>(levels);
    this.units = newConcurrentSetOf(currentLevel().getUnits());
    this.recentlyKilledUnits = newConcurrentSet();
    this.items = newConcurrentSetOf(currentLevel().getItems());
    this.mapEntities = newConcurrentSetOf(currentLevel().getMapEntities());
    this.mapEntities.addAll(currentLevel().getBorderEntities());
    this.projectiles = newConcurrentSet();
    this.pathFinder = pathfinder;
  }

  private <T> Set<T> newConcurrentSetOf(Collection<T> collection) {
    Set<T> set = newConcurrentSet();
    set.addAll(collection);
    return set;
  }

  private <T> Set<T> newConcurrentSet() {
    return Collections.newSetFromMap(new ConcurrentHashMap<>());
  }

  private Level currentLevel() {
    if (levels.isEmpty()) {
      throw new IllegalStateException("No levels left");
    }
    return levels.get(0);
  }

  /**
   * A getter method to get all possible units of type=PLAYER.
   *
   * @return a collection of all possible player units
   */
  public Collection<Unit> getAllUnits() {
    ArrayList<Unit> allUnits = new ArrayList<>(units);
    allUnits.add(heroUnit);
    return Collections.unmodifiableList(allUnits);
  }

  /**
   * Gets all entities in the world (including map entities, units, projectiles and other entities.
   *
   * @return an unmodifiable collection of all Entities in the world.
   */
  public Collection<Entity> getAllEntities() {
    return Collections.unmodifiableCollection(
        new ArrayList<Entity>() {
          {
            addAll(units);
            add(heroUnit);
            addAll(mapEntities);
            addAll(items);
            addAll(projectiles);
          }
        });
  }

  /**
   * Gets all map entities in the world, including {@link Level#borderEntities}.
   *
   * @return an unmodifiable collection of all the mapEntities.
   */
  public Collection<MapEntity> getAllMapEntities() {
    return Collections.unmodifiableCollection(mapEntities);
  }

  /**
   * Removes a given map entity from the map if it exists on the map.
   *
   * @param entity the entity object to remove
   */
  public void removeMapEntity(MapEntity entity) {
    if (mapEntities.contains(entity)) {
      mapEntities.remove(entity);
    }
  }

  public void addProjectile(Projectile projectile) {
    projectiles.add(projectile);
  }

  public void removeProjectile(Projectile projectile) {
    projectiles.remove(projectile);
  }

  public Collection<Projectile> getProjectiles() {
    return Collections.unmodifiableCollection(projectiles);
  }

  /**
   * A getter method which checks if a certain point in the map can be moved into. Returns false for
   * points outside the Map.
   *
   * @param point a point in the map.
   * @return returns whether the point can be moved into.
   */
  public boolean isPassable(MapPoint point) {
    if (!currentLevel().getBounds().contains(point)) {
      return false;
    }
    for (MapEntity mapEntity : mapEntities) {
      if (!mapEntity.isPassable() && mapEntity.contains(point)) {
        return false;
      }
    }
    return true;
  }

  /**
   * A method specific for progression of game. Triggers are specific quests/goals to be achieved
   * for progression.
   */
  private void checkLevelCompletion() {
    if (currentLevel().areGoalsCompleted()) {
      nextLevel();
    }
  }

  /**
   * A method which moves to the next level.
   */
  private void nextLevel() {
    if (!currentLevel().areGoalsCompleted()) {
      throw new IllegalStateException();
    }
    mapEntities.removeAll(currentLevel().getBorderEntities());
    levels.remove(0);

    items.addAll(currentLevel().getItems());
    mapEntities.addAll(currentLevel().getMapEntities());
    mapEntities.addAll(currentLevel().getBorderEntities());
    units.addAll(currentLevel().getUnits());
  }

  /**
   * A method to change all the current positions/animations of all entities in the world.
   */
  public void tick(long timeSinceLastTick) {
    getAllEntities().forEach(e -> e.tick(timeSinceLastTick, this));
    for (Iterator<Unit> iterator = recentlyKilledUnits.iterator(); iterator.hasNext(); ) {
      Unit deadUnit = iterator.next();
      iterator.remove();

      units.remove(deadUnit);
      mapEntities.add(deadUnit.createDeadUnit());
    }

    checkLevelCompletion();
  }

  /**
   * Units should call this when they die.
   */
  public void onEnemyKilled(Unit unit) {
    if (unit.getHealth() != 0) {
      throw new IllegalArgumentException();
    }

    recentlyKilledUnits.add(unit);
  }

  public List<MapPoint> findPath(MapPoint start, MapPoint end) {
    return pathFinder.findPath(this::isPassable, start, end);
  }

  /**
   * Gets the current goal for the current level.
   * @return String description of the current goal
   */
  public String getCurrentGoalDescription() {
    return currentLevel().getGoalDescription();
  }
}
