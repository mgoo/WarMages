package test.game.model.entity;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static test.game.model.world.WorldTestUtils.createDefaultUnit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import main.common.entity.HeroUnit;
import main.common.entity.Team;
import main.common.entity.Unit;
import main.common.entity.usable.Ability;
import main.common.images.GameImageResource;
import main.common.util.MapPoint;
import main.common.util.MapSize;
import main.game.model.GameModel;
import main.game.model.Level;
import main.game.model.entity.Projectile;
import main.game.model.entity.unit.DefaultHeroUnit;
import main.game.model.entity.unit.DefaultUnit;
import main.game.model.entity.unit.UnitType;
import main.game.model.entity.usable.DamageBuffAbility;
import main.game.model.world.World;
import main.game.model.world.pathfinder.DefaultPathFinder;
import main.images.DefaultUnitSpriteSheet;
import org.junit.Before;
import org.junit.Test;
import test.game.model.world.WorldTestUtils;

/**
 * Tests for DefaultUnits.
 * @author paladogabr
 * @author chongdyla (Secondary Author)
 */
public class DefaultUnitTest {

  //note that CreateDefaultUnit creates a Spearman who travels with 0.1 travel speed

  private DefaultUnit getUnit() {
    return createDefaultUnit(new MapPoint(1, 1));
  }

  private List<MapPoint> getPathDown() {
    return Arrays.asList(
        new MapPoint(1, 2),
        new MapPoint(1, 3),
        new MapPoint(1, 4),
        new MapPoint(1, 5),
        new MapPoint(1, 6),
        new MapPoint(1, 7),
        new MapPoint(1, 8),
        new MapPoint(1, 9)
    );
  }

  private List<MapPoint> getPathAcross() {
    return Arrays.asList(
        new MapPoint(2, 1),
        new MapPoint(3, 1),
        new MapPoint(4, 1),
        new MapPoint(5, 1),
        new MapPoint(6, 1),
        new MapPoint(7, 1),
        new MapPoint(8, 1),
        new MapPoint(9, 1
        )
    );
  }

  private List<MapPoint> getPathDiagonal() {
    return Arrays.asList(
        new MapPoint(2, 2),
        new MapPoint(3, 3),
        new MapPoint(4, 4),
        new MapPoint(5, 5),
        new MapPoint(6, 6),
        new MapPoint(7, 7),
        new MapPoint(8, 8),
        new MapPoint(9, 9)
    );
  }

  @Test
  public void testMovingEntity_oneSpaceHorizontally() {
    Unit unit = getUnit();
    World world = mock(World.class);

    List<MapPoint> path = getPathAcross();
    MapPoint targetPoint = path.get(path.size() - 1);
    when(world.findPath(any(), any())).thenReturn(path);

    unit.setTargetPoint(targetPoint);
    unit.translatePosition(-unit.getSize().width / 2, -unit.getSize().height / 2);

    // Allow unit to go to from idle to walking state
    unit.tick(GameModel.DELAY, world);

    // speed = 0.1, delay = 50
    for (int i = 0; i < 10; i++) {
      MapPoint mapPoint = new MapPoint(1D + Math.min(unit.getType().getMovingSpeed() * i, 1), 1);
      assertEquals(mapPoint.x, unit.getCentre().x, 0.001);
      assertEquals(mapPoint.y, unit.getCentre().y, 0.001);
      unit.tick(GameModel.DELAY, world);
    }
  }

  @Test
  public void testMovingEntity_twoSpacesHorizontally() {
    Unit unit = getUnit();
    World world = mock(World.class);

    List<MapPoint> path = getPathAcross();
    MapPoint targetPoint = path.get(path.size() - 1);
    when(world.findPath(any(), any())).thenReturn(path);

    unit.setTargetPoint(targetPoint);
    unit.translatePosition(-unit.getSize().width / 2, -unit.getSize().height / 2);

    // Allow unit to go to from idle to walking state
    unit.tick(GameModel.DELAY, world);

    for (int i = 0; i < 20; i++) {
      MapPoint mapPoint = new MapPoint(1D + unit.getType().getMovingSpeed() * i, 1);
      assertEquals(mapPoint.x, unit.getCentre().x, 0.001);
      assertEquals(mapPoint.y, unit.getCentre().y, 0.001);
      unit.tick(GameModel.DELAY, world);
    }
  }

  @Test
  public void testMovingEntity_oneSpaceVertically() {
    Unit unit = getUnit();
    World world = mock(World.class);

    List<MapPoint> path = getPathDown();
    MapPoint targetPoint = path.get(path.size() - 1);
    when(world.findPath(any(), any())).thenReturn(path);

    unit.setTargetPoint(targetPoint);
    unit.translatePosition(-unit.getSize().width / 2, -unit.getSize().height / 2);

    // Allow unit to go to from idle to walking state
    unit.tick(GameModel.DELAY, world);

    for (int i = 0; i < 10; i++) {
      MapPoint mapPoint = new MapPoint(1, 1D + unit.getType().getMovingSpeed() * i);
      assertEquals(mapPoint.x, unit.getCentre().x, 0.001);
      assertEquals(mapPoint.y, unit.getCentre().y, 0.001);
      unit.tick(GameModel.DELAY, world);
    }
  }

  @Test
  public void testMovingEntity_twoSpacesVertically() {
    Unit unit = getUnit();
    World world = mock(World.class);

    List<MapPoint> path = getPathDown();
    MapPoint targetPoint = path.get(path.size() - 1);
    when(world.findPath(any(), any())).thenReturn(path);

    unit.setTargetPoint(targetPoint);
    unit.translatePosition(-unit.getSize().width / 2, -unit.getSize().height / 2);

    // Allow unit to go to from idle to walking state
    unit.tick(GameModel.DELAY, world);

    for (int i = 0; i < 20; i++) {
      MapPoint mapPoint = new MapPoint(1, 1D + unit.getType().getMovingSpeed() * i);
      assertEquals(mapPoint.x, unit.getCentre().x, 0.001);
      assertEquals(mapPoint.y, unit.getCentre().y, 0.001);
      unit.tick(GameModel.DELAY, world);
    }
  }

  @Test
  public void testMovingEntity_oneSpaceDiagonally() {
    Unit unit = getUnit();
    World world = mock(World.class);

    List<MapPoint> path = getPathDiagonal();
    MapPoint targetPoint = path.get(path.size() - 1);
    when(world.findPath(any(), any())).thenReturn(path);

    unit.setTargetPoint(targetPoint);
    unit.translatePosition(-unit.getSize().width / 2, -unit.getSize().height / 2);

    // Allow unit to go to from idle to walking state
    unit.tick(GameModel.DELAY, world);

    // with the given speed of 0.01, with each tick (time since last being 200)
    // the movable entity should move 100*0.01 = 1 map distance which is approx 1 diagonal
    for (int i = 0; i < 10; i++) {
      assertEquals(1D + 0.1 / Math.sqrt(2) * i, unit.getCentre().x, 0.001);
      assertEquals(1D + 0.1 / Math.sqrt(2) * i, unit.getCentre().y, 0.001);
      unit.tick(GameModel.DELAY, world);
    }
  }

  @Test
  public void testTickEffects() {
    Unit unit = new DefaultUnit(new MapPoint(0,0),
        new MapSize(100, 100),
        Team.PLAYER,
        new DefaultUnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER
    );

    World world = mock(World.class);
    when(world.getAllUnits()).thenReturn(Arrays.asList(unit));

    final Ability ability1 = new ApplyToAllUnitsDamageBuffAbility(1, 2, 3);
    final Ability ability2 = new ApplyToAllUnitsDamageBuffAbility(1, 2, 3);
    final Ability ability3 = new ApplyToAllUnitsDamageBuffAbility(1, 2, 3);

    int baseDamageAmount = unit.getDamageAmount();
    assertEquals(baseDamageAmount, unit.getDamageAmount());
    ability1.use(world, Collections.emptyList());
    assertEquals(baseDamageAmount + 1, unit.getDamageAmount());
    ability2.use(world, Collections.emptyList());
    assertEquals(baseDamageAmount + 2, unit.getDamageAmount());
    ability3.use(world, Collections.emptyList());
    assertEquals(baseDamageAmount + 3, unit.getDamageAmount());

    for (int i = 0; i < 200; i++) {
      unit.tick(GameModel.DELAY, world);
    }

    assertEquals(baseDamageAmount, unit.getDamageAmount());
  }

  @Test
  public void testEntityAttack() {
    Unit playerUnit = new DefaultUnit(new MapPoint(0,0),
        new MapSize(0.5, 0.5),
        Team.PLAYER,
        new DefaultUnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER);
    Unit enemyUnit = new DefaultUnit(new MapPoint(1,1),
        new MapSize(0.5, 0.5),
        Team.ENEMY,
        new DefaultUnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.SPEARMAN);
    HeroUnit heroUnit = new DefaultHeroUnit(new MapPoint(50,50),
        new MapSize(0.5, 0.5),
        new DefaultUnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
        UnitType.ARCHER,
        new ArrayList<Ability>());

    List<Level> levels = new ArrayList<>();
    levels.add(WorldTestUtils.createLevelWith(playerUnit, enemyUnit, heroUnit));
    World world = new World(levels, heroUnit, new DefaultPathFinder());

    playerUnit.setTargetUnit(enemyUnit);

    int previousHealth = enemyUnit.getHealth();

    for (int i = 0; i < 100; i++) {
      world.tick(50);
    }

    assertNotEquals(enemyUnit.getHealth(), previousHealth);
  }

  /**
   * Tests related to a unit firing projectiles.
   * @author chongdyla
   */
  public static class ProjectileTest {

    private World world;
    private Unit enemyUnit;
    private List<Projectile> firedProjectiles = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
      world = mock(World.class);
      // pretend there are no objects in the way
      when(world.isPassable(any())).thenReturn(true);
      // path is direct
      when(world.findPath(any(), any()))
          .thenAnswer(invocation -> Arrays.asList(invocation.getArguments()));

      enemyUnit = new DefaultUnit(
          new MapPoint(0.1, 0),
          new MapSize(1, 1),
          Team.ENEMY,
          new DefaultUnitSpriteSheet(GameImageResource.MALE_MAGE_SPRITE_SHEET),
          UnitType.ARCHER
      );

      // Spy on addProjectile method
      doAnswer(invocationOnMock ->
          firedProjectiles.add((Projectile) invocationOnMock.getArguments()[0]))
          .when(world)
          .addProjectile(any());
    }

    @Test
    public void testArcherLaunchesProjectiles() {
      // Given all the objects in the setUp()
      // and an archer that targets the enemy
      Unit unit = createPlayerUnit(UnitType.ARCHER);
      unit.setTargetUnit(enemyUnit);

      // when the game ticks several times
      for (int i = 0; i < 100; i++) {
        unit.tick(GameModel.DELAY, world);
      }

      // then some projectiles should have been created
      assertTrue(firedProjectiles.size() > 0);
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
      assertEquals(0, firedProjectiles.size());
    }

    @Test
    public void testSwordsmanDoesntFireProjectiles() {
      // Given all the objects in the setUp()
      // and a swordsman
      Unit unit = createPlayerUnit(UnitType.SWORDSMAN);
      unit.setTargetUnit(enemyUnit);

      // when the game ticks several times
      for (int i = 0; i < 100; i++) {
        unit.tick(GameModel.DELAY, world);
      }

      // then no projectiles should have been fired
      assertEquals(0, firedProjectiles.size());
    }

    @Test
    public void testProjectileHitShouldReduceHealth() {
      // Given all the objects in the setUp()
      // and a swordsman
      Unit unit = createPlayerUnit(UnitType.ARCHER);
      unit.setTargetUnit(enemyUnit);
      // and the initial health of the enemy
      final int enemyStartingHealth = enemyUnit.getHealth();

      // when a projectile is eventually fired/created
      final int tickLimit = 100; // (in case the projectile never fires)
      for (int i = 0; i < tickLimit; i++) {
        unit.tick(GameModel.DELAY, world);
        if (!firedProjectiles.isEmpty()) {
          break;
        }
        if (i == tickLimit - 1) {
          fail("Projectile never fired");
        }
      }
      Projectile projectile = firedProjectiles.get(0);

      // then the projectile should do damage
      int projectileDamage = projectile.getDamageAmount();
      assertTrue(projectileDamage > 0);

      // when the projectile eventually hits something
      while (!projectile.hasHit()) {
        unit.tick(GameModel.DELAY, world);
        // (only tick this projectile, ignore others)
        projectile.tick(GameModel.DELAY, world);
      }

      // then the enemy health should be reduced
      int hitEnemyHealth = enemyUnit.getHealth();
      assertEquals(enemyStartingHealth - projectileDamage, hitEnemyHealth);
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
      unit.takeDamage(5, this.world);
      assertEquals(prevHealth - 5, unit.getHealth());
    }

    private Unit createPlayerUnit(UnitType unitType) {
      return new DefaultUnit(
          new MapPoint(0, 0),
          new MapSize(1, 1),
          Team.PLAYER,
          new DefaultUnitSpriteSheet(GameImageResource.ARCHER_SPRITE_SHEET),
          unitType
      );
    }

    private Unit createEnemyUnit(UnitType unitType) {
      return new DefaultUnit(
          new MapPoint(0, 0),
          new MapSize(1, 1),
          Team.ENEMY,
          new DefaultUnitSpriteSheet(GameImageResource.ARCHER_SPRITE_SHEET),
          unitType
      );
    }

  }

  private static class ApplyToAllUnitsDamageBuffAbility extends DamageBuffAbility {

    public ApplyToAllUnitsDamageBuffAbility(
        int damageIncrease,
        double coolDownSeconds,
        double effectDurationSeconds
    ) {
      super(
          GameImageResource.TEST_IMAGE_1_1.getGameImage(),
          damageIncrease,
          coolDownSeconds,
          effectDurationSeconds
      );
    }

    @Override
    public Collection<Unit> selectUnitsToApplyOn(World world, Collection<Unit> selectedUnits) {
      return world.getAllUnits();
    }
  }

}
