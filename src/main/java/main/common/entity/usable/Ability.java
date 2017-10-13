package main.common.entity.usable;

import main.game.model.entity.usable.BaseUsable;

/**
 * An ability can be applied to (a) HeroUnit(s).
 * @author chongdyla
 */
public interface Ability extends BaseUsable {

  double getEffectDurationSeconds();

  int getCoolDownTicks();
}
