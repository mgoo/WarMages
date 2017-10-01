package test.game.model.entity;

import main.game.model.entity.*;
import main.images.GameImageResource;
import main.images.UnitSpriteSheet;
import main.util.MapPoint;
import static org.junit.Assert.*;

import main.util.MapSize;
import org.junit.Test;

public class EntityTest {

  //test changing item position but position isn't changed (item shouldn't move).
  @Test
  public void test_no_change_position(){
    BuffItem buff = new BuffItem(new MapPoint(20, 40));
    buff.moveX(20);
    assertEquals(buff.getPosition(), new MapPoint(20, 40));
  }

  //test unit cannot attack another teammate.
  @Test
  public void test_damage_teammate(){
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
    int healthBefore = unit2.getCurrentHealth();
    unit1.attack(unit2);
    assertEquals(healthBefore, unit2.getCurrentHealth());
  }

  @Test
  public void test_damage(){
    Unit unit = new Unit(
        new MapPoint(20, 20),
        new MapSize(5, 5),
        Team.PLAYER, new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER
    );
    int prevHealth = unit.getCurrentHealth();
    unit.takeDamage(5);
    assertEquals(prevHealth-5, unit.getCurrentHealth());
  }

  @Test
  public void test_damage_enemy(){
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
    //note: archer baseline damage is 5
    int prevHealth = unit2.getCurrentHealth();
    unit1.attack(unit2);
    assertEquals(prevHealth-5, unit2.getCurrentHealth());
  }

  @Test
  public void test_heal(){
    Unit unit = new Unit(
        new MapPoint(20, 20),
        new MapSize(5, 5),
        Team.PLAYER, new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER
    );
    int prevHealth = unit.getCurrentHealth();
    unit.gainHealth(5);
    assertEquals(prevHealth+5, unit.getCurrentHealth());
  }

  @Test
  public void test_heal_teammate(){
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
    int prevHealth = unit2.getCurrentHealth();
    unit1.setHealing(true);
    unit1.attack(unit2);
    assertEquals(prevHealth+5, unit2.getCurrentHealth());
  }

  @Test
  public void test_heal_enemy(){
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
    //note: archer baseline damage is 5
    int prevHealth = unit2.getCurrentHealth();
    unit1.setHealing(true);
    unit1.attack(unit2);
    assertEquals(prevHealth, unit2.getCurrentHealth());
  }

  @Test
  public void test_use_healing_item(){
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
    HealingItem healing  = new HealingItem(new MapPoint(50, 150));
    healing.applyTo(unit1);
    int prevHealth = unit2.getCurrentHealth();
    unit1.attack(unit2);
    assertEquals(prevHealth+5, unit2.getCurrentHealth());
  }

  @Test
  public void test_use_buff_item(){
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
    BuffItem buff = new BuffItem(new MapPoint(150, 150));
    buff.applyTo(unit1);
    int prevHealth = unit2.getCurrentHealth();
    unit1.attack(unit2);
    assertEquals(prevHealth-10, unit2.getCurrentHealth());
  }

  @Test
  public void test_team_attackable(){
    assertTrue(Team.ENEMY.canAttack(Team.PLAYER));
    assertFalse(Team.PLAYER.canAttack(Team.PLAYER));
  }


}
