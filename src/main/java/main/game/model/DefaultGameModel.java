package main.game.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import main.common.GameModel;
import main.common.entity.HeroUnit;
import main.common.util.Events.GameLost;
import main.common.util.Events.GameWon;
import main.common.entity.Entity;
import main.common.entity.Unit;
import main.common.util.Looper;
import main.common.World;
import main.common.util.Events;
import main.common.util.Events.MainGameTick;

/**
 * Contains the main game loop, and controls the the progression of the story/game through the use
 * of {@link Level}s.
 *
 * @author Eric Diputado
 */
public class DefaultGameModel implements GameModel {

  private final World world;
  private final MainGameTick mainGameTick;
  private final Looper looper;
  private final GameWon gameWon;
  private final GameLost gameLost;

  private Collection<Unit> selectedUnits;

  /**
   * Creates a game model.
   *
   * @param world The world to use for the whole game.
   */
  public DefaultGameModel(
      World world, Events.MainGameTick mainGameTick, GameWon gameWon, GameLost gameLost
  ) {
    this.world = world;
    this.mainGameTick = mainGameTick;
    this.selectedUnits = Collections.emptySet();
    this.looper = new Looper();
    this.gameWon = gameWon;
    this.gameLost = gameLost;
  }

  @Override
  public Collection<Entity> getAllEntities() {
    return world.getAllEntities();
  }

  @Override
  public void startGame() {
    looper.startWithSchedule(() -> {
      mainGameTick.broadcast(System.currentTimeMillis());
      if (world.isWon()) {
        gameWon.broadcast(null);
      } else if (world.isLost()) {
        gameLost.broadcast(null);
      }
    }, DELAY);
  }

  @Override
  public void setUnitSelection(Collection<Unit> unitSelection) {
    selectedUnits = new HashSet<>(unitSelection);
    if (selectedUnits.contains(null)) {
      throw new NullPointerException();
    }
  }

  @Override
  public Collection<Unit> getUnitSelection() {
    return Collections.unmodifiableCollection(selectedUnits);
  }

  @Override
  public void addToUnitSelection(Unit unit) {
    this.selectedUnits.add(unit);
  }

  @Override
  public Collection<Unit> getAllUnits() {
    return world.getAllUnits();
  }

  @Override
  public World getWorld() {
    return world;
  }

  @Override
  public HeroUnit getHeroUnit() {
    return world.getHeroUnit();
  }

  @Override
  public void pauseGame() {
    looper.setPaused(true);
  }

  @Override
  public void resumeGame() {
    looper.setPaused(false);
  }

  @Override
  public void stopGame() {
    looper.stop();
  }

}
