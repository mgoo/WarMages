package main.game.model.entity;

import java.util.ArrayList;
import main.images.UnitSpriteSheet;
import main.util.MapPoint;
import main.util.MapSize;

/**
 * HeroUnit extends {@link Unit}. This unit is the main unit to be controlled by the user. It has
 * abilities, and is able to pick up items and use the items.
 */
public class HeroUnit extends Unit {

  private static final long serialVersionUID = 1L;

  private ArrayList<Ability> abilities;
  private ArrayList<Item> items;
  private boolean healing;
  private Ability ability;
  private int tickCount = 0;

  /**
   * Constructor takes initial position of HeroUnit, size, sprite sheet, and unit type.
   *
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
    if (item == null) {
      throw new IllegalArgumentException("Null Item");
    }
    item.applyTo(this);
  }

  /**
   * Sets the HeroUnit's ability to the given ability.
   *
   * @param ability to be applied to the HeroUnit.
   */
  public void setAbility(Ability ability) {
    if (ability == null) {
      throw new IllegalArgumentException("Null Ability");
    }
    tickCount = 0;
    this.ability = ability;
  }

  /**
   * Sets the type of attack the Unit will apply to it's targets.
   *
   * @param healing either true for healing or false for hurting.
   */
  public void setHealing(boolean healing) {
    this.healing = healing;
  }

  /**
   * Resets the HeroUnit's damage to the default/baseline amount.
   */
  public void resetDamage() {
    damageAmount = unitType.getBaselineDamage();
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

  @Override
  public void attack(Unit unit) {
    if (!unit.equals(target) || isDead || target == null) {
      return;
    }
    if (healing) {
      if (unit.team.equals(team)) {
        unit.gainHealth(damageAmount);
      }
    } else {
      super.attack(unit);
    }
  }

  @Override
  public void tick(long timeSinceLastTick) {
    //tick ability to check if expired.
    if (ability != null) {
      if (ability.tickTimedOut(++tickCount)) {
        ability.disableOn(this);
        ability = null;
      }
    }
    super.tick(timeSinceLastTick);
  }
}
