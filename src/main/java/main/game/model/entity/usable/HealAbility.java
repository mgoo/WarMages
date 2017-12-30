package main.game.model.entity.usable;

import static main.game.model.entity.usable.BaseEffect.INSTANT_EFFECT_DURATION;

import java.util.Collection;
import main.common.entity.usable.Effect;
import main.common.images.GameImage;
import main.common.entity.Team;
import main.common.entity.Unit;
import main.common.World;
import main.common.util.MapPoint;
import main.game.model.entity.unit.state.TargetUnit;

/**
 * Heals a unit instantly.
 * @author chongdyla
 */
public class HealAbility extends BaseAbility {

  private static final long serialVersionUID = 1L;

  private final int healAmount;

  /**
   * Constructor takes a string description of the ability, and the icon that represent the ability.
   */
  public HealAbility(GameImage icon, double coolDownSeconds, int healAmount) {
    super("Instantly heals units", icon, coolDownSeconds, INSTANT_EFFECT_DURATION);

    if (healAmount <= 0) {
      throw new IllegalArgumentException();
    }

    this.healAmount = healAmount;
  }

  @Override
  public Effect createEffectForUnit(Unit unit, World world) {
    return new HealEffect(unit, world);
  }

  public int getHealAmount() {
    return healAmount;
  }

  @Override
  public Collection<Unit> selectUnitsToApplyOn(World world, Collection<Unit> selectedUnits) {
    return selectedUnits;
  }

  private class HealEffect extends BaseEffect {

    private static final long serialVersionUID = 1L;

    private final Unit unit;
    private final World world;

    HealEffect(Unit unit, World world) {
      super(unit, world, INSTANT_EFFECT_DURATION);
      this.unit = unit;
      this.world = world;
    }

    @Override
    public void start() {
      super.start();
      unit.gainHealth(healAmount, world);
    }
  }

  @Override
  public boolean canApplyTo(MapPoint target) {
    return false;
  }

  @Override
  public boolean canApplyTo(Unit unit) {
    return unit.getTeam() == Team.PLAYER;
  }
}
