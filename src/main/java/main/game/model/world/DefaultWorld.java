package main.game.model.world;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import main.common.PathFinder;
import main.common.World;
import main.common.entity.Entity;
import main.common.entity.HeroUnit;
import main.common.entity.MapEntity;
import main.common.entity.Unit;
import main.common.entity.usable.Item;
import main.common.util.MapPoint;
import main.common.util.MapRect;
import main.common.util.MapSize;
import main.game.model.Level;
import main.common.entity.Projectile;

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
  private final PathFinder pathFinder;

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
          }
        });
  }

  @Override
  public Collection<MapEntity> getAllMapEntities() {
    return Collections.unmodifiableCollection(mapEntities);
  }

  @Override
  public void removeItem(Item item) {
    if (items.contains(item)) {
      items.remove(item);
    }
  }

  public void addProjectile(Projectile projectile) {
    projectiles.add(projectile);
  }

  @Override
  public void removeProjectile(Projectile projectile) {
    projectiles.remove(projectile);
  }

  @Override
  public Collection<Projectile> getProjectiles() {
    return Collections.unmodifiableCollection(projectiles);
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
    mapEntities.removeAll(currentLevel().getBorderEntities());
    levels.remove(0);

    items.addAll(currentLevel().getItems());
    mapEntities.addAll(currentLevel().getMapEntities());
    mapEntities.addAll(currentLevel().getBorderEntities());
    units.addAll(currentLevel().getUnits());
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

      units.remove(deadUnit);
      mapEntities.add(deadUnit.createDeadUnit());
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
