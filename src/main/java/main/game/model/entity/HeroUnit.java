package main.game.model.entity;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import main.game.model.entity.exceptions.ItemNotInRangeException;
import main.game.model.entity.usable.Ability;
import main.game.model.entity.usable.Item;
import main.game.model.world.World;
import main.images.UnitSpriteSheet;
import main.util.MapPoint;
import main.util.MapSize;

/**
 * HeroUnit extends {@link Unit}. This unit is the main unit to be controlled by the user. It has
 * abilities, and is able to pick up items and use the items.
 */
public class HeroUnit extends Unit {

  private static final long serialVersionUID = 1L;
  private static final double PICK_UP_MAX_DISTANCE = 0.5;

  private final List<Ability> abilities;

  private List<Item> itemInventory = new ArrayList<>();

  /**
   * Constructor takes initial position of HeroUnit, size, sprite sheet, and unit type.
   *
   * @param position of HeroUnit.
   * @param size of HeroUnit on Map.
   * @param sheet SpriteSheet of HeroUnit images.
   * @param type of HeroUnit.
   */
  public HeroUnit(
      MapPoint position,
      MapSize size,
      UnitSpriteSheet sheet,
      UnitType type,
      Collection<Ability> abilities
  ) {
    super(position, size, Team.PLAYER, sheet, type);
    this.abilities = new ArrayList<>(abilities);
  }

  /**
   * Adds the given item to the HeroUnit's itemInventory. Requires the item is not null.
   */
  public void pickUp(Item item) {
    requireNonNull(item);

    if (getCentre().distanceTo(item.getCentre()) > PICK_UP_MAX_DISTANCE) {
      throw new ItemNotInRangeException("Item is too far away");
    }

    itemInventory.add(item);
  }

  /**
   * Returns the HeroUnit's abilities.
   */
  public Collection<Ability> getAbilities() {
    return Collections.unmodifiableList(abilities);
  }

  /**
   * Returns the HeroUnit's itemInventory.
   */
  public Collection<Item> getItemInventory() {
    return Collections.unmodifiableList(itemInventory);
  }

  @Override
  public void tick(long timeSinceLastTick, World world) {
    super.tick(timeSinceLastTick, world);

    abilities.forEach(ability -> ability.usableTick(timeSinceLastTick));
    itemInventory.forEach(item -> item.usableTick(timeSinceLastTick));
  }
}
