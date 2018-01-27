package main.game.model.entity.unit;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import main.exceptions.ItemNotInRangeException;
import main.game.model.entity.HeroUnit;
import main.game.model.entity.Team;
import main.game.model.entity.unit.state.TargetItem;
import main.game.model.entity.usable.Ability;
import main.game.model.entity.usable.Item;
import main.game.model.world.World;
import main.images.UnitSpriteSheet;
import main.util.MapPoint;
import main.util.MapSize;

/**
 * Default implementation of HeroUnit.
 * @author paladogabr
 */
public class DefaultHeroUnit extends DefaultUnit implements HeroUnit {

  private static final long serialVersionUID = 1L;

  private final List<Ability> abilities;

  private List<Item> itemInventory = new CopyOnWriteArrayList<>();

  /**
   * Constructor takes initial position of HeroUnit, size, sprite sheet, and unit type.
   *
   * @param position of HeroUnit.
   * @param size of HeroUnit on Map.
   * @param sheet SpriteSheet of HeroUnit images.
   * @param type of HeroUnit.
   */
  public DefaultHeroUnit(
      MapPoint position,
      MapSize size,
      UnitSpriteSheet sheet,
      UnitType type,
      Collection<Ability> abilities,
      int level
  ) {
    super(position, size, Team.PLAYER, sheet, type, level);
    this.abilities = new CopyOnWriteArrayList<>(abilities);
    abilities.forEach(ability -> ability.setOwner(this));
  }

  /**
   * Adds the given item to the HeroUnit's itemInventory. Requires the item is not null.
   */
  @Override
  public void pickUp(Item item) {
    requireNonNull(item);

    if (!isItemWithinRange(item)) {
      throw new ItemNotInRangeException("Item is too far away");
    }

    if (itemInventory.contains(item)) {
      return;
    }

    itemInventory.add(item);
    item.getAbility().setOwner(this);
  }

  public boolean isItemWithinRange(Item item) {
    return new TargetItem(this, item).hasArrived();
  }

  /**
   * Returns the HeroUnit's abilities.
   */
  @Override
  public Collection<Ability> getAbilities() {
    return Collections.unmodifiableList(abilities);
  }

  @Override
  public Collection<Ability> getItemAbilities() {
    return this.itemInventory.stream()
        .map(Item::getAbility)
        .collect(Collectors.toList());
  }

  /**
   * Returns the HeroUnit's itemInventory.
   */
  @Override
  public Collection<Item> getItemInventory() {
    return Collections.unmodifiableList(itemInventory);
  }

  @Override
  public void tick(long timeSinceLastTick, World world) {
    super.tick(timeSinceLastTick, world);

    abilities.forEach(ability -> ability.usableTick(timeSinceLastTick));
    this.getItemAbilities().forEach(itemAbility -> itemAbility.usableTick(timeSinceLastTick));
  }
}
