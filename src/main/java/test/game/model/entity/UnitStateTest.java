package test.game.model.entity;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static test.game.model.entity.DefaultUnitTest.getWorld;

import java.util.ArrayList;
import java.util.Arrays;
import main.common.entity.Team;
import main.common.entity.Unit;
import main.common.entity.usable.Ability;
import main.common.util.MapPoint;
import main.common.util.MapSize;
import main.game.model.entity.unit.DefaultHeroUnit;
import main.game.model.entity.unit.DefaultUnit;
import main.game.model.entity.unit.UnitType;
import main.common.World;
import main.game.model.entity.unit.state.WalkingUnitState;
import org.junit.Ignore;
import org.junit.Test;

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
        new ArrayList<Ability>(),
        0);

    ArrayList<Unit> units = new ArrayList<>();
    units.add(unit);
    World world = getWorld(units, heroUnit);
    when(world.findPath(any(), any()))
        .thenAnswer(invocation -> Arrays.asList(invocation.getArguments()));

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
