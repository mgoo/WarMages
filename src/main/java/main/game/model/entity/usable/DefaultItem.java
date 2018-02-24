package main.game.model.entity.usable;

import main.game.model.data.dataobject.ImageData;
import main.game.model.entity.ImageMapEntity;
import main.game.view.Renderable;
import main.game.view.ViewVisitor;
import main.util.Config;
import main.util.MapPoint;
import main.util.MapSize;

/**
 * Default implementation of {@link Item}.
 * @author chongdyla
 */
public class DefaultItem extends ImageMapEntity implements Item  {

  private static final long serialVersionUID = 1L;
  private final Ability ability;

  private final String name;

  /**
   * Constructor takes the coordinates of the item.
   * @param onMapImage What this image looks like when it's on the map.
   */
  public DefaultItem(MapPoint coord, Ability ability, ImageData onMapImage, String name) {
    super(coord, new MapSize(1, 1), onMapImage);
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
