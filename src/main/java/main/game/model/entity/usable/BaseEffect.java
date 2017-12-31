package main.game.model.entity.usable;

import main.game.model.entity.Unit;
import main.game.model.world.World;
import main.util.TickTimer;

/**
 * All {@link Effect}s should extend this.
 * @author chongdyla
 */
public abstract class BaseEffect implements Effect {

  private static final long serialVersionUID = 1L;

  public static final double INSTANT_EFFECT_DURATION = 0;

  private final Unit targetUnit;
  private final TickTimer expiryTimer;
  private boolean hasStarted;

  /**
   * Default constructor.
   *
   * @param durationSeconds Number of seconds before this expires. Set to {@link
   * BaseEffect#INSTANT_EFFECT_DURATION} for one-shot effects.
   */
  public BaseEffect(Unit targetUnit, World world, double durationSeconds) {
    if (durationSeconds < 0) {
      throw new IllegalArgumentException();
    }

    this.targetUnit = targetUnit;
    this.expiryTimer = TickTimer.withPeriodInSeconds(durationSeconds);
  }

  @Override
  public void start() {
    if (hasStarted) {
      throw new IllegalStateException();
    }

    hasStarted = true;
    expiryTimer.restart();
  }

  @Override
  public void tick(long timeSinceLastTick) {
    if (!isActive()) {
      throw new IllegalStateException();
    }

    expiryTimer.tick(timeSinceLastTick);
  }

  @Override
  public boolean isTargetUnit(Unit unit) {
    return unit == this.targetUnit;
  }

  @Override
  public boolean isExpired() {
    return hasStarted && expiryTimer.isFinished();
  }

  private boolean isActive() {
    return !isExpired() && hasStarted;
  }

  @Override
  public double alterDamageModifier(double currentDamageModifier) {
    if (!isActive()) {
      throw new IllegalStateException();
    }

    return currentDamageModifier;
  }
}
