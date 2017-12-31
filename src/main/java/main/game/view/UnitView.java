package main.game.view;

import java.awt.Color;
import java.awt.Graphics2D;
import main.game.model.GameModel;
import main.game.model.entity.Team;
import main.game.model.entity.Unit;
import main.util.Config;
import main.util.MapSize;

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
