package main.game.model.entity;

import java.io.Serializable;
import main.game.model.GameModel;
import main.images.GameImage;

/**
 * An ability can be applied to (a) HeroUnit(s).
 */
public abstract class Ability implements Serializable, Usable {

  private static final long serialVersionUID = 1L;

  private static double secondsToTicks(double seconds) {
    double ticksPerSecond = 1000 / GameModel.DELAY;
    return seconds * ticksPerSecond;
  }

  /**
   * Number of ticks in a cool-down period.
   */
  private final double coolDownPeriodTicks;
  private final GameImage iconImage;
  private final String description;

  /**
   * Number of ticks left to end cool down.
   */
  private double coolDownTicksLeft;

  /**
   * Constructor takes a string description of the ability, and the icon that represent the
   * ability.
   */
  public Ability(String description, GameImage icon, double coolDownSeconds) {
    this.description = description;
    this.iconImage = icon;
    this.coolDownPeriodTicks = secondsToTicks(coolDownSeconds);
    this.coolDownTicksLeft = READY;
  }

  @Override
  public void tick(long timeSinceLastTick) {
    coolDownTicksLeft = Math.max(0, coolDownTicksLeft - 1);
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
    return 1 - (coolDownTicksLeft / coolDownPeriodTicks);
  }

  @Override
  public void _startCoolDown() {
    coolDownTicksLeft = coolDownPeriodTicks;
  }
}
