package test.game.model;

import static main.images.GameImageResource.ARCHER_SPRITE_SHEET;
import static main.images.GameImageResource.FOOT_KNIGHT_SPRITE_SHEET;
import static main.images.GameImageResource.GOLDEN_HERO_SPRITE_SHEET;
import static main.images.GameImageResource.ORC_SPEARMAN_SPRITE_SHEET;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import main.game.model.GameModel;
import main.game.model.Level;
import main.game.model.entity.HeroUnit;
import main.game.model.entity.Team;
import main.game.model.entity.Unit;
import main.game.model.entity.UnitType;
import main.game.model.world.World;
import main.images.UnitSpriteSheet;
import main.util.Events.MainGameTick;
import main.util.MapPoint;
import main.util.MapRect;
import main.util.MapSize;
import org.junit.Test;

public class GameModelTest {

  private GameModel model = null;
  private HeroUnit heroUnit = new HeroUnit(
      new MapPoint(1, 1),
      new MapSize(1, 1),
      new UnitSpriteSheet(GOLDEN_HERO_SPRITE_SHEET),
      UnitType.SWORDSMAN
  );

  @Test
  public void testSetSelectionAndGetSelection() {
    Unit unit = createDefaultEnemyOrc();
    Unit unit2 = createDefaultPlayerKnight();
    Unit unit3 = createDefaultPlayerArcher();
    model = new GameModel(
        createWorld(createLevels(createLevelWith(unit, unit2, unit3)), heroUnit),
        new MainGameTick()
    );
    model.setUnitSelection(Arrays.asList(unit, unit2, unit3));
    Collection<Unit> selection = model.getUnitSelection();
    assertEquals(3, selection.size());
    assertTrue(selection.contains(unit));
    assertTrue(selection.contains(unit2));
    assertTrue(selection.contains(unit3));
  }

  @Test
  public void testSelectionIsCloned() {
    Unit unit = createDefaultEnemyOrc();
    Unit unit2 = createDefaultPlayerKnight();
    Unit unit3 = createDefaultPlayerArcher();
    List<Unit> units = new ArrayList<>(Arrays.asList(unit, unit2));
    model = new GameModel(
        createWorld(createLevels(createLevelWith(unit, unit2, unit3)), heroUnit),
        new MainGameTick()
    );
    model.setUnitSelection(units);
    units.add(unit3);
    Collection<Unit> selection = model.getUnitSelection();
    assertEquals(2, selection.size());
    assertFalse(selection.contains(unit3));
  }

  //PRIVATE METHODS.

  /**
   * Creates a level with unit args.
   *
   * @param units units in level
   * @return a new level
   */
  private Level createLevelWith(Unit... units) {
    return new Level(
        new MapRect(new MapPoint(-100, -100), new MapPoint(100, 100)),
        Arrays.asList(units),
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList(),
        e -> false,
        ""
    );
  }

  /**
   * Creates a list of levels based on args.
   *
   * @param levels levels
   * @return a list of levels
   */
  private List<Level> createLevels(Level... levels) {
    return Arrays.asList(levels);
  }

  /**
   * Creates a world based on parameters.
   *
   * @param levels levels in a world
   * @param heroUnit herounit in a world
   * @return a new world
   */
  private World createWorld(List<Level> levels, HeroUnit heroUnit) {
    return new World(levels, heroUnit);
  }


  /**
   * Creates an enemy orc.
   *
   * @return an enemy orc unit
   */
  private Unit createDefaultEnemyOrc() {
    return new Unit(
        new MapPoint(20, 20),
        new MapSize(30, 30),
        Team.ENEMY,
        new UnitSpriteSheet(ORC_SPEARMAN_SPRITE_SHEET),
        UnitType.SPEARMAN
    );
  }

  /**
   * Creates a player knight.
   *
   * @return a player knight unit
   */
  private Unit createDefaultPlayerKnight() {
    return new Unit(
        new MapPoint(20, 20),
        new MapSize(30, 30),
        Team.PLAYER,
        new UnitSpriteSheet(FOOT_KNIGHT_SPRITE_SHEET),
        UnitType.SWORDSMAN
    );
  }

  /**
   * Creates a player knight.
   *
   * @return a player knight unit.
   */
  private Unit createDefaultPlayerArcher() {
    return new Unit(
        new MapPoint(20, 20),
        new MapSize(30, 30),
        Team.PLAYER,
        new UnitSpriteSheet(ARCHER_SPRITE_SHEET),
        UnitType.ARCHER
    );
  }
}
