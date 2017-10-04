package main.game.model.world;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import main.game.model.Level;
import main.game.model.entity.Entity;
import main.game.model.entity.HeroUnit;
import main.game.model.entity.Item;
import main.game.model.entity.MapEntity;
import main.game.model.entity.Projectile;
import main.game.model.entity.Unit;
import main.util.MapPoint;

/**
 * World class is a representation of all the in-play entities and in-play entities: all entity
 * objects that have been instantiated.
 */
public class World implements Serializable {

  private static final long serialVersionUID = 1L;

  private final List<Level> levels;

  private final HeroUnit heroUnit;
  private final Collection<Unit> units;
  private final Collection<Item> items;
  private final Collection<MapEntity> mapEntities;
  private final Collection<Projectile> projectiles;

  /**
   * Creates the world.
   *
   * @param levels The levels sorted from start to finish. The first level is the initial level.
   * @param heroUnit The hero unit used throughout the whole game.
   */
  public World(List<Level> levels, HeroUnit heroUnit) {
    Objects.requireNonNull(levels);
    Objects.requireNonNull(heroUnit);
    if (levels.isEmpty()) {
      throw new IllegalArgumentException("Cannot have empty levels");
    }
    this.heroUnit = heroUnit;
    this.levels = new ArrayList<>(levels);
    this.units = new ArrayList<>(currentLevel().getUnits());
    this.items = new ArrayList<>(currentLevel().getItems());
    this.mapEntities = new ArrayList<>(currentLevel().getMapEntities());
    this.mapEntities.addAll(currentLevel().getBorderEntities());
    this.projectiles = new ArrayList<>();
  }

  private Level currentLevel() {
    Objects.requireNonNull(levels);
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

  public void addProjectile(Projectile projectile) {
    projectiles.add(projectile);
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
      if (mapEntity.contains(point)) {
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
    checkLevelCompletion();
  }
}
