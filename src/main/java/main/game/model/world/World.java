package main.game.model.world;

import java.util.Collection;
import java.util.List;
import main.game.model.entity.Entity;
import main.game.model.entity.HeroUnit;
import main.game.model.entity.MapEntity;
import main.game.model.entity.Projectile;
import main.game.model.entity.StaticEntity;
import main.game.model.entity.Unit;
import main.game.model.entity.usable.Item;
import main.util.MapPoint;
import main.util.MapRect;

/**
 * World class is a representation of all the in-play entities and in-play entities: all entity
 * objects that have been instantiated.
 *
 * @author Eric Diputado
 */
public interface World {

  /**
   * A getter method to get all possible units of type=PLAYER.
   *
   * @return a collection of all possible player units
   */
  Collection<Unit> getAllUnits();

  void addUnitEntity(Unit unit);

  void removeUnitEntity(Unit unit);

  /**
   * Gets the hero unit in the world.
   */
  HeroUnit getHeroUnit();

  /**
   * Gets all entities in the world (including map entities, units, projectiles and other entities.
   *
   * @return an unmodifiable collection of all Entities in the world.
   */
  Collection<Entity> getAllEntities();

  /**
   * Gets all map entities in the world, including borderEntities from Levels.
   *
   * @return an unmodifiable collection of all the mapEntities.
   */
  Collection<MapEntity> getAllMapEntities();

  /**
   * Removes a given item from the map if it exists on the map.
   *
   * @param item -- the item to remove
   */
  void removeItem(Item item);

  void addProjectile(Projectile projectile);

  void removeProjectile(Projectile projectile);

  Collection<Projectile> getProjectiles();

  void addStaticEntity(StaticEntity staticEntity);

  void removeStaticEntity(StaticEntity staticEntity);

  Collection<StaticEntity> getStaticEntities();

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

  /**
   * Checks if game is won.
   * @return whether game is won
   */
  boolean isWon();

  /**
   * Checks if game is lost.
   * @return whether game is lost
   */
  boolean isLost();

  /**
   * Gets the current active level.
   */
  MapRect getCurrentLevelBounds();

  Collection<Entity> recieveRecentlyAddedEntities();
}
