package main.game.model;

import java.util.Collection;
import main.game.model.entity.Entity;
import main.game.model.entity.Item;
import main.game.model.entity.Unit;
import main.game.model.world.World;

/**
 * A container class for all model components (including units, items, projectiles, etc). This also
 * handles the progression of the story/game
 */
public class GameModel {

  public GameModel(
      World world,
      Collection<Unit> units,
      Collection<Item> items
  ) {
  }

  /**
   * A getter method to get all possible entities.
   *
   * @return a collection of all possible entities
   */
  public Collection<Entity> getAllEntities() {
    throw new Error("NYI");
  }

  /**
   * Starts the main game loop of this app.
   */
  public void startGame() {
    throw new Error("NYI");
  }

  /**
   * A setter method to select a collection.
   *
   * @param entitySelection points on the world that may contain entities
   */
  public void setEntitySelection(Collection<Entity> entitySelection) {
    throw new Error("NYI");
  }

  /**
   * A getter method get a previously selected collection.
   *
   * @return a collection of selected entities
   */
  public Collection<Entity> getEntitySelection() {
    throw new Error("NYI");
  }
}
