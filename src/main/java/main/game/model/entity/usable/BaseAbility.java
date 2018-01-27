package main.game.model.entity.usable;

import main.game.model.entity.Unit;
import main.game.model.entity.unit.attack.Attack;
import main.game.model.entity.unit.attack.AttackType;
import main.images.GameImage;
import main.images.UnitSpriteSheet.Sequence;
import main.util.TickTimer;

/**
 * Superclass for all {@link Ability} implementations.
 * @author chongdyla
 */
public abstract class BaseAbility extends Attack implements Ability {

  private static final long serialVersionUID = 1L;

  private final GameImage iconImage;

  protected final int coolDownSeconds;

  private final TickTimer coolDownTimer;
  private final String description;
  protected Unit owner;

  private boolean selected = false;

  public BaseAbility(
      GameImage icon,
      double coolDownSeconds,
      String description,
      String scriptLocation,
      double range,
      int attackSpeed,
      double windupPortion,
      Sequence attackSequence,
      AttackType attackType,
      double amount,
      double duration
  ) {
    super(scriptLocation, range, attackSpeed, windupPortion,
        attackSequence, attackType, amount, duration);
    this.description = description;
    if (coolDownSeconds <= 0) {
      throw new IllegalArgumentException();
    }
    this.iconImage = icon;
    this.coolDownSeconds = (int)coolDownSeconds;
    this.coolDownTimer = TickTimer.withPeriodInSeconds(coolDownSeconds);
  }

  @Override
  public void setOwner(Unit unit) {
    this.owner = unit;
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

  /**
   * TODO move to javascript.
   */
  @Override
  public String getDescription() {
    int coolDownProgressSeconds = (int)(this.coolDownSeconds * this.getCoolDownProgress());
    String desctiptionPopup =  this.description + "<br>";
    if (this.getAmount() > 0) {
      desctiptionPopup += "<b>Amount</b>: " + Math.round(this.getAmount()) + "<br>";
    }
    if (this.getDuration() > 0) {
      desctiptionPopup += "<b>Duration</b>: " + Math.round(this.getDuration()) + "<br>";
    }
    desctiptionPopup += "<b>Range</b>: " + Math.round(this.getModifiedRange(this.owner)) + "<br>";
    if (this.coolDownSeconds > 0) {
      desctiptionPopup +=
          "<b>Cooldown</b>: " + coolDownProgressSeconds + "/" + this.coolDownSeconds + "s";
    }
    return desctiptionPopup;
  }

}
