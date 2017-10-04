package main.game.model.entity;

import java.io.Serializable;
import main.images.GameImage;

/**
 * An ability can be applied to (a) Unit(s).
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
   * Applies this Ability to the given unit.
   *
   * @param unit to apply the Ability to.
   */
  public abstract void apply(Unit unit);

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
