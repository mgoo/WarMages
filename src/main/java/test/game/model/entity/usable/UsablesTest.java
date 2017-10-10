package test.game.model.entity.usable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static test.game.model.world.WorldTestUtils.createHeroUnit;

import java.util.Arrays;
import java.util.Collections;
import main.game.model.GameModel;
import main.game.model.entity.HeroUnit;
import main.game.model.entity.UnitType;
import main.game.model.entity.exceptions.UsableStillInCoolDownException;
import main.game.model.entity.usable.DamageBuffAbility;
import main.game.model.entity.usable.HealAbility;
import main.game.model.entity.usable.Item;
import main.game.model.entity.usable.Usable;
import main.game.model.world.World;
import main.images.GameImageResource;
import main.images.UnitSpriteSheet;
import main.common.util.MapPoint;
import main.common.util.MapSize;
import main.common.util.TickTimer;
import org.junit.Test;

public class UsablesTest {

  private World stubWorld = mock(World.class);

  @Test
  public void healAbilityShouldIncreaseHealth() {
    HealAbility healAbility = new HealAbility(
        GameImageResource.POTION_BLUE_ITEM.getGameImage(),
        2,
        3
    );
    HeroUnit heroUnit = new HeroUnit(
        new MapPoint(1, 1),
        new MapSize(1, 1),
        new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER,
        Arrays.asList(healAbility)
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
    HeroUnit heroUnit = new HeroUnit(
        new MapPoint(1, 1),
        new MapSize(1, 1),
        new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER,
        Arrays.asList()
    );

    HealAbility healAbility = new HealAbility(
        GameImageResource.POTION_BLUE_ITEM.getGameImage(),
        2,
        3
    );
    Item healItem = new Item(
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
    when(world.getAllEntities()).thenReturn(Arrays.asList(heroUnit));
    when(world.getAllUnits()).thenReturn(Arrays.asList(heroUnit));

    // when the hero takes damage
    heroUnit.takeDamage(heroUnit.getHealth() - 1, stubWorld);
    int lowHealth = heroUnit.getHealth();
    // and the heal is used
    healer.use(world, Collections.emptyList());

    // then the health should go up
    int firstNewHealth = heroUnit.getHealth();
    assertEquals(lowHealth + healAmount, firstNewHealth);
    // and ability should be on cool-down
    assertFalse(healer.isReadyToBeUsed());

    // when the ability finally cools down
    for (int i = 0; i < coolDownTicks; i++) {
      heroUnit.tick(GameModel.DELAY, stubWorld); // should tick usable
    }
    // and the heal is used again
    healer.use(world, Collections.emptyList());

    // then health should increase again
    int secondNewHealth = heroUnit.getHealth();
    assertEquals(firstNewHealth + healAmount, secondNewHealth);
  }

  @Test
  public void healAbilityShouldThrowOnlyIfCoolingDown() {
    // Given a hero unit
    HeroUnit heroUnit = createHeroUnit();
    // and a heal ability
    HealAbility healAbility = new HealAbility(
        GameImageResource.POTION_BLUE_ITEM.getGameImage(),
        2,
        3
    );
    assertTrue(healAbility.getCoolDownTicks() > 0); // sanity check
    // and a mock world
    World world = mock(World.class);
    when(world.getAllEntities()).thenReturn(Arrays.asList(heroUnit));
    when(world.getAllUnits()).thenReturn(Arrays.asList(heroUnit));

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
    HeroUnit heroUnit = new HeroUnit(
        new MapPoint(1, 1),
        new MapSize(1, 1),
        new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER,
        Arrays.asList(buffAbility)
    );
    int baseDamageAmount = heroUnit.getDamageAmount();
    // and a mock world
    World world = mock(World.class);
    when(world.getAllEntities()).thenReturn(Arrays.asList(heroUnit));
    when(world.getAllUnits()).thenReturn(Arrays.asList(heroUnit));

    // when we use the buff
    buffAbility.use(world, Collections.emptyList());

    // damageAmount should increase for several ticks
    int effectDurationTicks = TickTimer.secondsToTicks(buffAbility.getEffectDurationSeconds());
    for (int i = 0; i < effectDurationTicks; i++) {
      int buffedDamageAmount = heroUnit.getDamageAmount();
      assertEquals(baseDamageAmount + damageIncrease, buffedDamageAmount);
      heroUnit.tick(GameModel.DELAY, stubWorld); // should tick ability
    }

    // then damageAmount should go back to normal
    assertEquals(baseDamageAmount, heroUnit.getDamageAmount());
  }
}
