package main.game.model.entity.usable;

import java.io.Serializable;
import main.game.model.data.dataObject.ImageData;
import main.game.model.entity.Entity;
import main.game.model.entity.HeroUnit;
import main.game.model.entity.Unit;
import main.game.model.world.World;
import main.util.MapPoint;

/**
 * An ability can be applied to any units on the players team.
 *
 * @author chongdyla
 */
public interface Ability extends Serializable {

  void use(World world, Unit unit);

  void use(World world, MapPoint mapPoint);

  boolean canApplyTo(Unit unit, World world);

  boolean canApplyTo(MapPoint target, World world);

  /**
   * Sets the unit the owns this ability.
   */
  void setOwner(HeroUnit unit);

  void consume();

  void addUse(int number);

  int getUses();

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
  ImageData getIconImage();

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

  boolean isSelected();

  void setSelected(boolean selected);

  void startCoolDown();
}
