package main.game.view;

import java.awt.Color;
import main.game.model.entity.DefaultMapEntity;
import main.game.model.entity.Entity;
import main.game.model.entity.Team;
import main.game.model.entity.Unit;
import main.game.model.entity.usable.Item;
import main.images.ImageProvider;
import main.util.Config;

public class ViewVisitor {

  public EntityView makeDefaultView(Config config, Entity entity) {
    return new EntityView(config, entity);
  }

  /**
   * Makes a view of a unit.
   */
  public UnitView makeUnitView(Config config, Unit unit) {
    UnitView unitView = new UnitView(config, unit);
    if (unit.getTeam() == Team.PLAYER) {
      // Show the healing on your units.
      unit.getHealedEvent().registerListener(
          amt -> unitView.addHealthChangeIndicator(
              new HealthChangeIndicator(unitView, amt, new Color(0, 200, 0))
          )
      );
    } else {
      // Show the damage on enemy units.
      unit.getDamagedEvent().registerListener(
          amt -> unitView.addHealthChangeIndicator(
              new HealthChangeIndicator(unitView, amt, new Color(200, 0, 0))
          )
      );
    }
    return unitView;
  }

  public ItemView makeItemView(Config config, Item item) {
    return new ItemView(config, item);
  }
}
