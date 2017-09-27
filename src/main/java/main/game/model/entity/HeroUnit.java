package main.game.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import main.images.UnitSpriteSheet;
import main.util.MapPoint;
import main.util.MapSize;

/**
 * HeroUnit extends {@link Unit}. This unit is the main unit to be controlled by the user. It has
 * abilities, and is able to pick up items and use the items.
 */
public class HeroUnit extends Unit implements Serializable {

  private static final long serialVersionUID = 1L;

  private ArrayList<Ability> abilities;
  private ArrayList<Item> items;

  /**
   * Constructor takes initial position of HeroUnit, size, sprite sheet, and unit type.
   * @param position of HeroUnit.
   * @param size of HeroUnit on Map.
   * @param sheet SpriteSheet of HeroUnit images.
   * @param type of HeroUnit.
   */
  public HeroUnit(MapPoint position, MapSize size, UnitSpriteSheet sheet, UnitType type) {
    super(position, size, Team.PLAYER, sheet, type);
    abilities = new ArrayList<>();
    items = new ArrayList<>();
  }

  /**
   * Adds the given item to the HeroUnit's items. Requires the item is not null.
   */
  public void pickUp(Item item) {
    assert item != null;
    items.add(item);
    //todo if item has ability, include in abilities
  }

  /**
   * Activates the given item.
   */
  public void use(Item item) {
    assert item != null;
    item.applyTo(this);
  }

  /**
   * Returns the HeroUnit's abilities.
   */
  public ArrayList<Ability> getAbilities() {
    return new ArrayList<>(abilities);
  }

  /**
   * Returns the HeroUnit's items.
   */
  public ArrayList<Item> getItems() {
    return new ArrayList<>(items);
  }
}
