package test.game.model.entity.usable;

import static main.images.GameImageResource.GOLDEN_HERO_SPRITE_SHEET;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static test.game.model.entity.DefaultUnitTest.getHeroUnit;

import java.util.Collections;
import main.exceptions.UsableStillInCoolDownException;
import main.game.model.GameModel;
import main.game.model.entity.HeroUnit;
import main.game.model.entity.Team;
import main.game.model.entity.Unit;
import main.game.model.entity.unit.DefaultHeroUnit;
import main.game.model.entity.unit.DefaultUnit;
import main.game.model.entity.unit.UnitType;
import main.game.model.entity.unit.attack.DamageBuff;
import main.game.model.entity.unit.attack.HealAttack;
import main.game.model.entity.usable.Ability;
import main.game.model.entity.usable.AttackUnitAbility;
import main.game.model.entity.usable.DefaultItem;
import main.game.model.entity.usable.Item;
import main.game.model.entity.usable.Usable;
import main.game.model.world.World;
import main.images.DefaultUnitSpriteSheet;
import main.images.GameImageResource;
import main.util.MapPoint;
import main.util.MapSize;
import main.util.TickTimer;
import org.junit.Test;

/**
 * Test for {@link Usable} implementations in combination with {@link DefaultUnit}.
 * @author chongdyla
 */
public class UsablesTest {

  private World stubWorld = mock(World.class);

  @Test
  public void healAbilityShouldIncreaseHealth() {
    Ability healAbility = new AttackUnitAbility(
        GameImageResource.HEAL_SPELL_ICON.getGameImage(),
        2,
        new HealAttack(3),
        "Heals a unit instantly"
    );
    HeroUnit heroUnit = new DefaultHeroUnit(
        new MapPoint(1, 1),
        new MapSize(1, 1),
        new DefaultUnitSpriteSheet(GOLDEN_HERO_SPRITE_SHEET),
        UnitType.ARCHER,
        Collections.singletonList(healAbility),
        0
    );

    healUsableShouldIncreaseHealth(
        heroUnit,
        healAbility,
        6,
        healAbility.getCoolDownTicks()
    );
  }

  @Test
  public void healItemShouldIncreaseHealth() {
    HeroUnit heroUnit = new DefaultHeroUnit(
        new MapPoint(1, 1),
        new MapSize(1, 1),
        new DefaultUnitSpriteSheet(GOLDEN_HERO_SPRITE_SHEET),
        UnitType.ARCHER,
        Collections.emptyList(),
        0
    );

    Ability healAbility = new AttackUnitAbility(
        GameImageResource.HEAL_SPELL_ICON.getGameImage(),
        2,
        new HealAttack(3),
        "Heals a unit instantly"
    );
    Item healItem = new DefaultItem(
        heroUnit.getTopLeft().translate(0.001, 0.001),
        healAbility,
        GameImageResource.POTION_BLUE_ITEM.getGameImage(),
        ""
    );
    heroUnit.pickUp(healItem);

    healUsableShouldIncreaseHealth(
        heroUnit,
        healItem,
        6,
        healAbility.getCoolDownTicks()
    );
  }

  private void healUsableShouldIncreaseHealth(
      HeroUnit heroUnit,
      Usable healer,
      int healAmount,
      int coolDownTicks
  ) {
    // Given a heal with a heal usable
    assertTrue(healAmount > 0);
    // and a mock world
    World world = mock(World.class);
    when(world.getAllEntities()).thenReturn(Collections.singletonList(heroUnit));
    when(world.getAllUnits()).thenReturn(Collections.singletonList(heroUnit));
    // and an enemy
    Unit enemy = new DefaultUnit(
        heroUnit.getCentre(),
        heroUnit.getSize(),
        Team.ENEMY,
        new DefaultUnitSpriteSheet(GOLDEN_HERO_SPRITE_SHEET),
        UnitType.SWORDSMAN,
        1
    );

    // when the hero takes damage
    heroUnit.takeDamage(heroUnit.getHealth() - 1, stubWorld, enemy);
    final double lowHealth = heroUnit.getHealth();
    // and the heal is used
    healer.use(world, heroUnit);

    // and ability should be on cool-down
    assertFalse(healer.isReadyToBeUsed());

    // Wait for cast
    // when the ability finally cools down
    for (int i = 0; i < coolDownTicks; i++) {
      heroUnit.tick(GameModel.DELAY, stubWorld); // should tick usable
    }
    // then the health should go up
    double firstNewHealth = heroUnit.getHealth();
    assertEquals(lowHealth + healAmount, firstNewHealth, 0.001);

    // and the heal is used again
    healer.use(world, heroUnit);

    // Wait for cast
    // when the ability finally cools down
    for (int i = 0; i < coolDownTicks; i++) {
      heroUnit.tick(GameModel.DELAY, stubWorld); // should tick usable
    }
    // then health should increase again
    double secondNewHealth = heroUnit.getHealth();
    assertEquals(firstNewHealth + healAmount, secondNewHealth, 0.001);
  }

  @Test
  public void healAbilityShouldThrowOnlyIfCoolingDown() {
    // Given a hero unit
    HeroUnit heroUnit = getHeroUnit();
    // and a heal ability
    Ability healAbility = new AttackUnitAbility(
        GameImageResource.HEAL_SPELL_ICON.getGameImage(),
        2,
        new HealAttack(3),
        "Heals a unit instantly"
    );
    healAbility.setOwner(heroUnit);
    assertTrue(healAbility.getCoolDownTicks() > 0); // sanity check
    // and a mock world
    World world = mock(World.class);
    when(world.getAllEntities()).thenReturn(Collections.singletonList(heroUnit));
    when(world.getAllUnits()).thenReturn(Collections.singletonList(heroUnit));

    // when ability is used
    healAbility.use(world, heroUnit);
    try {
      // and we try to use it again
      healAbility.use(world, heroUnit);
      fail();
    } catch (UsableStillInCoolDownException ignored) {
      // then it should fail
    }

    // when some time passes
    for (int i = 0; i < healAbility.getCoolDownTicks(); i++) {
      assertFalse(healAbility.isReadyToBeUsed());
      healAbility.usableTick(GameModel.DELAY);
    }

    // then we should be able to use the ability again
    assertTrue(healAbility.isReadyToBeUsed());
    healAbility.use(world, heroUnit);
  }

  @Test
  public void buffAbilityIncreasesDamageOutputForAWhile() {
    // Given a DamageBuffAbility
    int damageIncrease = 3;
    Ability buffAbility = new AttackUnitAbility(
        GameImageResource.RING_ICON.getGameImage(),
        20,
        new DamageBuff(10, 1),
        "Buff the damage on a friendly unit"
    );
    // and a hero unit with the ability
    HeroUnit heroUnit = new DefaultHeroUnit(
        new MapPoint(1, 1),
        new MapSize(1, 1),
        new DefaultUnitSpriteSheet(GOLDEN_HERO_SPRITE_SHEET),
        UnitType.ARCHER,
        Collections.singletonList(buffAbility),
        0
    );
    double baseDamageAmount = heroUnit.getDamageModifier();
    // and a mock world
    World world = mock(World.class);
    when(world.getAllEntities()).thenReturn(Collections.singletonList(heroUnit));
    when(world.getAllUnits()).thenReturn(Collections.singletonList(heroUnit));

    // when we use the buff
    buffAbility.use(world, heroUnit);

    // damageAmount should increase for several ticks
    int effectDurationTicks = TickTimer.secondsToTicks(buffAbility.getEffectDurationSeconds());
    for (int i = 0; i < effectDurationTicks; i++) {
      double buffedDamageAmount = heroUnit.getDamageModifier();
      assertEquals(baseDamageAmount + damageIncrease, buffedDamageAmount, 0.001);
      heroUnit.tick(GameModel.DELAY, stubWorld); // should tick ability
    }

    // then damageAmount should go back to normal
    assertEquals(baseDamageAmount, heroUnit.getDamageModifier(), 0.001);
  }
}
