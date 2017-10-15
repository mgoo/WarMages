package test.game.model.entity;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import main.common.entity.Team;
import main.common.entity.usable.Ability;
import main.common.util.MapPoint;
import main.common.util.MapSize;
import main.game.model.Level;
import main.game.model.entity.unit.DefaultHeroUnit;
import main.game.model.entity.unit.DefaultUnit;
import main.game.model.entity.unit.UnitType;
import main.common.World;
import main.game.model.world.DefaultWorld;
import main.game.model.entity.unit.state.WalkingUnitState;
import main.game.model.world.pathfinder.DefaultPathFinder;
import org.junit.Ignore;
import org.junit.Test;
import test.game.model.world.WorldTestUtils;

/**
 * Test the changing of states works properly.
 * @author Andrew McGhie
 */
public class UnitStateTest {

  @Test
  public void testUnitState_unitShouldBeInWalkingStateWhenMoving() {
    DefaultUnit unit = new DefaultUnit(new MapPoint(0,0),
        new MapSize(100, 100),
        Team.PLAYER,
        new StubUnitSpriteSheet(),
        UnitType.ARCHER);
    final DefaultHeroUnit heroUnit = new DefaultHeroUnit(new MapPoint(50,50),
        new MapSize(100, 100),
        new StubUnitSpriteSheet(),
        UnitType.ARCHER,
        new ArrayList<>(),
        0);
    List<Level> levels = new ArrayList<>();
    levels.add(WorldTestUtils.createLevelWith(
        unit,
        heroUnit
    ));
    World world = new DefaultWorld(levels, heroUnit, new DefaultPathFinder()); //TODO mock world

    unit.setTargetPoint(new MapPoint(20, 2));
    unit.tick(50, world);
    unit.tick(50, world);
    unit.tick(50, world);

    assertTrue(unit._getUnitState() instanceof WalkingUnitState);
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
