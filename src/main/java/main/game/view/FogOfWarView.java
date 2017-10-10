package main.game.view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Collection;
import main.common.util.Config;
import main.common.util.MapPoint;
import main.common.util.MapSize;
import main.renderer.Renderable;

/**
 * Represents the fog of war that covers anything that the player doesnt have a unit close enough.
 * @author Andrew McGhie
 */
public class FogOfWarView implements Renderable {

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
  void calculate(Collection<UnitView> unitViews, GameView gameView) {
    this.fowMask = new BufferedImage((int)this.size.width,
        (int)this.size.height,
        BufferedImage.TYPE_4BYTE_ABGR);
    Graphics2D g = ((Graphics2D) this.fowMask.getGraphics());
    g.setColor(this.baseColor);
    g.fillRect(0, 0, (int)this.size.width, (int)this.size.height);

    g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0F));
    unitViews.forEach(unitView -> {
      MapPoint position = unitView.getImagePosition(0);
      double los = unitView.getLos();
      g.fillOval(
          (int)(position.x + gameView.getViewBox().x() - los / 2),
          (int)(position.y + gameView.getViewBox().y() - los / 2),
          (int)los,
          (int)los);
    });
  }

  @Override
  public MapPoint getImagePosition(long currentTime) {
    return this.origin;
  }

  @Override
  public MapSize getImageSize() {
    return this.size;
  }

  @Override
  public BufferedImage getImage() {
    return this.fowMask;
  }

  @Override
  public MapPoint getEffectiveEntityPosition(long currentTime) {
    return null;
  }
}
