package main.game.model.entity.usable;

import main.game.model.data.DataLoader;
import main.game.model.data.dataobject.AbilityData;
import main.game.model.data.dataobject.ImageData;
import main.game.model.entity.HeroUnit;
import main.game.model.entity.unit.attack.Attack;
import main.util.TickTimer;

/**
 * Superclass for all {@link Ability} implementations.
 * @author chongdyla
 */
public abstract class BaseAbility extends Attack implements Ability {

  private static final long serialVersionUID = 1L;

  public static final String TARGETS_UNITS = "units";
  public static final String TARGETS_GROUND = "ground";
  public static final int INFINITE_USES = -1;

  private final ImageData iconImage;

  protected final double coolDownSeconds;

  private int uses;
  private final TickTimer coolDownTimer;
  private final String description;
  protected HeroUnit owner;

  private boolean selected = false;

  /**
   * Will build a new ability.
   * But splits the ability to {@link AttackUnitAbility} and {@link AttackGroundAbility}
   */
  public static Ability buildAbility(
      AbilityData abilityData,
      DataLoader dataLoader,
      int uses
  ) {
    switch (abilityData.getTargets()) {
      case TARGETS_UNITS:
        return new AttackUnitAbility(abilityData, dataLoader, uses);
      case TARGETS_GROUND:
        return new AttackGroundAbility(abilityData, dataLoader, uses);
      default:
        throw new IllegalArgumentException("The targeting option was not found. Fix the data");
    }
  }

  protected BaseAbility(
      AbilityData abilityData,
      DataLoader dataLoader,
      int uses
  ) {
    super(abilityData.getAttackData(), dataLoader);
    this.description = abilityData.getDescription();
    this.iconImage = abilityData.getIcon();
    this.coolDownSeconds = abilityData.getCooldown();
    if (this.coolDownSeconds == 0) {
      this.coolDownTimer = new TickTimer(1);
    } else {
      this.coolDownTimer = TickTimer.withPeriodInSeconds(coolDownSeconds);
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
  public ImageData getIconImage() {
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
          "<b>Cooldown</b>: " + coolDownProgressSeconds + "/" + (int)this.coolDownSeconds + "s";
    }
    return desctiptionPopup;
  }

}
