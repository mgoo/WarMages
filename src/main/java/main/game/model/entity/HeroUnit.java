package main.game.model.entity;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import main.game.model.entity.usables.Ability;
import main.game.model.entity.usables.Item;
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

  private List<Item> items = new ArrayList<>();
  private boolean healing;
  private int tickCount = 0;

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
   * Adds the given item to the HeroUnit's items. Requires the item is not null.
   */
  public void pickUp(Item item) {
    requireNonNull(item);

    if (getCentre().distanceTo(item.getCentre()) < PICK_UP_MAX_DISTANCE) {
      throw new IllegalArgumentException("Item is too far away");
    }

    items.add(item);
  }

  /**
   * Returns the HeroUnit's abilities.
   */
  public Collection<Ability> getAbilities() {
    return new ArrayList<>(abilities);
  }

  /**
   * Returns the HeroUnit's items.
   */
  public Collection<Item> getItems() {
    return new ArrayList<>(items);
  }

  @Override
  public void tick(long timeSinceLastTick, World world) {
    super.tick(timeSinceLastTick, world);
    abilities.forEach(ability -> ability.usableTick(timeSinceLastTick));
  }
}
