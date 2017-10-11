package test.game.model.entity;

import static org.junit.Assert.assertEquals;
import static test.game.model.world.WorldTestUtils.createDefaultEnemyOrc;
import static test.game.model.world.WorldTestUtils.createDefaultPlayerKnight;

import main.game.model.GameModel;
import main.game.model.entity.DefaultHeroUnit;
import main.game.model.entity.DefaultUnit;
import main.game.model.world.World;
import org.junit.Test;
import test.game.model.world.WorldTestUtils;

public class EntityTest {

  private final int damageAmt = 10;

  private World getWorld() {
    return WorldTestUtils
        .createWorld(WorldTestUtils.createLevels(WorldTestUtils.createEmptyLevel()), getHeroUnit());
  }

  private DefaultHeroUnit getHeroUnit() {
    return WorldTestUtils.createDefaultHeroUnit();
  }

  private DefaultUnit getPlayer() {
    return createDefaultPlayerKnight();
  }

  private DefaultUnit getEnemy() {
    return createDefaultEnemyOrc();
  }

  @Test
  public void testDamageEnemy() {
    DefaultUnit unit1 = getPlayer();
    DefaultUnit unit2 = getEnemy();
    World world = getWorld();
    unit1.setTarget(unit2, world);
    int prevHealth = unit2.getHealth();
    for (int i = 0; i < 820; i++) {
      unit1.tick(GameModel.DELAY, world);
    }
    assertEquals(prevHealth - damageAmt, unit2.getHealth());
  }
}
