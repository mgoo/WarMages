package main.game.model.entity;

import java.io.Serializable;
import main.images.GameImage;

/**
 * An ability can be applied to (a) HeroUnit(s).
 */
public abstract class Ability implements Serializable {

  private static final long serialVersionUID = 1L;

  protected GameImage iconImage;
  protected String description;

  /**
   * Constructor takes a string description of the ability, and the icon that represent the
   * ability.
   */
  public Ability(String description, GameImage icon) {
    this.description = description;
    iconImage = icon;
  }

  /**
   * Applies this Ability to the given HeroUnit.
   *
   * @param heroUnit to apply the Ability to.
   */
  public abstract void apply(HeroUnit heroUnit);

  /**
   * Disables the ability on the given unit.
   *
   * @param heroUnit ability is to be disabled on.
   */
  public abstract void disableOn(HeroUnit heroUnit);

  /**
   * Returns a boolean depending on whether the Ability has timed out/expired or not.
   *
   * @param tickCount current tick count.
   * @return boolean if time out has been reached.
   */
  public abstract boolean tickTimedOut(int tickCount);

  /**
   * Returns the GameImage of this Ability.
   *
   * @return GameImage of the Ability.
   */
  public GameImage getIconImage() {
    return iconImage;
  }

  /**
   * Returns a string description of the Ability.
   *
   * @return String describing the Ability
   */
  public String getInfo() {
    return description;
  }
}
