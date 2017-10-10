package main.game.view;

import main.common.util.Config;
import main.common.util.MapSize;
import main.game.model.entity.Entity;
import main.game.model.entity.Team;
import main.game.model.entity.Unit;
import main.images.ImageProvider;

/**
 * Created by mgoo on 10/10/17.
 */
public class UnitView extends EntityView {

  Unit unit;

  UnitView(
      Config config,
      Unit unit,
      ImageProvider imageProvider
  ) {
    super(config, unit, imageProvider);
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
}
