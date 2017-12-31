package main.game.model.entity.usable;

/**
 * An ability can be applied to any units on the players team.
 *
 * @author chongdyla
 */
public interface Ability extends Usable {

  double getEffectDurationSeconds();

}
