package test.game.model.entity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.concurrent.atomic.AtomicInteger;
import main.common.images.GameImageResource;
import main.game.model.GameModel;
import main.game.model.entity.AttackingUnitState;
import main.game.model.entity.Direction;
import main.game.model.entity.Team;
import main.common.Unit;
import main.game.model.entity.UnitImagesComponent;
import main.game.model.entity.UnitType;
import main.game.model.world.World;
import main.images.DefaultUnitSpriteSheet;
import org.junit.Test;

public class AttackingUnitStateTest {

  @Test
  public void onlyOneAttackShouldOccurPerCycle() {
    // Given a target
    Unit target = mock(Unit.class);
    when(target.getTeam()).thenReturn(Team.ENEMY);
    // that counts attacks received
    AtomicInteger attackCount = new AtomicInteger(0);
    doAnswer(invocation -> attackCount.incrementAndGet())
        .when(target)
        .takeDamage(anyInt(), any());
    // and a swordsman
    Unit unit = mock(Unit.class);
    when(target.getTeam()).thenReturn(Team.PLAYER);
    when(unit.getUnitType()).thenReturn(UnitType.SWORDSMAN);
    when(unit.getSpriteSheet())
        .thenReturn(new DefaultUnitSpriteSheet(GameImageResource.ARCHER_SPRITE_SHEET));
    when(unit.getTarget()).thenReturn(target);
    when(unit.getCurrentDirection()).thenReturn(Direction.DOWN);
    // with some configuration parameters
    final int attackFrame = unit.getUnitType().getAttackSequence().getAttackFrame();
    final int ticksPerFrame = UnitImagesComponent.TICKS_PER_FRAME;
    // and the state to test
    AttackingUnitState state = new AttackingUnitState(unit);
    // and a stub world
    World world = mock(World.class);

    // when enough ticks happen (up to start of attack frame)
    for (int i = 0; i < attackFrame * ticksPerFrame - 1; i++) {
      state.tick(GameModel.DELAY, world);
    }

    // then no attacks should have occurred
    assertEquals(0, attackCount.get());

    // when the attack frame is eventually completed
    for (int i = 0; i < ticksPerFrame; i++) {
      state.tick(GameModel.DELAY, world);
    }

    // then exactly one attack should have occurred
    assertEquals(1, attackCount.get());
  }

}
