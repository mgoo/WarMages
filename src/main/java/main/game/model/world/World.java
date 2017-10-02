package main.game.model.world;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import main.game.model.Level;
import main.game.model.entity.Entity;
import main.game.model.entity.HeroUnit;
import main.game.model.entity.Item;
import main.game.model.entity.MapEntity;
import main.game.model.entity.Team;
import main.game.model.entity.Unit;
import main.util.MapPoint;
import main.util.MapRect;

/**
 * World class is a representation of all the in-play entities and in-play entities: all entity
 * objects that have been instantiated.
 */
public class World implements Serializable {

  private static final long serialVersionUID = 1L;

  private final List<Level> levels;

  private int levelIndex = 0; // TODO ERIC unused

  private final HeroUnit heroUnit;
  private final Collection<Unit> units;
  private final Collection<Item> items;
  private final Collection<MapEntity> mapEntities;

  private Collection<Entity> selectedEntities;

  /**
   * Creates the world.
   *
   * @param levels The levels sorted from start to finish. The first level in this list is the
   *     initial level.
   * @param heroUnit The hero unit used throughout the whole game.
   */
  public World(List<Level> levels, HeroUnit heroUnit) {
    Objects.requireNonNull(levels);
    Objects.requireNonNull(heroUnit);
    this.heroUnit = heroUnit;
    this.levels = new ArrayList<>(levels);
    this.units = new ArrayList<>(levels.get(0).getUnits()); // TODO eric tidy get(0)
    this.items = new ArrayList<>(levels.get(0).getItems());
    this.mapEntities = new ArrayList<>(levels.get(0).getMapEntities());
    this.mapEntities.addAll(levels.get(0).getBorderEntities());
  }

  /**
   * A getter method which returns all the entities in the world thats within the selection. The
   * collection to be return must be ordered.
   *
   * @param rect a selection box.
   * @return A collection of Entities within the given selection rect.
   */
  public Collection<Unit> getUnits(MapRect rect) {
    return Collections.unmodifiableCollection(
        new ArrayList<Unit>(units) {
          {
            add(heroUnit);
          }
        }.stream()
            .filter(e -> rect.contains(e.getCentre()))
            .collect(Collectors.toList()));
  }

  /**
   * A getter method to get all possible units of type=PLAYER.
   * TODO ERIC this is method name is really misleading!!! Also it doesnt include the heroUnit
   *
   * @return a collection of all possible player units
   */
  public Collection<Unit> getAllUnits() {
    return Collections.unmodifiableCollection(
        units.stream().filter(u -> u.getTeam().equals(Team.PLAYER)).collect(Collectors.toList())
    );
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
   * A getter method which checks if a certain point in the map can be moved into. TODO - make sure
   * that the method returns false for points outside the Map
   *
   * @param point a point in the map.
   * @return returns whether the point can be moved into.
   */
  public boolean isPassable(MapPoint point) {
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
   * TODO ERIC update method name and doc
   */
  private void easeTrigger() {
    if (levels.get(0).areGoalsCompleted()) {
      nextLevel();
    }
  }

  /**
   * A method which moves to the next level.
   */
  private void nextLevel() {
    // TODO eric add precondition
    mapEntities.removeAll(levels.get(0).getBorderEntities());
    levels.remove(0);

    items.addAll(levels.get(0).getItems());
    mapEntities.addAll(levels.get(0).getMapEntities());
    mapEntities.addAll(levels.get(0).getBorderEntities());
    units.addAll(levels.get(0).getUnits());
  }

  /**
   * A method to change all the current positions/animations of all entities in the world.
   */
  public void tick(long timeSinceLastTick) {
    getAllEntities().stream().forEach(e -> e.tick(timeSinceLastTick));
    easeTrigger();
  }

  public void setEntitySelection(Collection<Entity> selection) {
    selectedEntities = selection;
  }

  public Collection<Entity> getSelectedEntity() {
    return selectedEntities;
  }
}
