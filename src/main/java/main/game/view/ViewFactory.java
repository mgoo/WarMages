package main.game.view;

import java.awt.image.BufferedImage;
import main.game.model.entity.Entity;
import main.game.model.entity.Unit;
import main.images.ImageProvider;
import main.util.Config;

/**
 * Makes Views.
 *
 * @author Andrew McGhie
 */
class ViewFactory {

  static EntityView makeEntityView(Config config, Entity entity, ImageProvider imageProvider) {
    if (entity instanceof Unit) {
      return new UnitView(config, (Unit)entity);
    } else {
      return new EntityView(config, entity);
    }
  }

  static FogOfWarView makeFogOfWarView(Config config) {
    return new FogOfWarView(config);
  }

  static BackGroundView makeBackGroundView(Config config,
                                           GameView gameView,
                                           BufferedImage baseImage) {
    return new BackGroundView(config, gameView, baseImage);
  }

}
