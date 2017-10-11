package test.game.model.entity;

import static org.junit.Assert.assertEquals;
import static test.game.model.world.WorldTestUtils.createDefaultEnemyOrc;
import static test.game.model.world.WorldTestUtils.createDefaultPlayerKnight;

import main.common.HeroUnit;
import main.common.Unit;
import main.game.model.GameModel;
import main.game.model.world.World;
import org.junit.Test;
import test.game.model.world.WorldTestUtils;

public class EntityTest {

  private final int damageAmt = 10;

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
    unit1.setTarget(unit2, world);
    int prevHealth = unit2.getHealth();
    for (int i = 0; i < 820; i++) {
      unit1.tick(GameModel.DELAY, world);
    }
    assertEquals(prevHealth - damageAmt, unit2.getHealth());
  }
}
