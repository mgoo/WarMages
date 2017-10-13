package test.game.model.entity;

import static org.junit.Assert.assertTrue;
import static test.game.model.world.WorldTestUtils.createDefaultEnemyOrc;
import static test.game.model.world.WorldTestUtils.createDefaultPlayerKnight;

import main.common.entity.HeroUnit;
import main.common.entity.Unit;
import main.game.model.GameModel;
import main.game.model.world.World;
import org.junit.Test;
import test.game.model.world.WorldTestUtils;

/**
 * Tests for Entities.
 *
 * @author paladogabr
 */
public class EntityTest {

  private World getWorld() {
    return WorldTestUtils
        .createWorld(WorldTestUtils.createLevels(WorldTestUtils.createEmptyLevel()), getHeroUnit());
  }

  private HeroUnit getHeroUnit() {
    return WorldTestUtils.createDefaultHeroUnit();
  }

  private Unit getPlayer() {
    return createDefaultPlayerKnight();
  }

  private Unit getEnemy() {
    return createDefaultEnemyOrc();
  }

  @Test
  public void testDamageEnemy() {
    Unit unit1 = getPlayer();
    Unit unit2 = getEnemy();
    World world = getWorld();
    unit1.setTargetUnit(unit2, world);
    int prevHealth = unit2.getHealth();
    for (int i = 0; i < 900; i++) {
      unit1.tick(GameModel.DELAY, world);
    }
    assertTrue(prevHealth > unit2.getHealth());
  }
}
