package main.game.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import main.common.images.ImageProvider;
import main.common.util.Config;
import main.common.util.MapSize;
import main.game.model.entity.Entity;
import main.game.model.entity.Team;
import main.game.model.entity.Unit;

/**
 * Created by mgoo on 10/10/17.
 */
public class UnitView extends EntityView {

  private Unit unit;

  UnitView(
      Config config,
      Unit unit,
      ImageProvider imageProvider
  ) {
    super(config, unit, imageProvider);
    this.unit = unit;
  }

  @Override
  void update(long tickTime, boolean isSelected) {
    super.update(tickTime, isSelected);

    if (isSelected) {
      this.currentImage = this.addSelectionDecorations(this.currentImage);
    }
    // 0.999 cause doubles are anoying
    if (unit.getHealthPercent() < 0.999) {
      this.currentImage = this.addHealthBar(this.currentImage);
    }
  }

  private BufferedImage addSelectionDecorations(BufferedImage image) { // TODO fix name
    BufferedImage newImage = new BufferedImage(image.getWidth(),
        image.getHeight(),
        BufferedImage.TYPE_4BYTE_ABGR);
    Graphics2D g = (Graphics2D) newImage.getGraphics();
    RenderingHints rh = new RenderingHints(
        RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON
    );
    g.setRenderingHints(rh);
    g.drawOval(0, image.getHeight() - 50, image.getWidth(), 50); // TODO math properly
    g.drawImage(image, 0, 0, null);
    return newImage;
  }

  private BufferedImage addHealthBar(BufferedImage image) {
    BufferedImage newImage = new BufferedImage(image.getWidth(),
        image.getHeight(),
        BufferedImage.TYPE_4BYTE_ABGR);
    Graphics2D g = ((Graphics2D) newImage.getGraphics());
    g.drawImage(image, 0, 0, null);
    g.setColor(new Color(200,200,200, 155));
    g.fillRect(0,
        0,
        image.getWidth(),
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
    g.fillRect(0,
        0,
        (int)(image.getWidth() * unit.getHealthPercent()),
        config.getEntityViewTilePixelsY() / 20);
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
}
