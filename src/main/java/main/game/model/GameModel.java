package main.game.model;

import java.util.Collection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import main.game.model.entity.Entity;
import main.game.model.entity.HeroUnit;
import main.game.model.world.World;
import main.util.MapPoint;

/**
 * Contains the main game loop, and controls the the progression of the story/game through the use
 * of {@link Level}s.
 */
public class GameModel {
  public final long delay = 50;

  private final World world;

  /**
   * Creates a game model.
   *
   * @param world The world to use for the whole game.
   */
  public GameModel(World world) {
    this.world = world;
  }


  /**
   * A getter method to get all possible entities.
   *
   * @return a collection of all possible entities
   */
  public Collection<Entity> getAllEntities() {
    return world.getAllEntities();
  }

  /**
   * Starts the main game loop of this app.
   */
  public void startGame() {
    Timer t = new Timer();
    t.schedule(new TimerTask() {
      @Override
      public void run() {
        //TODO @DYLAN thank you.
      }
    }, delay, delay);
    throw new Error("NYI");
  }

  /**
   * A setter method to select a collection.
   *
   * @param entitySelection points on the world that may contain entities
   */
  public void setEntitySelection(Collection<Entity> entitySelection) {
    world.setEntitySelection(entitySelection);
  }

  /**
   * A getter method get a previously selected collection.
   *
   * @return a collection of selected entities
   */
  public Collection<Entity> getEntitySelection() {
    return world.getSelectedEntity();
  }
}
