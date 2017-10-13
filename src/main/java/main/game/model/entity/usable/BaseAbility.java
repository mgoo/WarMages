package main.game.model.entity.usable;

import main.common.entity.usable.Ability;
import main.common.images.GameImage;
import main.common.util.TickTimer;

/**
 * Superclass for all {@link Ability} implementations.
 * @author chongdyla
 */
public abstract class BaseAbility extends BaseUsable implements Ability {

  private static final long serialVersionUID = 1L;

  private final GameImage iconImage;
  private final String description;

  private final TickTimer coolDownTimer;
  private final double effectDurationSeconds;

  public BaseAbility(
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
  public void startCoolDown() {
    coolDownTimer.restart();
  }

  @Override
  public double getEffectDurationSeconds() {
    return effectDurationSeconds;
  }

  @Override
  public int getCoolDownTicks() {
    return coolDownTimer.getMaxTicks();
  }

}
