package main.game.view;

import main.common.images.ImageProvider;
import main.common.util.Config;
import main.game.model.entity.Entity;
import main.game.model.entity.Unit;

/**
 * Created by mgoo on 10/10/17.
 */
public class ViewFactory {

  static EntityView makeEntityView(Config config, Entity entity, ImageProvider imageProvider) {
    if (entity instanceof Unit) {
      return new UnitView(config, (Unit)entity, imageProvider);
    } else {
      return new EntityView(config, entity, imageProvider);
    }
  }

  static FogOfWarView makeFogOfWarView(Config config) {
    return new FogOfWarView(config);
  }

}
