package test.game.model.entity;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static test.game.model.entity.DefaultUnitTest.getHeroUnit;

import java.util.Arrays;
import main.exceptions.ItemNotInRangeException;
import main.game.model.GameModel;
import main.game.model.entity.HeroUnit;
import main.game.model.entity.unit.DefaultHeroUnit;
import main.game.model.entity.unit.UnitType;
import main.game.model.entity.unit.attack.AttackCache;
import main.game.model.entity.usable.Ability;
import main.game.model.entity.usable.AttackUnitAbility;
import main.game.model.entity.usable.DefaultItem;
import main.game.model.entity.usable.Item;
import main.game.model.world.World;
import main.images.GameImage;
import main.images.GameImageResource;
import main.util.MapPoint;
import main.util.MapSize;
import org.junit.Test;

/**
 * Some tests.
 *
 * @author chongdyla
 */
public class HeroUnitTest {

  private World mockWorld = mock(World.class);

  private HeroUnit heroUnit = getHeroUnit();

  /**
   * Returns a default item at the given position.
   *
   * @param pos of the item to be created.
   * @return item at pos.
   */
  public static Item getItem(MapPoint pos) {
    return new DefaultItem(
        pos,
        AttackCache.TEST_HEAL,
        mock(GameImage.class),
        ""
    );
  }

  @Test
  public void addingAnItemToTheInventoryShouldWorkWhenItemInRange() {
    Item item = getItem(heroUnit.getTopLeft().translate(0.001, 0.001));
    heroUnit.pickUp(item);
    assertTrue(heroUnit.getItemAbilities().contains(item.getAbility()));
  }

  @Test(expected = ItemNotInRangeException.class)
  public void addingAnItemToTheInventoryShouldNotWorkWhenItemIsFarAway() {
    Item item = getItem(heroUnit.getCentre().translate(100, 100));
    heroUnit.pickUp(item);
  }

  @Test
  public void heroUnitShouldTickAbilities() {
    // Given an ability
    Ability mockAbility = mock(Ability.class);
    // and a hero
    HeroUnit heroUnit = new DefaultHeroUnit(
        new MapPoint(1, 1),
        new MapSize(1, 1),
        new StubUnitSpriteSheet(),
        UnitType.SWORDSMAN,
        Arrays.asList(mockAbility),
        0
    );
    long delay = GameModel.DELAY;

    // when I call tick
    heroUnit.tick(delay, mockWorld);

    // then ability's tick should have been called
    verify(mockAbility, times(1)).usableTick(delay);
  }
}
