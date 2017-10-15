package main.common;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import main.common.entity.Entity;
import main.common.entity.MapEntity;
import main.common.entity.Unit;
import main.common.util.MapPoint;
import main.game.model.Level;
import main.game.model.entity.Projectile;

public interface World {

  /**
   * A getter method to get all possible units of type=PLAYER.
   *
   * @return a collection of all possible player units
   */
  Collection<Unit> getAllUnits();

  /**
   * Gets all entities in the world (including map entities, units, projectiles and other entities.
   *
   * @return an unmodifiable collection of all Entities in the world.
   */
  Collection<Entity> getAllEntities();

  /**
   * Gets all map entities in the world, including {@link Level#borderEntities}.
   *
   * @return an unmodifiable collection of all the mapEntities.
   */
  Collection<MapEntity> getAllMapEntities();

  void addProjectile(Projectile projectile);

  void removeProjectile(Projectile projectile);

  Collection<Projectile> getProjectiles();

  /**
   * A getter method which checks if a certain point in the map can be moved into. Returns false for
   * points outside the Map.
   *
   * @param point a point in the map.
   * @return returns whether the point can be moved into.
   */
  boolean isPassable(MapPoint point);

  /**
   * A method to change all the current positions/animations of all entities in the world.
   */
  void tick(long timeSinceLastTick);

  /**
   * Units should call this when they die.
   */
  void onEnemyKilled(Unit unit);

  List<MapPoint> findPath(MapPoint start, MapPoint end);

  /**
   * Gets the current goal for the current level.
   * @return String description of the current goal
   */
  String getCurrentGoalDescription();
}
