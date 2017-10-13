package main.common.entity;

import java.util.Collection;
import main.common.entity.usable.Ability;
import main.common.entity.usable.Item;

/**
 * HeroUnit extends {@link Unit}. This unit is the main unit to be controlled by the user. It has
 * abilities, and is able to pick up items and use the items.
 */
public interface HeroUnit extends Unit {

  void pickUp(Item item);

  Collection<Ability> getAbilities();

  Collection<Item> getItemInventory();

}
