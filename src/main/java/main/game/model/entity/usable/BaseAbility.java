package main.game.model.entity.usable;

import main.game.model.entity.HeroUnit;
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

  public static final int INFINITE_USES = -1;

  private final GameImage iconImage;

  protected final int coolDownSeconds;

  private int uses;
  private final TickTimer coolDownTimer;
  private final String description;
  protected HeroUnit owner;

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
      double duration,
      int uses
  ) {
    super(scriptLocation, range, attackSpeed, windupPortion,
        attackSequence, attackType, amount, duration);
    this.description = description;
    if (coolDownSeconds <= 0) {
      throw new IllegalArgumentException();
    }
    this.iconImage = icon;
    this.coolDownSeconds = (int)coolDownSeconds;
    if (this.coolDownSeconds != 0) {
      this.coolDownTimer = TickTimer.withPeriodInSeconds(coolDownSeconds);
    } else {
      this.coolDownTimer = new TickTimer(1);
    }
    this.uses = uses;
  }

  @Override
  public void setOwner(HeroUnit unit) {
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

  @Override
  public void consume() {
    if (this.uses != INFINITE_USES) {
      this.uses = this.uses - 1;
      if (this.uses == 0) {
        this.owner.removeItemAbility(this);
      }
    }
  }

  @Override
  public void addUse(int number) {
    if (this.uses != INFINITE_USES) {
      this.uses = this.uses + number;
    }
  }

  @Override
  public int getUses() {
    return this.uses;
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
