package main.game.view;

import java.awt.image.BufferedImage;
import main.common.GameView;
import main.common.images.ImageProvider;
import main.common.util.Config;
import main.common.entity.Entity;
import main.common.entity.Unit;

/**
 * Makes Views.
 *
 * @author Andrew McGhie
 */
class ViewFactory {

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

  static BackGroundView makeBackGroundView(Config config,
                                           GameView gameView,
                                           BufferedImage baseImage) {
    return new BackGroundView(config, gameView, baseImage);
  }

}
