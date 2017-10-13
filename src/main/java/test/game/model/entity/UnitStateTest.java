package test.game.model.entity;

import java.util.ArrayList;
import java.util.List;
import main.common.images.GameImageResource;
import main.common.util.MapPoint;
import main.common.util.MapSize;
import main.game.model.Level;
import main.game.model.entity.unit.state.DefaultHeroUnit;
import main.game.model.entity.unit.DefaultUnit;
import main.common.entity.Team;
import main.game.model.entity.unit.UnitType;
import main.common.entity.usable.Ability;
import main.common.World;
import main.game.model.world.DefaultWorld;
import main.game.model.world.pathfinder.DefaultPathFinder;
import main.images.DefaultUnitSpriteSheet;
import org.junit.Ignore;
import org.junit.Test;
import test.game.model.world.WorldTestUtils;

/**
 * Test the changing of states works properly.
 * @author Andrew McGhie
 */
public class UnitStateTest {

  @Ignore
  @Test
  public void testUnitState_unitShouldBeInWalkingStateWhenMoving() {
    DefaultUnit unit = new DefaultUnit(new MapPoint(0,0),
        new MapSize(100, 100),
        Team.PLAYER,
        new DefaultUnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER);
    final DefaultHeroUnit heroUnit = new DefaultHeroUnit(new MapPoint(50,50),
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
    World world = new DefaultWorld(levels, heroUnit, new DefaultPathFinder());
    unit.tick(50, world);
    unit.tick(50, world);
    unit.tick(50, world);
    // TODO assert unit state
  }

  @Ignore
  @Test
  public void testUnitState_unitShouldBeAttackingStateWhenAttacking() {
    // TODO
  }

  @Ignore
  @Test
  public void testUnitState_unitShouldBeAbleToGoToWalkingFromMoving() {
    // TODO
  }

  @Ignore
  @Test
  public void testUnitState_unitShouldGoIdleAfterAttacking() {
    // TODO
  }

}
