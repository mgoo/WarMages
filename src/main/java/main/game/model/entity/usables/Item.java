package main.game.model.entity.usables;

import main.game.model.entity.MapEntity;
import main.game.model.entity.Unit;
import main.game.model.world.World;
import main.images.GameImage;
import main.util.MapPoint;

/**
 * Item extends {@link MapEntity}. An item is something that can be picked up and used by HeroUnit.
 * For now, all items have an {@link Ability} (through decoration).
 *
 * <p>
 * Functionality should be delegated to the {@link Ability}.
 * </p>
 */
public class Item extends MapEntity implements Usable {

  private static final long serialVersionUID = 1L;
  private final Ability ability;

  /**
   * Constructor takes the coordinates of the item.
   * @param onMapImage What this image looks like when it's on the map.
   */
  public Item(MapPoint coord, Ability ability, GameImage onMapImage) {
    super(coord);
    this.ability = ability;
    this.image = onMapImage;
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
