package main.common.entity;

import java.io.Serializable;
import java.util.Collection;
import main.common.World;
import main.common.entity.usable.Ability;
import main.common.entity.usable.Effect;
import main.common.entity.usable.Item;
import main.common.images.GameImage;

/**
 * An usable {@link Item} or {@link Ability} - these have some effect on the unit (e.g. instant
 * health increase or a damage increase for a certain amount of time).
 * @author chongdyla
 */
public interface Usable extends Serializable {

  /**
   * Creates an {@link Effect} for each {@link Unit} and applies {@link Effect} to whatever
   * units are chosen by this {@link Usable}.
   *
   * @throws IllegalStateException When this is not ready to be used yet (e.g. cool-down).
   */
  void use(World world, Collection<Unit> selectedUnits);

  /**
   * False if currently in a cool-down state.
   */
  boolean isReadyToBeUsed();

  /**
   * Should update any cool-down timers. This is not called 'tick' because there is already
   * a method called 'tick' in {@link Entity}.
   */
  void usableTick(long timeSinceLastTick);

  /**
   * Returns the GameImage of this Ability.
   *
   * @return GameImage of the Ability.
   */
  GameImage getIconImage();

  /**
   * Returns a string description of the Ability.
   *
   * @return String describing the Ability
   */
  String getDescription();

  /**
   * 0 if just used, 1 if ready to use.
   */
  double getCoolDownProgress();

  int getCoolDownTicks();

  boolean canApplyTo(Unit unit);

}
