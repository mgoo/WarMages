package test.game.model.entity;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import main.game.model.entity.BuffItem;
import main.game.model.entity.HealingItem;
import main.game.model.entity.HealingSphere;
import main.game.model.entity.PizzaBall;
import main.game.model.entity.Team;
import main.game.model.entity.Unit;
import main.game.model.entity.UnitType;
import main.images.GameImageResource;
import main.images.UnitSpriteSheet;
import main.util.MapPoint;
import main.util.MapSize;
import org.junit.Test;

public class EntityTest {

  private final int archerDamage = 5;
  private final int buffDamage = 10;

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
    Unit unit1 = new Unit(
        new MapPoint(20, 20),
        new MapSize(5, 5),
        Team.PLAYER, new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER
    );
    Unit unit2 = new Unit(
        new MapPoint(20, 20),
        new MapSize(5, 5),
        Team.PLAYER, new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER
    );
    int healthBefore = unit2.getHealth();
    unit1.attack(unit2);
    assertEquals(healthBefore, unit2.getHealth());
  }

  @Test
  public void testDamage() {
    Unit unit = new Unit(
        new MapPoint(20, 20),
        new MapSize(5, 5),
        Team.PLAYER, new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER
    );
    int prevHealth = unit.getHealth();
    unit.takeDamage(archerDamage);
    assertEquals(prevHealth - archerDamage, unit.getHealth());
  }

  @Test
  public void testDamageEnemy() {
    Unit unit1 = new Unit(
        new MapPoint(20, 20),
        new MapSize(5, 5),
        Team.PLAYER, new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER
    );
    Unit unit2 = new Unit(
        new MapPoint(20, 20),
        new MapSize(5, 5),
        Team.ENEMY, new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER
    );
    int prevHealth = unit2.getHealth();
    unit1.attack(unit2);
    assertEquals(prevHealth - archerDamage, unit2.getHealth());
  }

  @Test
  public void testHeal() {
    Unit unit = new Unit(
        new MapPoint(20, 20),
        new MapSize(5, 5),
        Team.PLAYER, new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER
    );
    int prevHealth = unit.getHealth();
    unit.gainHealth(archerDamage);
    assertEquals(prevHealth + archerDamage, unit.getHealth());
  }

  @Test
  public void testHealTeammate() {
    Unit unit1 = new Unit(
        new MapPoint(20, 20),
        new MapSize(5, 5),
        Team.PLAYER, new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER
    );
    Unit unit2 = new Unit(
        new MapPoint(20, 20),
        new MapSize(5, 5),
        Team.PLAYER, new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER
    );
    //note: archer baseline damage is 5
    int prevHealth = unit2.getHealth();
    unit1.setHealing(true);
    unit1.attack(unit2);
    assertEquals(prevHealth + archerDamage, unit2.getHealth());
  }

  @Test
  public void testHealEnemy() {
    Unit unit1 = new Unit(
        new MapPoint(20, 20),
        new MapSize(5, 5),
        Team.PLAYER, new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER
    );
    Unit unit2 = new Unit(
        new MapPoint(20, 20),
        new MapSize(5, 5),
        Team.ENEMY, new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER
    );
    int prevHealth = unit2.getHealth();
    unit1.setHealing(true);
    unit1.attack(unit2);
    assertEquals(prevHealth, unit2.getHealth());
  }

  @Test
  public void testUseHealingItem() {
    Unit unit1 = new Unit(
        new MapPoint(20, 20),
        new MapSize(5, 5),
        Team.PLAYER, new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER
    );
    Unit unit2 = new Unit(
        new MapPoint(50, 20),
        new MapSize(5, 5),
        Team.PLAYER, new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER
    );
    HealingItem healing = new HealingItem(
        new MapPoint(50, 150), GameImageResource.RING_BLUE_ITEM.getGameImage());
    healing.applyTo(unit1);
    int prevHealth = unit2.getHealth();
    unit1.attack(unit2);
    assertEquals(prevHealth + archerDamage, unit2.getHealth());
  }

  @Test
  public void testUseBuffItem() {
    Unit unit1 = new Unit(
        new MapPoint(20, 20),
        new MapSize(5, 5),
        Team.PLAYER, new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER
    );
    Unit unit2 = new Unit(
        new MapPoint(50, 20),
        new MapSize(5, 5),
        Team.ENEMY, new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER
    );
    BuffItem buff = new BuffItem(
        new MapPoint(150, 150), GameImageResource.POTION_BLUE_ITEM.getGameImage());
    buff.applyTo(unit1);
    unit1.setTarget(unit2);
    int prevHealth = unit2.getHealth();
    unit1.attack(unit2);
    assertEquals(prevHealth - buffDamage, unit2.getHealth());
  }

  @Test
  public void testUseBuffAbility() {
    //note that buff currently increases damage to 10
    Unit unit1 = new Unit(
        new MapPoint(20, 20),
        new MapSize(5, 5),
        Team.PLAYER, new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER
    );
    Unit unit2 = new Unit(
        new MapPoint(50, 20),
        new MapSize(5, 5),
        Team.ENEMY, new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER
    );
    unit1.setTarget(unit2);
    BuffItem buff = new BuffItem(
        new MapPoint(150, 150), GameImageResource.POTION_BLUE_ITEM.getGameImage());
    buff.applyTo(unit1);
    int prevHealth = unit2.getHealth();
    unit1.attack(unit2);
    assertEquals(prevHealth - buffDamage, unit2.getHealth());
  }

  @Test
  public void testTeamAttackable() {
    assertTrue(Team.ENEMY.canAttack(Team.PLAYER));
    assertFalse(Team.PLAYER.canAttack(Team.PLAYER));
  }

  @Test
  public void testPizzaballHit() {
    //note that pizza ball damage = 5
    Unit unit = new Unit(
        new MapPoint(20, 20),
        new MapSize(5, 5),
        Team.PLAYER, new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER
    );
    PizzaBall pizza = new PizzaBall(
        new MapPoint(22, 22),
        new MapSize(2, 2),
        unit
    );
    int prevHealth = unit.getHealth();
    pizza.hits(unit);
    assertEquals(prevHealth - archerDamage, unit.getHealth());
  }

  @Test
  public void testHealingSphereHit() {
    //note that healing sphere heals 5
    Unit unit = new Unit(
        new MapPoint(20, 20),
        new MapSize(5, 5),
        Team.PLAYER,
        new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER
    );
    HealingSphere sphere = new HealingSphere(
        new MapPoint(22, 22),
        new MapSize(2, 2),
        unit
    );
    int prevHealth = unit.getHealth();
    sphere.hits(unit);
    assertEquals(prevHealth + archerDamage, unit.getHealth());
  }
}
