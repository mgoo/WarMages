package main.common.entity.usable;

import main.common.entity.Usable;

/**
 * An ability can be applied to (a) HeroUnit(s).
 * @author chongdyla
 */
public interface Ability extends Usable {

  double getEffectDurationSeconds();

  int getCoolDownTicks();
}
