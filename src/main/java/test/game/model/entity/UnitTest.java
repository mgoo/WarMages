package test.game.model.entity;

//import static org.mockito.Matchers.any;
//import static org.mockito.Mockito.doAnswer;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;

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
import org.junit.Before;
import org.junit.Test;

public class UnitTest {

  /**
   * Tests related to a unit firing projectiles.
   */
  public static class ProjectileTest {

    private World world;
    private Unit enemyUnit;
    private AtomicInteger projectileCount;

    @Before
    public void setUp() throws Exception {
//      world = mock(World.class);
//      // pretend there are no objects in the way
//      when(world.isPassable(any())).thenReturn(true);

      enemyUnit = new Unit(
          new MapPoint(0.1, 0),
          new MapSize(1, 1),
          Team.ENEMY,
          new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
          UnitType.ARCHER
      );

      // Spy on addProjectile method
      projectileCount = new AtomicInteger();
//      doAnswer(invocationOnMock -> projectileCount.getAndIncrement())
//          .when(world)
//          .addProjectile(any());
    }

    @Test
    public void testArcherLaunchesProjectiles() {
      // Given all the objects in the setUp()
      // and an archer that targets the enemy
      Unit unit = createPlayerUnit(UnitType.ARCHER);
      unit.setTarget(enemyUnit, world);

      // when the game ticks several times
      for (int i = 0; i < 100; i++) {
        unit.tick(GameModel.DELAY, world);
      }

      // then some projectiles should have been created
      assert projectileCount.get() > 0;
    }

    @Test
    public void testArcherDoesntLaunchProjectilesWhenNoTargetSet() {
      // Given all the objects in the setUp()
      // and an archer that has no target
      Unit unit = createPlayerUnit(UnitType.ARCHER);
      unit.clearTarget();

      // when the game ticks several times
      for (int i = 0; i < 100; i++) {
        unit.tick(GameModel.DELAY, world);
      }

      // then no projectiles should have been fired
      assert projectileCount.get() == 0;
    }

    @Test
    public void testSwordsmanDoesntFireProjectiles() {
      // Given all the objects in the setUp()
      // and a swordsman
      Unit unit = createPlayerUnit(UnitType.SWORDSMAN);
      unit.setTarget(enemyUnit, world);

      // when the game ticks several times
      for (int i = 0; i < 100; i++) {
        unit.tick(GameModel.DELAY, world);
      }

      // then no projectiles should have been fired
      assert projectileCount.get() == 0;
    }

    private Unit createPlayerUnit(UnitType unitType) {
      return new Unit(
          new MapPoint(0, 0),
          new MapSize(1, 1),
          Team.PLAYER,
          new UnitSpriteSheet(GameImageResource.ARCHER_SPRITE_SHEET),
          unitType
      );
    }

  }
}
