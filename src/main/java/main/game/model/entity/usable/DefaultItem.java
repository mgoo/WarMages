package main.game.model.entity.usable;

import main.game.model.entity.DefaultMapEntity;
import main.game.model.entity.Unit;
import main.game.model.world.World;
import main.game.view.Renderable;
import main.game.view.ViewVisitor;
import main.images.GameImage;
import main.util.Config;
import main.util.MapPoint;

/**
 * Default implementation of {@link Item}.
 * @author chongdyla
 */
public class DefaultItem extends DefaultMapEntity implements Item  {

  private static final long serialVersionUID = 1L;
  private final Ability ability;

  private final String name;

  /**
   * Constructor takes the coordinates of the item.
   * @param onMapImage What this image looks like when it's on the map.
   */
  public DefaultItem(MapPoint coord, Ability ability, GameImage onMapImage, String name) {
    super(coord, onMapImage);
    this.ability = ability;
    this.name = name;
  }

  @Override
  public boolean isPassable() {
    return true;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public Ability getAbility() {
    return this.ability;
  }

  @Override
  public Renderable accept(
      Config config, ViewVisitor viewVisitor
  ) {
    return viewVisitor.makeItemView(config, this);
  }
}
