package test.game.model.entity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.concurrent.atomic.AtomicInteger;
import main.common.GameModel;
import main.game.model.entity.unit.DefaultUnit;
import main.game.model.entity.unit.UnitAnimation;
import main.game.model.entity.unit.state.Attacking;
import main.game.model.entity.unit.state.Interacting;
import main.common.entity.Direction;
import main.common.entity.Team;
import main.common.util.MapPoint;
import main.common.util.MapSize;
import main.game.model.entity.unit.UnitType;
import main.common.World;
import main.game.model.entity.unit.state.TargetEnemyUnit;
import org.junit.Test;

/**
 * Some tests.
 * @author chongdyla
 */
public class InteractingTest {

  @Test
  public void onlyOneAttackShouldOccurPerCycle() {
    // Given a target
    DefaultUnit target = mock(DefaultUnit.class);
    when(target.getHealth()).thenReturn(100D);
    when(target.getSize()).thenReturn(new MapSize(1, 1));
    when(target.getTeam()).thenReturn(Team.ENEMY);
    MapPoint targetLocation = new MapPoint(0, 0);
    when(target.getCentre()).thenReturn(targetLocation);
    // that counts attacks received
    AtomicInteger attackCount = new AtomicInteger(0);
    doAnswer(invocation -> 1 == attackCount.incrementAndGet())
        .when(target)
        .takeDamage(anyDouble(), any(), any());
    // and a swordsman
    DefaultUnit unit = mock(DefaultUnit.class);
    when(unit.getSize()).thenReturn(new MapSize(1, 1));
    when(unit.getCentre()).thenReturn(targetLocation);
    when(unit.getRangeModifier()).thenReturn(1D);
    when(unit.getTeam()).thenReturn(Team.PLAYER);
    when(unit.getUnitType()).thenReturn(UnitType.SWORDSMAN);
    when(unit.getSpriteSheet()).thenReturn(new StubUnitSpriteSheet());
    when(unit.getCurrentDirection()).thenReturn(Direction.DOWN);
    // with some configuration parameters
    final int totalTicks = unit.getUnitType().getBaseAttack().getModifiedAttackSpeed(unit);
    final int attackTick = (int)(unit.getUnitType().getBaseAttack().getWindupPortion(unit)
        * totalTicks);
    // and the state to test
    Interacting state = new Attacking(unit,
        new TargetEnemyUnit(unit, target, unit.getUnitType().getBaseAttack()),
        unit.getUnitType().getBaseAttack());
    // and a stub world
    World world = mock(World.class);

    // when enough ticks happen (up to start of attack frame)
    for (int i = 0; i < attackTick; i++) {
      state.tick(GameModel.DELAY, world);
    }

    // then no attacks should have occurred
    assertEquals(0, attackCount.get());

    state.tick(GameModel.DELAY, world);

    // Now an attack should have happened
    assertEquals(1, attackCount.get());

    // when the attack frame is eventually completed
    for (int i = attackTick + 1; i <= totalTicks; i++) {
      state.tick(GameModel.DELAY, world);
    }

    // then no more attacks
    assertEquals(1, attackCount.get());
  }

}
