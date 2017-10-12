package main.game.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;
import main.common.util.Looper;
import main.game.model.entity.Entity;
import main.game.model.entity.Unit;
import main.game.model.world.World;
import main.common.util.Events;
import main.common.util.Events.MainGameTick;

/**
 * Contains the main game loop, and controls the the progression of the story/game through the use
 * of {@link Level}s.
 */
public class GameModel {

  /**
   * Milliseconds between ticks.
   */
  public static final long DELAY = 50;

  private final World world;
  private final MainGameTick mainGameTick;
  private final Looper looper;

  private Collection<Unit> selectedUnits;

  /**
   * Creates a game model.
   *
   * @param world The world to use for the whole game.
   */
  public GameModel(World world, Events.MainGameTick mainGameTick) {
    this.world = world;
    this.mainGameTick = mainGameTick;
    this.selectedUnits = Collections.emptySet();
    this.looper = new Looper();
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
    looper.startWithSchedule(() -> mainGameTick.broadcast(System.currentTimeMillis()), DELAY);
  }

  /**
   * A setter method to select a collection.
   *
   * @param unitSelection Selection points on the world that may contain units
   */
  public void setUnitSelection(Collection<Unit> unitSelection) {
    selectedUnits = new HashSet<>(unitSelection);
    if (selectedUnits.contains(null)) {
      throw new NullPointerException();
    }
  }

  /**
   * A getter method get a previously selected collection.
   *
   * @return a collection of selected entities
   */
  public Collection<Unit> getUnitSelection() {
    return Collections.unmodifiableCollection(selectedUnits);
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
   * Getter for the world.
   *
   * @return world
   */
  public World getWorld() {
    return world;
  }

  /**
   * Pauses the main game loop.
   */
  public void pauseGame() {
    looper.setPaused(true);
  }

  /**
   * Resumes the main game loop.
   */
  public void resumeGame() {
    looper.setPaused(false);
  }

  /**
   * Stops the main game loop.
   */
  public void stopGame() {
    looper.stop();
  }
}
