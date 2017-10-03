package test.game.model.entity;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.concurrent.atomic.AtomicInteger;
import main.game.model.GameModel;
import main.game.model.entity.Team;
import main.game.model.entity.Unit;
import main.game.model.entity.UnitType;
import main.game.model.world.World;
import main.images.GameImageResource;
import main.images.UnitSpriteSheet;
import main.util.MapPoint;
import main.util.MapSize;
import org.junit.Test;

public class UnitTest {

  private Unit enemyUnit = new Unit(
      new MapPoint(0.1, 0),
      new MapSize(1, 1),
      Team.ENEMY,
      new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
      UnitType.ARCHER
  );

  @Test
  public void testArcherLaunchesProjectiles() {
    // Given an archer
    Unit unit = new Unit(
        new MapPoint(0, 0),
        new MapSize(1, 1),
        Team.PLAYER,
        new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER
    );
    // and a fake world
    World world = mock(World.class);
    when(world.isPassable(any())).thenReturn(true);
    // and that the unit targets the enemy
    unit.setTarget(enemyUnit, world);
    // and a projectile counter
    AtomicInteger projectileCount = new AtomicInteger();
    doAnswer(invocationOnMock -> projectileCount.getAndIncrement())
        .when(world)
        .addProjectile(any());

    // when the game ticks several times
    for (int i = 0; i < 50; i++) {
      System.out.println("i = " + i);
      unit.tick(GameModel.DELAY, world);
    }

    // then some projectiles should have been created
    assert projectileCount.get() > 0;
  }

  // TODO fail on no target
  // TODO no launch when not archer

}
