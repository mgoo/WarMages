package test.game.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static test.game.model.world.WorldTestUtils.createDefaultEnemyOrc;
import static test.game.model.world.WorldTestUtils.createDefaultHeroUnit;
import static test.game.model.world.WorldTestUtils.createDefaultPlayerArcher;
import static test.game.model.world.WorldTestUtils.createDefaultPlayerKnight;
import static test.game.model.world.WorldTestUtils.createLevelWith;
import static test.game.model.world.WorldTestUtils.createLevels;
import static test.game.model.world.WorldTestUtils.createStubItem;
import static test.game.model.world.WorldTestUtils.createWorld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import main.common.util.Events.GameLost;
import main.common.util.Events.GameWon;
import main.game.model.GameModel;
import main.common.entity.HeroUnit;
import main.common.entity.Unit;
import main.common.util.Events.MainGameTick;
import main.common.util.MapPoint;
import org.junit.Before;
import org.junit.Test;

public class GameModelTest {
  private HeroUnit heroUnit = createDefaultHeroUnit();
  private GameModel model = null;
  private GameWon gc = new GameWon();
  GameLost gl = new GameLost();

  @Before
  public void setup() {
    gc.registerListener(parameter -> {});
    gl.registerListener(parameter -> {});
  }

  @Test
  public void testSetSelectionAndGetSelection() {
    Unit unit = createDefaultEnemyOrc();
    Unit unit2 = createDefaultPlayerKnight();
    Unit unit3 = createDefaultPlayerArcher();
    model = new GameModel(
        createWorld(createLevels(createLevelWith(unit, unit2, unit3)), heroUnit),
        new MainGameTick(),
        gc, gl
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
        new MainGameTick(),
        gc, gl
    );
    model.setUnitSelection(units);
    units.add(unit3);
    Collection<Unit> selection = model.getUnitSelection();
    assertEquals(2, selection.size());
    assertFalse(selection.contains(unit3));
  }

  @Test
  public void testNumberOfEntities_1() {
    model = new GameModel(
        createWorld(
            createLevels(createLevelWith(createDefaultPlayerArcher(), createDefaultPlayerKnight())),
            heroUnit
        ),
        new MainGameTick(),
        gc, gl
    );
    assertEquals(3, model.getAllEntities().size());
    assertEquals(3, model.getAllUnits().size());
  }

  @Test
  public void testNumberOfEntities_2() {
    model = new GameModel(
        createWorld(
            createLevels(createLevelWith(
                createStubItem(new MapPoint(2, 2)),
                createStubItem(new MapPoint(6, 6))
            )),
            heroUnit
        ),
        new MainGameTick(),
        gc, gl
    );
    assertEquals(3, model.getAllEntities().size());
    assertEquals(1, model.getAllUnits().size());
  }
}

