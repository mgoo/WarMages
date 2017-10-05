package test.game.model.entity.usables;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static test.game.model.world.WorldTestUtils.createHeroUnit;

import java.util.Arrays;
import main.game.model.GameModel;
import main.game.model.entity.HeroUnit;
import main.game.model.entity.UnitType;
import main.game.model.entity.exceptions.UsableStillInCoolDownException;
import main.game.model.entity.usables.DamageBuffAbility;
import main.game.model.entity.usables.HealAbility;
import main.game.model.world.World;
import main.images.GameImageResource;
import main.images.UnitSpriteSheet;
import main.util.MapPoint;
import main.util.MapSize;
import main.util.TickTimer;
import org.junit.Test;

public class UsablesTest {

  private World stubWorld = mock(World.class);

  @Test
  public void healAbilityShouldIncreaseHealth() {
    // Given a heal ability
    HealAbility healAbility = new HealAbility(
        GameImageResource.POTION_BLUE_ITEM.getGameImage(),
        2,
        3
    );

    int healAmount = healAbility.getHealAmount();
    assertTrue(healAmount > 0);
    // and a heroUnit with the ability
    HeroUnit heroUnit = new HeroUnit(
        new MapPoint(1, 1),
        new MapSize(1, 1),
        new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER,
        Arrays.asList(healAbility)
    );

    // when the hero takes damage
    heroUnit.takeDamage(heroUnit.getHealth() - 1);
    int lowHealth = heroUnit.getHealth();
    // and the healAbility is used
    healAbility.useOnUnits(Arrays.asList(heroUnit));

    // then the health should go up
    int firstNewHealth = heroUnit.getHealth();
    assertEquals(lowHealth + healAmount, firstNewHealth);
    // and ability should be on cool-down
    assertFalse(healAbility.isReadyToBeUsed());

    // when the ability finally cools down
    for (int i = 0; i < healAbility.getCoolDownTicks(); i++) {
      heroUnit.tick(GameModel.DELAY, stubWorld); // should tick ability
    }
    // and the healAbility is used again
    healAbility.useOnUnits(Arrays.asList(heroUnit));

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

    // when ability is used
    healAbility.useOnUnits(Arrays.asList(heroUnit)); // should be ok
    try {
      // and we try to use it again
      healAbility.useOnUnits(Arrays.asList(heroUnit));
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
    healAbility.useOnUnits(Arrays.asList(heroUnit));
  }

  @Test
  public void buffAbilityIncreasesDamageOutputForAWhile() {
    // Given a DamageBuffAbility
    int damageIncrease = 3;
    DamageBuffAbility buffAbility = new DamageBuffAbility(
        GameImageResource.POTION_BLUE_ITEM.getGameImage(),
        damageIncrease,
        1,
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

    // when we use the buff
    buffAbility.useOnUnits(Arrays.asList(heroUnit));

    // damageAmount should increase for several ticks
    int effectDurationTicks = TickTimer.secondsToTicks(buffAbility.getEffectDurationSeconds());
    for (int i = 0; i < effectDurationTicks; i++) {
      int buffedDamageAmount = heroUnit.getDamageAmount();
      assertEquals(baseDamageAmount + damageIncrease, buffedDamageAmount);
      heroUnit.tick(GameModel.DELAY, stubWorld); // should tick ability
    }

    // then damageAmount should go back to normal
    assertEquals(baseDamageAmount, heroUnit.getDamageAmount());

    // when some time passes
    for (int i = 0; i < buffAbility.getCoolDownTicks(); i++) {
      heroUnit.tick(GameModel.DELAY, stubWorld); // should tick ability
    }

    // then the ability should be ready to be used
    assertTrue(buffAbility.isReadyToBeUsed());
  }
}
