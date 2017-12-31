package main.game.view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Collection;
import main.util.Config;
import main.util.MapPoint;
import main.util.MapSize;

/**
 * Represents the fog of war that covers anything that the player doesnt have a unit close enough.
 * @author Andrew McGhie
 */
public class FogOfWarView {

  private final Color baseColor;
  private final MapPoint origin = new MapPoint(0, 0);
  private final MapSize size;

  private BufferedImage fowMask;

  FogOfWarView(Config config) {
    this.size = new MapSize(config.getContextScreenWidth(), config.getContextScreenHeight());
    this.baseColor = config.getBaseFogOfWarColor();
  }

  /**
   * Recalculates what the fog of war should cover and what it should display.
   */
  void calculate(Collection<UnitView> unitViews, GameView gameView, long currentTime) {
    this.fowMask = new BufferedImage((int)this.size.width,
        (int)this.size.height,
        BufferedImage.TYPE_4BYTE_ABGR);
    Graphics2D g = ((Graphics2D) this.fowMask.getGraphics());
    g.setColor(this.baseColor);
    g.fillRect(0, 0, (int)this.size.width, (int)this.size.height);

    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 0F));
    g.setColor(new Color(255,255,255, 0));
    RenderingHints rh = new RenderingHints(
        RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON
    );
    g.setRenderingHints(rh);
    unitViews.forEach(unitView -> {
      MapPoint position = unitView.getImagePosition(currentTime);
      MapSize imageSize = unitView.getImageSize();
      MapSize los = unitView.getLos();
      g.fillOval(
          (int)(position.x + imageSize.width / 2 - gameView.getViewBox().x() - los.width / 2),
          (int)(position.y + imageSize.height / 2 - gameView.getViewBox().y() - los.height / 2),
          (int)los.width,
          (int)los.height);
    });
  }

  public MapPoint getImagePosition(long currentTime) {
    return this.origin;
  }


  public MapSize getImageSize() {
    return this.size;
  }


  public BufferedImage getImage() {
    return this.fowMask;
  }
}
