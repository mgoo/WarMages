package main.game.model.entity.usable;

import main.game.model.entity.DefaultMapEntity;
import main.game.model.entity.Unit;
import main.game.model.world.World;
import main.images.GameImage;
import main.util.MapPoint;

/**
 * Default implementation of {@link Item}.
 * @author chongdyla
 */
public class DefaultItem extends DefaultMapEntity implements Item  {

  private static final long serialVersionUID = 1L;
  private final Ability ability;

  private boolean selected = false;

  /**
   * Constructor takes the coordinates of the item.
   * @param onMapImage What this image looks like when it's on the map.
   */
  public DefaultItem(MapPoint coord, Ability ability, GameImage onMapImage) {
    super(coord, onMapImage);
    this.ability = ability;
  }

  @Override
  public void setOwner(Unit unit) {
    this.ability.setOwner(unit);
  }

  @Override
  public void use(World world, Unit selectedUnit) {
    ability.use(world, selectedUnit);
  }

  @Override
  public void use(World world, MapPoint target) {
    ability.use(world, target);
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

  @Override
  public boolean canApplyTo(Unit unit, World world) {
    return this.ability.canApplyTo(unit, world);
  }

  @Override
  public boolean canApplyTo(MapPoint target, World world) {
    return this.ability.canApplyTo(target, world);
  }

  @Override
  public boolean isSelected() {
    return this.selected;
  }

  @Override
  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  @Override
  public int getCoolDownTicks() {
    return ability.getCoolDownTicks();
  }


}
