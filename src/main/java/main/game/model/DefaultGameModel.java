package main.game.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import main.game.model.entity.Entity;
import main.game.model.entity.HeroUnit;
import main.game.model.entity.Unit;
import main.game.model.world.World;
import main.util.Events;
import main.util.Events.GameLost;
import main.util.Events.GameWon;
import main.util.Events.MainGameTick;
import main.util.Looper;

/**
 * Implementation of GameModel.
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
    selectedUnits = Collections.unmodifiableSet(new HashSet<>(unitSelection));
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
    Set<Unit> units = new HashSet<>(this.selectedUnits);
    units.add(unit);
    this.selectedUnits = Collections.unmodifiableSet(units);
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
