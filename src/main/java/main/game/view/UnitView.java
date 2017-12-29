package main.game.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import main.common.GameModel;
import main.common.World;
import main.common.images.ImageProvider;
import main.common.util.Config;
import main.common.util.MapSize;
import main.common.entity.Team;
import main.common.entity.Unit;

/**
 * View of a unit.
 * @author Andrew McGhie
 */
public class UnitView extends EntityView {

  private Unit unit;
  private boolean isSelected = false;

  UnitView(
      Config config,
      Unit unit
  ) {
    super(config, unit);
    this.unit = unit;
  }

  private BufferedImage addHealthBar(BufferedImage image) {
    BufferedImage newImage = new BufferedImage(image.getWidth(),
        image.getHeight(),
        BufferedImage.TYPE_4BYTE_ABGR);
    Graphics2D g = ((Graphics2D) newImage.getGraphics());

    return newImage;
  }

  MapSize getLos() {
    return new MapSize(
        this.unit.getLineOfSight() * this.config.getEntityViewTilePixelsX(),
        this.unit.getLineOfSight() * this.config.getEntityViewTilePixelsY()
    );
  }

  boolean revealsFogOfWar() {
    return this.unit.getTeam() == Team.PLAYER;
  }

  @Override
  void update(long tickTime, GameModel model) {
    super.update(tickTime, model);
    this.isSelected = model.getUnitSelection().contains(unit);
  }

  @Override
  public void drawDecorations(Graphics2D g, int x, int y, int width, int height) {
    if (this.isSelected) {
      g.drawOval(x, y, width, height);
    }

    // 0.999 cause doubles are anoying
    if (unit.getHealthPercent() < 0.999) {
      g.setColor(new Color(200,200,200, 155));
      g.fillRect(x,
          y,
          width,
          config.getEntityViewTilePixelsY() / 20);
      Color healthColor;
      if (unit.getHealthPercent() > 0.5) {
        healthColor = new Color(84,255, 106);
      } else if (unit.getHealthPercent() > 0.25) {
        healthColor = new Color(255, 194, 41);
      } else {
        healthColor = new Color(255, 0, 61);
      }
      g.setColor(healthColor);
      g.fillRect(x,
          y,
          (int)(width * unit.getHealthPercent()),
          config.getEntityViewTilePixelsY() / 20);
    }
  }
}
