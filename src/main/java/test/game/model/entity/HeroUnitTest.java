package test.game.model.entity;

import static main.common.images.GameImageResource.GOLDEN_HERO_SPRITE_SHEET;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static test.game.model.world.WorldTestUtils.createHeroUnit;

import java.util.Arrays;
import main.common.util.MapPoint;
import main.common.util.MapSize;
import main.game.model.GameModel;
import main.common.HeroUnit;
import main.game.model.entity.UnitType;
import main.game.model.entity.exceptions.ItemNotInRangeException;
import main.game.model.entity.usable.Ability;
import main.game.model.entity.usable.Item;
import main.game.model.world.World;
import main.images.DefaultUnitSpriteSheet;
import org.junit.Test;
import test.game.model.world.WorldTestUtils;

public class HeroUnitTest {

  private World mockWorld = mock(World.class);
  private HeroUnit heroUnit = createHeroUnit();

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
    HeroUnit heroUnit = new HeroUnit(
        new MapPoint(1, 1),
        new MapSize(1, 1),
        new DefaultUnitSpriteSheet(GOLDEN_HERO_SPRITE_SHEET),
        UnitType.SWORDSMAN,
        Arrays.asList(mockAbility)
    );
    long delay = GameModel.DELAY;

    // when I call tick
    heroUnit.tick(delay, mockWorld);

    // then ability's tick should have been called
    verify(mockAbility, times(1)).usableTick(delay);
  }

  @Test
  public void heroUnitShouldTickItemsButOnlyWhenInTheInventory() {
    // Given a hero
    HeroUnit heroUnit = createHeroUnit();
    long delay = GameModel.DELAY;
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
