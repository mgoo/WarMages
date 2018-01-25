package main.game.model.entity.unit.attack;

import java.util.Collection;
import java.util.stream.Collectors;
import main.game.model.entity.Unit;
import main.game.model.entity.unit.state.Targetable;
import main.game.model.world.World;
import main.images.UnitSpriteSheet;
import main.images.UnitSpriteSheet.Sequence;

public class HealAttack extends FixedAttack {

  private final double amount;

  public HealAttack(double range,
                    int attackSpeed,
                    double windupPortion,
                    Sequence sequence,
                    double amount) {
    super(
        CanEffect.ALLIES,
        "resources/scripts/healSpell.js",
        range,
        attackSpeed,
        windupPortion,
        sequence,
        AttackType.HEAL);
    this.amount = amount;
  }

  /**
   * Expose the amount to javascript.
   */
  public double getAmount() {
    return amount;
  }

  @Override
  public Collection<Unit> getEffectedUnits(
      Unit owner, World world, Targetable target
  ) {
    return super.getEffectedUnits(owner, world, target)
        .stream()
        .filter(u -> u.getHealthPercent() < 0.999)
        .collect(Collectors.toSet());
  }
}
