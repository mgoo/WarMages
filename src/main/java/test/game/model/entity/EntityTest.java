package test.game.model.entity;

import main.game.model.entity.*;
import main.images.GameImage;
import main.images.GameImageResource;
import main.images.UnitSpriteSheet;
import main.util.MapPoint;

import static org.junit.Assert.*;

import main.util.MapSize;
import org.junit.Test;

public class EntityTest {

  private final int archerDamage = 5;
  private final int buffDamage = 10;

  //test changing item position but position isn't changed (item shouldn't move).
  @Test
  public void test_no_change_position() {
    BuffItem buff = new BuffItem(new MapPoint(20, 40), GameImageResource.POTION_BLUE_ITEM.getGameImage());
    buff.moveX(20);
    assertEquals(buff.getTopLeft(), new MapPoint(20, 40));
  }

  //test unit cannot attack another teammate.
  @Test
  public void test_damage_teammate() {
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
  public void test_damage() {
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
  public void test_damage_enemy() {
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
  public void test_heal() {
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
  public void test_heal_teammate() {
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
  public void test_heal_enemy() {
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
  public void test_use_healing_item() {
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
    HealingItem healing = new HealingItem(new MapPoint(50, 150), GameImageResource.RING_BLUE_ITEM.getGameImage());
    healing.applyTo(unit1);
    int prevHealth = unit2.getHealth();
    unit1.attack(unit2);
    assertEquals(prevHealth + archerDamage, unit2.getHealth());
  }

  @Test
  public void test_use_buff_item() {
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
    BuffItem buff = new BuffItem(new MapPoint(150, 150), GameImageResource.POTION_BLUE_ITEM.getGameImage());
    buff.applyTo(unit1);
    int prevHealth = unit2.getHealth();
    unit1.attack(unit2);
    assertEquals(prevHealth - buffDamage, unit2.getHealth());
  }

  @Test
  public void test_use_buff_ability() {
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
    BuffItem buff = new BuffItem(new MapPoint(150, 150), GameImageResource.POTION_BLUE_ITEM.getGameImage());
    buff.applyTo(unit1);
    int prevHealth = unit2.getHealth();
    unit1.attack(unit2);
    assertEquals(prevHealth - buffDamage, unit2.getHealth());
  }

  @Test
  public void test_team_attackable() {
    assertTrue(Team.ENEMY.canAttack(Team.PLAYER));
    assertFalse(Team.PLAYER.canAttack(Team.PLAYER));
  }

  @Test
  public void test_pizza_ball_hit() {
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
  public void test_healing_sphere_hit() {
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
