package main.game.model.entity;

import java.util.Collection;
import main.game.model.entity.usable.Ability;
import main.game.model.entity.usable.Item;

/**
 * HeroUnit extends {@link Unit}. This unit is the main unit to be controlled by the user. It has
 * abilities, and is able to pick up items and use the items.
 * @author paladogabr
 */
public interface HeroUnit extends Unit {

  void pickUp(Item item);

  boolean isItemWithinRange(Item item);

  Collection<Ability> getAbilities();

  Collection<Ability> getItemAbilities();

  void removeItemAbility(Ability itemAbility);
}
