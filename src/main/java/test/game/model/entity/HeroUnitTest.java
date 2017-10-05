package test.game.model.entity;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import main.game.model.entity.HeroUnit;
import main.game.model.entity.UnitType;
import main.game.model.entity.exceptions.ItemNotInRangeException;
import main.game.model.entity.usables.Item;
import main.images.GameImageResource;
import main.images.UnitSpriteSheet;
import main.util.MapPoint;
import main.util.MapSize;
import org.junit.Test;
import test.game.model.world.WorldTestUtils;

public class HeroUnitTest {
  private HeroUnit heroUnit = new HeroUnit(
      new MapPoint(0, 0),
      new MapSize(1, 1),
      new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
      UnitType.MAGICIAN,
      Arrays.asList()
  );

  @Test
  public void addingAnItemToTheInventoryShouldWorkWhenItemInRange() {
    Item item = WorldTestUtils.createStubItem(heroUnit.getTopLeft().shiftedBy(0.001, 0.001));
    heroUnit.pickUp(item);
    assertTrue(heroUnit.getItemInventory().contains(item));
  }

  @Test(expected = ItemNotInRangeException.class)
  public void addingAnItemToTheInventoryShouldNotWorkWhenItemIsFarAway() {
    Item item = WorldTestUtils.createStubItem(heroUnit.getCentre().shiftedBy(100, 100));
    heroUnit.pickUp(item);
  }
}
