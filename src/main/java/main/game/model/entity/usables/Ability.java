package main.game.model.entity.usables;

import java.io.Serializable;
import main.images.GameImage;
import main.util.TickTimer;

/**
 * An ability can be applied to (a) HeroUnit(s).
 */
public abstract class Ability implements Serializable, Usable {

  private static final long serialVersionUID = 1L;

  private final GameImage iconImage;
  private final String description;

  private final TickTimer coolDownTimer;
  private final double effectDurationSeconds;

  /**
   * Constructor takes a string description of the ability, and the icon that represent the
   * ability.
   */
  public Ability(
      String description,
      GameImage icon,
      double coolDownSeconds,
      double effectDurationSeconds
  ) {
    if (coolDownSeconds <= 0) {
      throw new IllegalArgumentException();
    }
    if (effectDurationSeconds < 0) {
      throw new IllegalArgumentException();
    }
    this.description = description;
    this.iconImage = icon;
    this.coolDownTimer = TickTimer.withPeriodInSeconds(coolDownSeconds);
    this.effectDurationSeconds = effectDurationSeconds;
  }

  @Override
  public boolean isReadyToBeUsed() {
    return coolDownTimer.isFinished();
  }

  @Override
  public void usableTick(long timeSinceLastTick) {
    coolDownTimer.tick(timeSinceLastTick);
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
    return coolDownTimer.getProgress();
  }

  @Override
  public void _startCoolDown() {
    coolDownTimer.restart();
  }

  public double getEffectDurationSeconds() {
    return effectDurationSeconds;
  }

  public int getCoolDownTicks() {
    return coolDownTimer.getMaxTicks();
  }

}
