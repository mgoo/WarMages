package main.game.view;

import java.awt.image.BufferedImage;
import main.common.GameView;
import main.common.util.Config;
import main.common.util.MapPoint;
import main.common.util.MapSize;
import main.common.Renderable;

/**
 * The View for the background.
 * @author Andrew McGhie
 */
public class BackGroundView {

  private final Config config;
  private final GameView gameView;
  private final MapSize size;
  private final BufferedImage background;

  BackGroundView(Config config, GameView gameView, BufferedImage baseImage) {
    this.config = config;
    this.gameView = gameView;
    this.size = new MapSize(config.getContextScreenWidth() * 2,
        config.getContextScreenHeight() * 2);
    this.background = new BufferedImage(config.getContextScreenWidth() * 2,
        config.getContextScreenHeight() * 2,
        BufferedImage.TYPE_4BYTE_ABGR);
    background.getGraphics().drawImage(baseImage,
        0,
        0,
        config.getContextScreenWidth(),
        config.getContextScreenHeight(),
        null);
    background.getGraphics().drawImage(baseImage,
        config.getContextScreenWidth(),
        0,
        config.getContextScreenWidth(),
        config.getContextScreenHeight(),
        null);
    background.getGraphics().drawImage(baseImage,
        0,
        config.getContextScreenHeight(),
        config.getContextScreenWidth(),
        config.getContextScreenHeight(),
        null);
    background.getGraphics().drawImage(baseImage,
        config.getContextScreenWidth(),
        config.getContextScreenHeight(),
        config.getContextScreenWidth(),
        config.getContextScreenHeight(),
        null);
  }


  public MapPoint getImagePosition(long currentTime) {
    double x = gameView.getViewBox().x() < 0
        ? - gameView.getViewBox().x() % config.getContextScreenWidth()
        : config.getContextScreenWidth()
            - gameView.getViewBox().x() % config.getContextScreenWidth();
    double y = gameView.getViewBox().y() < 0
        ? - gameView.getViewBox().y() % config.getContextScreenHeight()
        : config.getContextScreenHeight()
            - gameView.getViewBox().y() % config.getContextScreenHeight();
    return new MapPoint(
        x - config.getContextScreenWidth(),
        y - config.getContextScreenHeight());
  }


  public MapSize getImageSize() {
    return this.size;
  }


  public BufferedImage getImage() {
    return this.background;
  }
}
