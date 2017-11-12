package test.game.model.entity.usable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static test.game.model.entity.DefaultUnitTest.getHeroUnit;

import java.util.Collections;
import main.common.GameModel;
import main.common.World;
import main.common.entity.HeroUnit;
import main.common.entity.Team;
import main.common.entity.Unit;
import main.common.entity.Usable;
import main.common.entity.usable.Item;
import main.common.exceptions.UsableStillInCoolDownException;
import main.common.images.GameImageResource;
import main.common.util.MapPoint;
import main.common.util.MapSize;
import main.common.util.TickTimer;
import main.game.model.entity.unit.DefaultHeroUnit;
import main.game.model.entity.unit.DefaultUnit;
import main.game.model.entity.unit.UnitType;
import main.game.model.entity.usable.DamageBuffAbility;
import main.game.model.entity.usable.DefaultItem;
import main.game.model.entity.usable.HealAbility;
import org.junit.Test;
import test.game.model.entity.StubUnitSpriteSheet;

/**
 * Test for {@link Usable} implementations in combination with {@link DefaultUnit}.
 * @author chongdyla
 */
public class UsablesTest {

  private World stubWorld = mock(World.class);

  @Test
  public void healAbilityShouldIncreaseHealth() {
    HealAbility healAbility = new HealAbility(
        GameImageResource.POTION_BLUE_ITEM.getGameImage(),
        2,
        3
    );
    HeroUnit heroUnit = new DefaultHeroUnit(
        new MapPoint(1, 1),
        new MapSize(1, 1),
        new StubUnitSpriteSheet(),
        UnitType.ARCHER,
        Collections.singletonList(healAbility),
        0
    );

    healUsableShouldIncreaseHealth(
        heroUnit,
        healAbility,
        healAbility.getHealAmount(),
        healAbility.getCoolDownTicks()
    );
  }

  @Test
  public void healItemShouldIncreaseHealth() {
    HeroUnit heroUnit = new DefaultHeroUnit(
        new MapPoint(1, 1),
        new MapSize(1, 1),
        new StubUnitSpriteSheet(),
        UnitType.ARCHER,
        Collections.emptyList(),
        0
    );

    HealAbility healAbility = new HealAbility(
        GameImageResource.POTION_BLUE_ITEM.getGameImage(),
        2,
        3
    );
    Item healItem = new DefaultItem(
        heroUnit.getTopLeft().translate(0.001, 0.001),
        healAbility,
        GameImageResource.POTION_BLUE_ITEM.getGameImage()
    );
    heroUnit.pickUp(healItem);

    healUsableShouldIncreaseHealth(
        heroUnit,
        healItem,
        healAbility.getHealAmount(),
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
        new StubUnitSpriteSheet(),
        UnitType.SWORDSMAN,
        1
    );

    // when the hero takes damage
    heroUnit.takeDamage(heroUnit.getHealth() - 1, stubWorld, enemy);
    double lowHealth = heroUnit.getHealth();
    // and the heal is used
    healer.use(world, Collections.singletonList(heroUnit));

    // then the health should go up
    double firstNewHealth = heroUnit.getHealth();
    assertEquals(lowHealth + healAmount, firstNewHealth, 0.001);
    // and ability should be on cool-down
    assertFalse(healer.isReadyToBeUsed());

    // when the ability finally cools down
    for (int i = 0; i < coolDownTicks; i++) {
      heroUnit.tick(GameModel.DELAY, stubWorld); // should tick usable
    }
    // and the heal is used again
    healer.use(world, Collections.singletonList(heroUnit));

    // then health should increase again
    double secondNewHealth = heroUnit.getHealth();
    assertEquals(firstNewHealth + healAmount, secondNewHealth, 0.001);
  }

  @Test
  public void healAbilityShouldThrowOnlyIfCoolingDown() {
    // Given a hero unit
    HeroUnit heroUnit = getHeroUnit();
    // and a heal ability
    HealAbility healAbility = new HealAbility(
        GameImageResource.POTION_BLUE_ITEM.getGameImage(),
        2,
        3
    );
    assertTrue(healAbility.getCoolDownTicks() > 0); // sanity check
    // and a mock world
    World world = mock(World.class);
    when(world.getAllEntities()).thenReturn(Collections.singletonList(heroUnit));
    when(world.getAllUnits()).thenReturn(Collections.singletonList(heroUnit));

    // when ability is used
    healAbility.use(world, Collections.emptyList()); // should be ok
    try {
      // and we try to use it again
      healAbility.use(world, Collections.emptyList());
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
    healAbility.use(world, Collections.emptyList());
  }

  @Test
  public void buffAbilityIncreasesDamageOutputForAWhile() {
    // Given a DamageBuffAbility
    int damageIncrease = 3;
    DamageBuffAbility buffAbility = new DamageBuffAbility(
        GameImageResource.POTION_BLUE_ITEM.getGameImage(),
        damageIncrease,
        2,
        1
    );
    // and a hero unit with the ability
    HeroUnit heroUnit = new DefaultHeroUnit(
        new MapPoint(1, 1),
        new MapSize(1, 1),
        new StubUnitSpriteSheet(),
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
    buffAbility.use(world, Collections.singletonList(heroUnit));

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
