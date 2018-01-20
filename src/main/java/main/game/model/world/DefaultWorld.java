package main.game.model.world;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import main.game.model.Level;
import main.game.model.entity.Entity;
import main.game.model.entity.HeroUnit;
import main.game.model.entity.MapEntity;
import main.game.model.entity.Projectile;
import main.game.model.entity.StaticEntity;
import main.game.model.entity.Unit;
import main.game.model.entity.usable.Item;
import main.game.model.world.pathfinder.PathFinder;
import main.util.MapPoint;
import main.util.MapRect;
import main.util.MapSize;

/**
 * Implementation of the World API.
 *
 * @author Eric Diputado
 * @author Hrshikesh Arora (Secondary author)
 */
public class DefaultWorld implements Serializable, World {

  private static final long serialVersionUID = 1L;
  private static final double UNIT_REPEL_MULTIPLIER = 25; // bigger is smaller repel

  private final List<Level> levels;
  private final HeroUnit heroUnit;
  private final Set<Unit> units;
  private final Set<Unit> recentlyKilledUnits;
  private final Set<Item> items;
  private final Set<MapEntity> mapEntities;
  private final Set<Projectile> projectiles;
  private final Set<StaticEntity> staticEntities;
  private final PathFinder pathFinder;

  /** The entities that were recently added to the world. */
  private Set<Entity> recentlyAddedEntities = new HashSet<>();

  /**
   * Creates the world.
   *
   * @param levels The levels sorted from start to finish. The first level is the initial level.
   * @param heroUnit The hero unit used throughout the whole game.
   */
  public DefaultWorld(List<Level> levels, HeroUnit heroUnit, PathFinder pathfinder) {
    Objects.requireNonNull(levels);
    Objects.requireNonNull(heroUnit);
    if (levels.isEmpty()) {
      throw new IllegalArgumentException("Cannot have empty levels");
    }
    this.heroUnit = heroUnit;
    this.recentlyAddedEntities.add(heroUnit);
    this.levels = new ArrayList<>(levels);
    this.units = newConcurrentSet();
    this.recentlyKilledUnits = newConcurrentSet();
    this.items = newConcurrentSet();
    this.mapEntities = newConcurrentSet();
    this.mapEntities.addAll(currentLevel().getBorderEntities());
    this.projectiles = newConcurrentSet();
    this.pathFinder = pathfinder;
    staticEntities = newConcurrentSet();

    this.addAllEntity(currentLevel().getUnits(), this.units);
    this.addAllEntity(currentLevel().getItems(), this.items);
    this.addAllEntity(currentLevel().getMapEntities(), this.mapEntities);
  }

  /**
   * Gets the entities that were recently added to the world.
   * Then resets the recently added collection.
   */
  public Collection<Entity> recieveRecentlyAddedEntities() {
    if (this.recentlyAddedEntities.size() == 0) {
      return this.recentlyAddedEntities;
    }
    Set<Entity> recentlyAdded = this.recentlyAddedEntities;
    this.recentlyAddedEntities = new HashSet<>();
    return recentlyAdded;
  }

  private <T extends Entity> void addAllEntity(Collection<T> entitiesToAdd, Collection<T> to) {
    this.recentlyAddedEntities.addAll(entitiesToAdd);
    to.addAll(entitiesToAdd);
  }

  private <T extends Entity> void addEntity(T entity, Collection<T> to) {
    this.recentlyAddedEntities.add(entity);
    to.add(entity);
  }

  private <T extends Entity> void removeEntity(T entity, Collection<T> from) {
    boolean wasRemoved = from.remove(entity);
    if (wasRemoved) {
      entity.getRemovedEvent().broadcast(null);
    }
  }

  /**
   * This will cause all the views for the entities to be removed whether they are removed or not
   * so use with caution. If in dout use a loop round the {@link this.removeEntity()} method
   */
  private <T extends Entity> void removeAllEntity(
      Collection<T> entitiesToRemove, Collection<T> from
  ) {
    boolean result = from.removeAll(entitiesToRemove);
    if (result) {
      entitiesToRemove.forEach(e -> e.getRemovedEvent().broadcast(null));
    }
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

  @Override
  public Collection<Unit> getAllUnits() {
    ArrayList<Unit> allUnits = new ArrayList<>(units);
    allUnits.add(heroUnit);
    return Collections.unmodifiableList(allUnits);
  }

  @Override
  public HeroUnit getHeroUnit() {
    return this.heroUnit;
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
            addAll(staticEntities);
          }
        });
  }

  @Override
  public Collection<MapEntity> getAllMapEntities() {
    return Collections.unmodifiableCollection(mapEntities);
  }

  @Override
  public void removeItem(Item item) {
    this.removeEntity(item, this.items);
  }

  public void addProjectile(Projectile projectile) {
    this.addEntity(projectile, projectiles);
  }

  @Override
  public void removeProjectile(Projectile projectile) {
    this.removeEntity(projectile, this.projectiles);
  }

  @Override
  public Collection<Projectile> getProjectiles() {
    return Collections.unmodifiableCollection(projectiles);
  }

  public void addStaticEntity(StaticEntity staticEntity) {
    this.addEntity(staticEntity, this.staticEntities);
  }

  @Override
  public void removeStaticEntity(StaticEntity staticEntity) {
    this.removeEntity(staticEntity, this.staticEntities);
  }

  @Override
  public Collection<StaticEntity> getStaticEntities() {
    return Collections.unmodifiableCollection(staticEntities);
  }

  @Override
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
    if (currentLevel().areGoalsCompleted(this)) {
      nextLevel();
    }
  }

  /**
   * A method which moves to the next level.
   */
  private void nextLevel() {
    if (!currentLevel().areGoalsCompleted(this)) {
      throw new IllegalStateException();
    }
    if (levels.size() == 1) {
      return;
    }
    this.removeAllEntity(currentLevel().getBorderEntities(), this.mapEntities);
    levels.remove(0);

    this.addAllEntity(currentLevel().getItems(), this.items);
    this.addAllEntity(currentLevel().getMapEntities(), this.mapEntities);
    this.addAllEntity(currentLevel().getBorderEntities(), this.mapEntities);
    this.addAllEntity(currentLevel().getUnits(), this.units);
  }

  @Override
  public MapRect getCurrentLevelBounds() {
    return currentLevel().getBounds();
  }

  @Override
  public void tick(long timeSinceLastTick) {
    getAllEntities().forEach(e -> e.tick(timeSinceLastTick, this));
    for (Iterator<Unit> iterator = recentlyKilledUnits.iterator(); iterator.hasNext(); ) {
      Unit deadUnit = iterator.next();
      iterator.remove();

      this.removeEntity(deadUnit, units);
      this.addEntity(deadUnit.createDeadUnit(), this.mapEntities);
    }

    this.repelUnits();
    checkLevelCompletion();
  }

  private void repelUnits() {
    HashMap<Unit, MapSize> unitMovements = new HashMap<>();

    getAllUnits().forEach(baseUnit -> getAllUnits()
        .stream()
        .filter(otherUnit -> otherUnit != baseUnit)
        .filter(otherUnit -> otherUnit.getTeam() == baseUnit.getTeam())
        .forEach(otherUnit -> {
          if (baseUnit == otherUnit) {
            return;
          }
          double distance = baseUnit.getCentre().distanceTo(otherUnit.getCentre());
          double minDistance = baseUnit.getSize().width / 2 + otherUnit.getSize().width / 2;
          if (distance < minDistance) {
            double angle = baseUnit.getCentre().angleTo(otherUnit.getCentre()) + (Math.PI) / 2;
            double dx = (1 / (UNIT_REPEL_MULTIPLIER * distance)) * Math.sin(angle);
            double dy = (1 / (UNIT_REPEL_MULTIPLIER * distance)) * Math.cos(angle);
            unitMovements.computeIfAbsent(baseUnit,
                unit -> unitMovements.put(unit, new MapSize(0, 0)));
            MapSize currentMovement = unitMovements.get(baseUnit);
            MapSize newMovement = new MapSize(currentMovement.width - dx,
                currentMovement.height - dy);
            unitMovements.put(baseUnit, newMovement);
          }
        }));

    unitMovements.keySet().forEach(unit -> {
      MapSize movement = unitMovements.get(unit);
      unit.slidePosition(movement.width, movement.height);
    });
  }

  @Override
  public void onEnemyKilled(Unit unit) {
    if (unit.getHealth() != 0) {
      throw new IllegalArgumentException();
    }
    recentlyKilledUnits.add(unit);
  }

  @Override
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

  @Override
  public boolean isWon() {
    return levels.size() == 1 && currentLevel().areGoalsCompleted(this);
  }

  @Override
  public boolean isLost() {
    return heroUnit.getHealth() <= 0;
  }
}
