package main.game.model.entity.usable;

import static main.game.model.entity.usable.BaseEffect.INSTANT_EFFECT_DURATION;

import java.util.Collection;
import java.util.stream.Collectors;
import main.common.Effect;
import main.common.images.GameImage;
import main.game.model.entity.Team;
import main.game.model.entity.Unit;
import main.game.model.world.World;

/**
 * Heals a unit instantly.
 * @author chongdyla
 */
public class HealAbility extends Ability {

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
  public Effect _createEffectForUnit(Unit unit) {
    return new HealEffect(unit);
  }

  public int getHealAmount() {
    return healAmount;
  }

  @Override
  public Collection<Unit> _selectUnitsToApplyOn(World world, Collection<Unit> selectedUnits) {
    return world.getAllUnits()
        .stream()
        .filter(unit -> unit.getTeam() == Team.PLAYER)
        .collect(Collectors.toList());
  }

  private class HealEffect extends BaseEffect {

    private static final long serialVersionUID = 1L;

    private final Unit unit;

    HealEffect(Unit unit) {
      super(unit, INSTANT_EFFECT_DURATION);
      this.unit = unit;
    }

    @Override
    public void start() {
      super.start();
      unit.gainHealth(healAmount);
    }
  }

}
