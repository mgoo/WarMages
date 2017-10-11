package main.game.model.entity.usable;

import java.util.Collection;
<<<<<<< HEAD
import main.common.Usable;
import main.common.MapEntity;
import main.common.Unit;
=======
import main.common.Effect;
import main.game.model.entity.MapEntity;
import main.game.model.entity.Unit;
>>>>>>> 2d22d26dd18895b3a2c08759622f7a071a869b31
import main.game.model.world.World;
import main.common.images.GameImage;
import main.common.util.MapPoint;

/**
 * Item extends {@link MapEntity}. An item is something that can be picked up and used by HeroUnit.
 * For now, all items have an {@link Ability} (through decoration).
 *
 * <p>
 * Functionality should be delegated to the {@link Ability}.
 * </p>
 */
public class Item extends MapEntity implements BaseUsable {

  private static final long serialVersionUID = 1L;
  private final Ability ability;

  /**
   * Constructor takes the coordinates of the item.
   * @param onMapImage What this image looks like when it's on the map.
   */
  public Item(MapPoint coord, Ability ability, GameImage onMapImage) {
    super(coord);
    this.ability = ability;
    this.setImage(onMapImage);
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
}
