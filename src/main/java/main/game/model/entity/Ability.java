package main.game.model.entity;

import java.io.Serializable;
import main.images.GameImage;

/**
 * An ability can be applied to (a) HeroUnit(s).
 */
public abstract class Ability implements Serializable, Usable {

  private static final long serialVersionUID = 1L;

  private final GameImage iconImage;
  private final double coolDownPercentPerTick;
  private final String description;

  private double coolDownProgress;

  /**
   * Constructor takes a string description of the ability, and the icon that represent the
   * ability.
   */
  public Ability(String description, GameImage icon, double coolDownPercentPerTick) {
    this.description = description;
    this.iconImage = icon;
    this.coolDownPercentPerTick = coolDownPercentPerTick;
    this.coolDownProgress = READY;
  }

  @Override
  public void tick(long timeSinceLastTick) {
    coolDownProgress = Math.max(0, coolDownProgress - coolDownPercentPerTick);
  }

  @Override
  public GameImage getIconImage() {
    return iconImage;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public double getCoolDownProgress() {
    return coolDownProgress;
  }

  @Override
  public void _startCoolDown() {
    coolDownProgress = COOL_DOWN_JUST_STARTED;
  }
}
