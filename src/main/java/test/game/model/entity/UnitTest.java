package test.game.model.entity;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import main.game.model.GameModel;
import main.game.model.Level;
import main.game.model.entity.HeroUnit;
import main.game.model.entity.Team;
import main.game.model.entity.Unit;
import main.game.model.entity.UnitType;
import main.game.model.entity.usable.Ability;
import main.game.model.entity.usable.Effect;
import main.game.model.world.World;
import main.images.GameImageResource;
import main.images.UnitSpriteSheet;
import main.util.MapPoint;
import main.util.MapSize;
import org.junit.Before;
import org.junit.Test;
import test.game.model.world.WorldTestUtils;

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
      world = mock(World.class);
      // pretend there are no objects in the way
      when(world.isPassable(any())).thenReturn(true);

      enemyUnit = new Unit(
          new MapPoint(0.1, 0),
          new MapSize(1, 1),
          Team.ENEMY,
          new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
          UnitType.ARCHER
      );

      // Spy on addProjectile method
      projectileCount = new AtomicInteger();
      doAnswer(invocationOnMock -> projectileCount.getAndIncrement())
          .when(world)
          .addProjectile(any());
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

    @Test
    public void testTeamsCanAttack() {
      assertTrue(Team.ENEMY.canAttack(Team.PLAYER));
      assertFalse(Team.PLAYER.canAttack(Team.PLAYER));
    }

    @Test
    public void testDamage() {
      Unit unit = createPlayerUnit(UnitType.SWORDSMAN);
      int prevHealth = unit.getHealth();
      unit.takeDamage(5);
      assertEquals(prevHealth - 5, unit.getHealth());
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

    private Unit createEnemyUnit(UnitType unitType) {
      return new Unit(
          new MapPoint(0, 0),
          new MapSize(1, 1),
          Team.ENEMY,
          new UnitSpriteSheet(GameImageResource.ARCHER_SPRITE_SHEET),
          unitType
      );
    }

  }

  @Test
  public void testTickEffects() {
    Unit unit = new Unit(new MapPoint(0,0),
        new MapSize(100, 100),
        Team.PLAYER,
        new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER);
    HeroUnit heroUnit = new HeroUnit(new MapPoint(50,50),
        new MapSize(100, 100),
        new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER,
        new ArrayList<Ability>());

    List<Level> levels = new ArrayList<>();
    levels.add(WorldTestUtils.createLevelWith(unit, heroUnit));
    World world = new World(levels, heroUnit);

    Ability ability1 = new Ability("", GameImageResource.TEST_IMAGE_1_1.getGameImage(), 1, 2) {
      @Override
      public Effect _createEffectForUnit(Unit unit) {
        return new Effect(unit, 1) {
          @Override
          public int alterDamageAmount(int currentDamageAmount) {
            return currentDamageAmount + 1;
          }
        };
      }
    };
    Ability ability2 = new Ability("", GameImageResource.TEST_IMAGE_1_1.getGameImage(), 1, 2) {
      @Override
      public Effect _createEffectForUnit(Unit unit) {
        return new Effect(unit, 2) {
          @Override
          public int alterDamageAmount(int currentDamageAmount) {
            return currentDamageAmount + 1;
          }
        };
      }
    };
    Ability ability3 = new Ability("", GameImageResource.TEST_IMAGE_1_1.getGameImage(), 1, 2) {
      @Override
      public Effect _createEffectForUnit(Unit unit) {
        return new Effect(unit, 1) {
          @Override
          public int alterDamageAmount(int currentDamageAmount) {
            return currentDamageAmount + 1;
          }
        };
      }
    };

    assertEquals(5, unit.getDamageAmount());
    ability1.useOnUnits(Arrays.asList(unit));
    assertEquals(6, unit.getDamageAmount());
    ability2.useOnUnits(Arrays.asList(unit));
    assertEquals(7, unit.getDamageAmount());
    ability3.useOnUnits(Arrays.asList(unit));
    assertEquals(8, unit.getDamageAmount());

    for (int i = 0; i < 25; i++) {
      unit.tick(50, world);
    }
    assertEquals(6, unit.getDamageAmount());
  }

  @Test
  public void testEntityAttack() {
    Unit playerUnit = new Unit(new MapPoint(0,0),
        new MapSize(0.5, 0.5),
        Team.PLAYER,
        new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER);
    Unit enemyUnit = new Unit(new MapPoint(1,1),
        new MapSize(0.5, 0.5),
        Team.ENEMY,
        new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.SPEARMAN);
    HeroUnit heroUnit = new HeroUnit(new MapPoint(50,50),
        new MapSize(0.5, 0.5),
        new UnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER,
        new ArrayList<Ability>());

    List<Level> levels = new ArrayList<>();
    levels.add(WorldTestUtils.createLevelWith(playerUnit, enemyUnit, heroUnit));
    World world = new World(levels, heroUnit);

    playerUnit.setTarget(enemyUnit, world);

    int previousHealth = enemyUnit.getHealth();

    for (int i = 0; i < 100; i++) {
      world.tick(50);
    }

    assertNotEquals(enemyUnit.getHealth(), previousHealth);
  }


}
