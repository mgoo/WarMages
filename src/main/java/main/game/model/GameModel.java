package main.game.model;

import java.util.Collection;
import java.util.List;
import main.game.model.entity.Entity;
import main.game.model.entity.HeroUnit;
import main.game.model.world.World;
import main.util.MapPoint;

/**
 * Contains the main game loop, and controls the the progression of the story/game through the use
 * of {@link Level}s.
 */
public class GameModel {

  public static long delay = 20;

  /**
   * Creates a game model.
   *
   * @param world The world to use for the whole game.
   */
  public GameModel(World world) {

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
