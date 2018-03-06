package main.game.model.entity.unit;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import main.exceptions.ItemNotInRangeException;
import main.game.model.data.DataLoader;
import main.game.model.data.dataObject.UnitData;
import main.game.model.entity.HeroUnit;
import main.game.model.entity.Team;
import main.game.model.entity.unit.state.TargetItem;
import main.game.model.entity.usable.Ability;
import main.game.model.entity.usable.Item;
import main.game.model.world.World;
import main.util.MapPoint;

/**
 * Default implementation of HeroUnit.
 * @author paladogabr
 */
public class DefaultHeroUnit extends DefaultUnit implements HeroUnit {

  private static final long serialVersionUID = 1L;

  private final List<Ability> abilities;

  private List<Ability> itemAbilities = new CopyOnWriteArrayList<>();

  /**
   * Constructor takes initial position of HeroUnit, size, sprite sheet, and unit type.
   */
  public DefaultHeroUnit(
      UnitData unitData,
      MapPoint position,
      Collection<Ability> abilities,
      DataLoader dataLoader,
      int level
  ) {
    super(unitData, position, Team.PLAYER, dataLoader, level);
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

    Ability itemAbility = item.getAbility();

    int indexOf = itemAbilities.indexOf(itemAbility);
    // If the hero already has the ability
    if (indexOf != -1) {
      itemAbilities.get(indexOf).addUse(itemAbility.getUses());
      return;
    }

    itemAbilities.add(itemAbility);
    itemAbility.setOwner(this);
  }

  public boolean isItemWithinRange(Item item) {
    return new TargetItem(this, item).hasArrived();
  }

  /**
   * Returns the HeroUnit's abilities.
   */
  @Override
  public List<Ability> getAbilities() {
    return Collections.unmodifiableList(abilities);
  }

  @Override
  public List<Ability> getItemAbilities() {
    return Collections.unmodifiableList(this.itemAbilities);
  }

  public void removeItemAbility(Ability itemAbility) {
    this.itemAbilities.remove(itemAbility);
  }

  @Override
  public void tick(long timeSinceLastTick, World world) {
    super.tick(timeSinceLastTick, world);

    abilities.forEach(ability -> ability.usableTick(timeSinceLastTick));
    this.getItemAbilities().forEach(itemAbility -> itemAbility.usableTick(timeSinceLastTick));
  }
}
