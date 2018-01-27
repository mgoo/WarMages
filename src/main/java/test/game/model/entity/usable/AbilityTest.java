package test.game.model.entity.usable;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static test.game.model.entity.DefaultUnitTest.getHeroUnit;

import java.util.Collections;
import main.exceptions.UsableStillInCoolDownException;
import main.game.model.GameModel;
import main.game.model.entity.HeroUnit;
import main.game.model.entity.unit.DefaultUnit;
import main.game.model.entity.unit.attack.AttackCache;
import main.game.model.entity.usable.Ability;
import main.game.model.world.DefaultWorld;
import main.game.model.world.World;
import org.junit.Test;

/**
 * Test for {@link Usable} implementations in combination with {@link DefaultUnit}.
 *
 * @author chongdyla
 */
public class AbilityTest {

  @Test
  public void testAbilitiesShouldThrowOnlyIfCoolingDown() {
    // Given a hero unit
    HeroUnit heroUnit = getHeroUnit();
    // and a heal ability
    Ability healAbility = AttackCache.TEST_HEAL;
    healAbility.setOwner(heroUnit);
    assertTrue(healAbility.getCoolDownTicks() > 0); // sanity check
    // and a mock world
    World world = mock(DefaultWorld.class);
    when(world.getAllEntities()).thenReturn(Collections.singletonList(heroUnit));
    when(world.getAllUnits()).thenReturn(Collections.singletonList(heroUnit));
    doCallRealMethod().when(world).tick(anyLong());

    // when ability is used
    healAbility.startCoolDown();
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
}
