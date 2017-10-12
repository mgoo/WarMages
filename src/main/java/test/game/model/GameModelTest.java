package test.game.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static test.game.model.world.WorldTestUtils.createDefaultEnemyOrc;
import static test.game.model.world.WorldTestUtils.createDefaultPlayerArcher;
import static test.game.model.world.WorldTestUtils.createDefaultPlayerKnight;
import static test.game.model.world.WorldTestUtils.createHeroUnit;
import static test.game.model.world.WorldTestUtils.createLevelWith;
import static test.game.model.world.WorldTestUtils.createLevels;
import static test.game.model.world.WorldTestUtils.createStubItem;
import static test.game.model.world.WorldTestUtils.createWorld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import main.game.model.GameModel;
import main.game.model.entity.HeroUnit;
import main.game.model.entity.Unit;
import main.common.util.Events.MainGameTick;
import main.common.util.MapPoint;
import org.junit.Test;

/**
 * Tests for the GameModel
 *
 * @author Eric Diptuado
 */
public class GameModelTest {

  private GameModel model = null;
  private HeroUnit heroUnit = createHeroUnit();

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

  @Test
  public void testNumberOfEntities_1() {
    model = new GameModel(
        createWorld(
            createLevels(createLevelWith(createDefaultPlayerArcher(), createDefaultPlayerKnight())),
            heroUnit
        ),
        new MainGameTick()
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
        new MainGameTick()
    );
    assertEquals(3, model.getAllEntities().size());
    assertEquals(1, model.getAllUnits().size());
  }
}

