package main.game.model.entity.usable;

import java.util.Collection;
import main.common.entity.usable.Ability;
import main.common.entity.usable.Effect;
import main.common.entity.usable.Item;
import main.common.entity.Unit;
import main.common.images.GameImage;
import main.common.util.MapPoint;
import main.game.model.entity.DefaultMapEntity;
import main.game.model.world.World;

/**
 * Default implementation of {@link Item}.
 * @author chongdyla
 */
public class DefaultItem extends DefaultMapEntity implements Item  {

  private static final long serialVersionUID = 1L;
  private final Ability ability;

  /**
   * Constructor takes the coordinates of the item.
   * @param onMapImage What this image looks like when it's on the map.
   */
  public DefaultItem(MapPoint coord, Ability ability, GameImage onMapImage) {
    super(coord, onMapImage);
    this.ability = ability;
  }

  @Override
  public boolean isReadyToBeUsed() {
    return ability.isReadyToBeUsed();
  }

  /**
   * Should be called by whoever has this {@link Item}.
   */
  @Override
  public void usableTick(long timeSinceLastTick) {
    ability.usableTick(timeSinceLastTick);
  }

  @Override
  public GameImage getIconImage() {
    return ability.getIconImage();
  }

  @Override
  public String getDescription() {
    return ability.getDescription();
  }

  @Override
  public double getCoolDownProgress() {
    return ability.getCoolDownProgress();
  }

  @Override
  public Collection<Unit> _selectUnitsToApplyOn(World world, Collection<Unit> selectedUnits) {
    return ability._selectUnitsToApplyOn(world, selectedUnits);
  }

  @Override
  public void _startCoolDown() {
    ability._startCoolDown();
  }

  @Override
  public Effect _createEffectForUnit(Unit unit) {
    return ability._createEffectForUnit(unit);
  }

  /**
   * Should be called by the {@link World} if this is not picked up.
   */
  @Override
  public void tick(long timeSinceLastTick, World world) {
    usableTick(timeSinceLastTick);
  }

  @Override
  public boolean isPassable() {
    return true;
  }

}
