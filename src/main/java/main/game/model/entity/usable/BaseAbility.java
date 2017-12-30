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

  protected final int coolDownSeconds;

  private final TickTimer coolDownTimer;
  protected final double effectDurationSeconds;

  private boolean selected = false;

  public BaseAbility(
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
    this.iconImage = icon;
    this.coolDownSeconds = (int)coolDownSeconds;
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

  @Override
  public boolean isSelected() {
    return this.selected;
  }

  @Override
  public void setSelected(boolean selected) {
    this.selected = selected;
  }

}
