package test.game.model.entity;

import static org.junit.Assert.assertEquals;
import static test.game.model.world.WorldTestUtils.createDefaultEnemyOrc;
import static test.game.model.world.WorldTestUtils.createDefaultPlayerKnight;
import static test.game.model.world.WorldTestUtils.createHeroUnit;

import main.game.model.entity.HeroUnit;
import main.game.model.entity.Unit;
import main.game.model.entity.usable.DamageBuffAbility;
import main.game.model.entity.usable.Item;
import main.game.model.world.World;
import main.images.GameImageResource;
import main.util.MapPoint;
import org.junit.Test;
import test.game.model.world.WorldTestUtils;

public class EntityTest {

  private final int damageAmt = 5;

  private World getWorld() {
    return WorldTestUtils
        .createWorld(WorldTestUtils.createLevels(WorldTestUtils.createEmptyLevel()), getHeroUnit());
  }

  private HeroUnit getHeroUnit() {
    return createHeroUnit();
  }

  private Unit getPlayer() {
    return createDefaultPlayerKnight();
  }

  private Unit getEnemy() {
    return createDefaultEnemyOrc();
  }

  //test changing item position but position isn't changed (item shouldn't move).
  @Test
  public void testItemNotChangingPosition() {
    Item buff = new Item(
        new MapPoint(1, 1),
        new DamageBuffAbility(GameImageResource.RING_GOLD_ITEM.getGameImage(), 5, 1D, 2D),
        GameImageResource.RING_GOLD_ITEM.getGameImage()
    );
    MapPoint original = buff.getCentre();
    buff.moveX(20D);
    buff.moveY(10D);
    assertEquals(original, buff.getCentre());
  }

  //test unit cannot attack another teammate.
  @Test
  public void testDamageTeammate() {
    Unit unit1 = getPlayer();
    Unit unit2 = getPlayer();
    World world = getWorld();
    unit1.setTarget(unit2, world);
    int healthBefore = unit2.getHealth();
    for(int i=0; i<100; i++) {
      unit1.tick(100, world);
    }
    assertEquals(healthBefore, unit2.getHealth());
  }

  @Test
  public void testDamageEnemy() {
    Unit unit1 = getPlayer();
    Unit unit2 = getEnemy();
    World world = getWorld();
    unit1.setTarget(unit2, world);
    int prevHealth = unit2.getHealth();
    for(int i=0; i<100; i++) {
      unit1.tick(100, world);
    }
    assertEquals(prevHealth - damageAmt, unit2.getHealth());
  }
}
