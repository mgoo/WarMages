package main.game.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;
import main.game.model.entity.Entity;
import main.game.model.entity.Unit;
import main.game.model.world.World;
import main.util.Events;
import main.util.Events.MainGameTick;
import main.util.MapPoint;
import main.util.MapRect;

/**
 * Contains the main game loop, and controls the the progression of the story/game through the use
 * of {@link Level}s.
 */
public class GameModel {

  public static final long DELAY = 50;

  private final World world;
  private final MainGameTick mainGameTick;

  private Collection<Entity> selectedUnits;

  /**
   * Creates a game model.
   *
   * @param world The world to use for the whole game.
   */
  public GameModel(World world, Events.MainGameTick mainGameTick) {
    this.world = world;
    this.mainGameTick = mainGameTick;
    selectedUnits = new HashSet<>();
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
   * A getter method to get all possible units.
   *
   * @return a collection of all possible units
   */
  public Collection<Unit> getAllUnits() {
    return world.getAllUnits();
  }

  /**
   * Starts the main game loop of this app.
   */
  public void startGame() {
    Timer t = new Timer();
    t.schedule(new TimerTask() {
      @Override
      public void run() {
        mainGameTick.broadcast(DELAY);
      }
    }, DELAY, DELAY);
  }

  /**
   * A setter method to select a collection.
   *
   * @param entitySelection points on the world that may contain entities
   */
  public void setEntitySelection(Collection<Entity> entitySelection) {
    selectedUnits = entitySelection;
  }

  /**
   * A getter method get a previously selected collection.
   *
   * @return a collection of selected entities
   */
  public Collection<Entity> getEntitySelection() {
    return Collections.unmodifiableCollection(selectedUnits);
  }
}
