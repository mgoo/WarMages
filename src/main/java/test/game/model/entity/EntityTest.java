package test.game.model.entity;

import static org.junit.Assert.assertTrue;
import static test.game.model.world.WorldTestUtils.createDefaultEnemyOrc;
import static test.game.model.world.WorldTestUtils.createDefaultPlayerKnight;

import main.common.GameModel;
import main.common.entity.HeroUnit;
import main.common.entity.Unit;
import main.game.model.DefaultGameModel;
import main.common.World;
import org.junit.Test;
import test.game.model.world.WorldTestUtils;

/**
 * Tests for Entities.
 * TODO Gabei shouldnt this be in defaultunittest?.
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
    Unit player = getPlayer();
    Unit enemy = getEnemy();

    // Make sure they are in range of each other (by making them on top of each other)
    double dx = enemy.getCentre().x - player.getCentre().x;
    double dy = enemy.getCentre().y - player.getCentre().y;
    player.translatePosition(dx, dy);

    World world = getWorld();
    player.setTargetUnit(enemy);
    double prevHealth = enemy.getHealth();
    for (int i = 0; i < 900; i++) {
      player.tick(GameModel.DELAY, world);
    }
    assertTrue(prevHealth > enemy.getHealth());
  }
}
