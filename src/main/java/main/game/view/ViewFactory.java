package main.game.view;

import java.awt.image.BufferedImage;
import main.game.model.entity.Entity;
import main.util.Config;

/**
 * Makes Views.
 *
 * @author Andrew McGhie
 */
class ViewFactory {

  static ViewVisitor viewVisitor = new ViewVisitor();

  /**
   * Makes the view for an entity.
   */
  static Renderable makeEntityView(Config config, Entity entity) {
    return entity.accept(config, viewVisitor);
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
