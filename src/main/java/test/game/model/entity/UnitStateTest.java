package test.game.model.entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import main.common.images.GameImageResource;
import main.common.util.MapPoint;
import main.common.util.MapSize;
import main.game.model.Level;
import main.game.model.entity.HeroUnit;
import main.game.model.entity.Team;
import main.game.model.entity.Unit;
import main.game.model.entity.UnitType;
import main.game.model.entity.usable.Ability;
import main.game.model.world.World;
import main.game.model.world.pathfinder.DefaultPathFinder;
import main.images.DefaultImageProvider;
import main.images.DefaultUnitSpriteSheet;
import org.junit.Before;
import org.junit.Test;
import test.game.model.world.WorldTestUtils;

/**
 * TODO atm units dont move so its a bit hard to test
 * @author Andrew McGhie
 */
public class UnitStateTest {

  @Test
  public void testUnitState() {
    Unit unit = new Unit(new MapPoint(0,0),
        new MapSize(100, 100),
        Team.PLAYER,
        new DefaultUnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER);
    HeroUnit heroUnit = new HeroUnit(new MapPoint(50,50),
        new MapSize(100, 100),
        new DefaultUnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER,
        new ArrayList<Ability>());
    List<MapPoint> path = new ArrayList<>();
    path.add(new MapPoint(1,1));
    path.add(new MapPoint(2,2));

    unit.setPath(path);

    List<Level> levels = new ArrayList<>();
    levels.add(WorldTestUtils.createLevelWith(
        unit,
        heroUnit
    ));
    World world = new World(levels, heroUnit, new DefaultPathFinder());
    unit.tick(50, world);
    unit.tick(50, world);
    unit.tick(50, world);
    BufferedImage img;
    try {
      img = unit.getImage().load(new DefaultImageProvider());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
