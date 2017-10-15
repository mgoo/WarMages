package main.common.entity.usable;

import main.common.entity.Unit;
import main.common.entity.Usable;

/**
 * An ability can be applied to any units on the players team
 * @author chongdyla
 */
public interface Ability extends Usable {

  double getEffectDurationSeconds();

  int getCoolDownTicks();
}
