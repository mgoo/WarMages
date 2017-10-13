package test.game.model.entity;

import static main.common.images.GameImageResource.GOLDEN_HERO_SPRITE_SHEET;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static test.game.model.world.WorldTestUtils.createDefaultHeroUnit;

import java.util.Arrays;
import main.common.exceptions.ItemNotInRangeException;
import main.common.util.MapPoint;
import main.common.util.MapSize;
import main.game.model.DefaultGameModel;
import main.common.entity.HeroUnit;
import main.game.model.entity.unit.state.DefaultHeroUnit;
import main.game.model.entity.unit.UnitType;
import main.common.entity.usable.Ability;
import main.common.entity.usable.Item;
import main.game.model.world.World;
import main.images.DefaultUnitSpriteSheet;
import org.junit.Test;
import test.game.model.world.WorldTestUtils;

/**
 * Some tests.
 * @author chongdyla
 */
public class HeroUnitTest {

  private World mockWorld = mock(World.class);
  private HeroUnit heroUnit = createDefaultHeroUnit();

  @Test
  public void addingAnItemToTheInventoryShouldWorkWhenItemInRange() {
    Item item = WorldTestUtils.createStubItem(heroUnit.getTopLeft().translate(0.001, 0.001));
    heroUnit.pickUp(item);
    assertTrue(heroUnit.getItemInventory().contains(item));
  }

  @Test(expected = ItemNotInRangeException.class)
  public void addingAnItemToTheInventoryShouldNotWorkWhenItemIsFarAway() {
    Item item = WorldTestUtils.createStubItem(heroUnit.getCentre().translate(100, 100));
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
        new DefaultUnitSpriteSheet(GOLDEN_HERO_SPRITE_SHEET),
        UnitType.SWORDSMAN,
        Arrays.asList(mockAbility)
    );
    long delay = DefaultGameModel.DELAY;

    // when I call tick
    heroUnit.tick(delay, mockWorld);

    // then ability's tick should have been called
    verify(mockAbility, times(1)).usableTick(delay);
  }

  @Test
  public void heroUnitShouldTickItemsButOnlyWhenInTheInventory() {
    // Given a hero
    HeroUnit heroUnit = createDefaultHeroUnit();
    long delay = DefaultGameModel.DELAY;
    // and an item close to the hero
    Item mockItem = mock(Item.class);
    when(mockItem.getCentre()).thenReturn(heroUnit.getCentre());

    // when I call tick
    heroUnit.tick(delay, mockWorld);

    // then item's tick should not have been called
    verify(mockItem, times(0)).usableTick(delay);

    // when I add the item to the unit
    heroUnit.pickUp(mockItem);
    // and then call tick
    heroUnit.tick(delay, mockWorld);

    // then item's tick should have been called
    verify(mockItem, times(1)).usableTick(delay);
  }
}
