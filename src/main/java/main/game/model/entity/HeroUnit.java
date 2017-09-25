package main.game.model.entity;

import java.util.ArrayList;
import java.util.Collection;
import main.images.UnitSpriteSheet;
import main.util.MapPoint;
import main.util.MapSize;

/**
 * HeroUnit extends {@link Unit}. This unit is the main unit to be controlled by the user. It has
 * abilities, and is able to pick up items and use the items.
 */
public class HeroUnit extends Unit {
  private ArrayList<Ability> abilities;
  private ArrayList<Item> items;

  public HeroUnit(MapPoint position, MapSize size, Team team, UnitSpriteSheet sheet, UnitType type) {
    super(position, size, team, sheet, type);
    abilities = new ArrayList<>();
    items = new ArrayList<>();
  }

  /**
   * Adds the given item to the HeroUnit's items.
   * Requires the item is not null.
   * @param item
   */
  public void pickUp(Item item) {
    assert item!=null;
    items.add(item);
    //todo if item has ability, include in abilities
  }

  /**
   * Activates the given item.
   * @param item
   */
  public void use(Item item) {
    throw new Error("NYI");
  }

  /**
   * Returns the HeroUnit's abilities.
   * @return
   */
  public ArrayList<Ability> getAbilities() {
    return new ArrayList<>(abilities);
  }

  /**
   * Returns the HeroUnit's items.
   * @return
   */
  public ArrayList<Item> getItems() {
    return new ArrayList<>(items);
  }
}
