package test.game.model.entity;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import main.game.model.entity.BuffItem;
import main.game.model.entity.HealingItem;
import main.game.model.entity.HealingSphere;
import main.game.model.entity.HeroUnit;
import main.game.model.entity.Team;
import main.game.model.entity.Unit;
import main.game.model.entity.UnitType;
import main.game.model.world.World;
import main.images.GameImageResource;
import main.images.UnitSpriteSheet;
import main.util.MapPoint;
import main.util.MapSize;
import org.junit.Test;
import test.game.model.world.WorldTestUtils;

public class EntityTest {

  private final int damageAmt = 5;
  private final int buffDamage = 10;

  private World getWorld() {
    return WorldTestUtils.createWorld(WorldTestUtils.createLevels(WorldTestUtils.createEmptyLevel()), getHeroUnit());
  }

  private HeroUnit getHeroUnit() {
    return getPlayer1();
  }

  private HeroUnit getPlayer1() {
    return new HeroUnit(
        new MapPoint(20, 20),
        new MapSize(5, 5),
        new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.SWORDSMAN
    );
  }

  private HeroUnit getPlayer2() {
    return new HeroUnit(
        new MapPoint(25, 20),
        new MapSize(5, 5),
        new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.SWORDSMAN
    );
  }

  private Unit getEnemy1() {
    return new Unit(
        new MapPoint(20, 20),
        new MapSize(5, 5),
        Team.ENEMY, new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.SWORDSMAN
    );
  }

  private Unit getEnemy2() {
    return new Unit(
        new MapPoint(20, 20),
        new MapSize(5, 5),
        Team.ENEMY, new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.SWORDSMAN
    );
  }

  //test changing item position but position isn't changed (item shouldn't move).
  @Test
  public void testNoChangePosition() {
    BuffItem buff = new BuffItem(
        new MapPoint(20, 40), GameImageResource.POTION_BLUE_ITEM.getGameImage());
    buff.moveX(20);
    assertEquals(buff.getTopLeft(), new MapPoint(20, 40));
  }

  //test unit cannot attack another teammate.
  @Test
  public void testDamageTeammate() {
    Unit unit1 = getPlayer1();
    Unit unit2 = getPlayer2();
    World world = getWorld();
    unit1.setTarget(unit2, world);
    int healthBefore = unit2.getHealth();
    unit1.tick(100, world);
    assertEquals(healthBefore, unit2.getHealth());
  }

  @Test
  public void testDamage() {
    Unit unit = getPlayer1();
    int prevHealth = unit.getHealth();
    unit.takeDamage(damageAmt);
    assertEquals(prevHealth - damageAmt, unit.getHealth());
  }

  @Test
  public void testDamageEnemy() {
    Unit unit1 = getPlayer1();
    Unit unit2 = getEnemy2();
    World world = getWorld();
    unit1.setTarget(unit2, world);
    int prevHealth = unit2.getHealth();
    unit1.tick(100, world);
    assertEquals(prevHealth - damageAmt, unit2.getHealth());
  }

  @Test
  public void testHeal() {
    Unit unit = getPlayer1();
    int prevHealth = unit.getHealth();
    unit.gainHealth(damageAmt);
    assertEquals(prevHealth + damageAmt, unit.getHealth());
  }

  @Test
  public void testHealTeammate() {
    Unit unit1 = getPlayer1();
    Unit unit2 = getPlayer2();
    World world = getWorld();
    unit1.setTarget(unit2, world);
    //note: archer baseline damage is 5
    int prevHealth = unit2.getHealth();
    unit1.setHealing(true);
    unit1.tick(100, world);
    assertEquals(prevHealth + damageAmt, unit2.getHealth());
  }

  @Test
  public void testHealEnemy() {
    Unit unit1 = getPlayer1();
    Unit unit2 = getEnemy2();
    unit1.setHealing(true);
    World world = getWorld();
    int prevHealth = unit2.getHealth();
    unit1.setTarget(unit2, world);
    unit1.tick(100, world);
    assertEquals(prevHealth, unit2.getHealth());
  }

  @Test
  public void testUseHealingItem() {
    Unit unit1 = getPlayer1();
    Unit unit2 = getPlayer2();
    World world = getWorld();
    unit1.setTarget(unit2, world);
    HealingItem healing = new HealingItem(
        new MapPoint(50, 150), GameImageResource.RING_BLUE_ITEM.getGameImage());
    healing.applyTo(unit1);
    int prevHealth = unit2.getHealth();
    unit1.tick(100, world);
    assertEquals(prevHealth + damageAmt, unit2.getHealth());
  }

  @Test
  public void testUseBuffItem() {
    Unit unit1 = getPlayer1();
    Unit unit2 = getEnemy2();
    BuffItem buff = new BuffItem(
        new MapPoint(150, 150), GameImageResource.POTION_BLUE_ITEM.getGameImage());
    buff.applyTo(unit1);
    World world = getWorld();
    unit1.setTarget(unit2, world);
    int prevHealth = unit2.getHealth();
    unit1.tick(100, world);
    assertEquals(prevHealth - buffDamage, unit2.getHealth());
  }

  @Test
  public void testUseBuffAbility() {
    //note that buff currently increases damage to 10
    Unit unit1 = getPlayer1();
    Unit unit2 = getEnemy2();
    World world = getWorld();
    unit1.setTarget(unit2, world);
    BuffItem buff = new BuffItem(
        new MapPoint(150, 150), GameImageResource.POTION_BLUE_ITEM.getGameImage());
    buff.applyTo(unit1);
    int prevHealth = unit2.getHealth();
    unit1.tick(100, getWorld());
    assertEquals(prevHealth - buffDamage, unit2.getHealth());
  }

  @Test
  public void testTeamAttackable() {
    assertTrue(Team.ENEMY.canAttack(Team.PLAYER));
    assertFalse(Team.PLAYER.canAttack(Team.PLAYER));
  }

  @Test
  public void testHealingSphereHit() {
    //note that healing sphere heals 5
    Unit unit = getPlayer1();
    HealingSphere sphere = new HealingSphere(
        new MapPoint(22, 22),
        new MapSize(2, 2),
        unit,
        GameImageResource.WHITE_BALL_PROJECTILE.getGameImage(),
        unit.getDamageAmount()
    );
    int prevHealth = unit.getHealth();
    sphere.hits(unit);
    assertEquals(prevHealth + damageAmt, unit.getHealth());
  }
}
