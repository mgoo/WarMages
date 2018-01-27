package test.game.model.entity;

import static junit.framework.TestCase.assertFalse;
import static main.images.GameImageResource.GOLDEN_HERO_SPRITE_SHEET;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import main.game.model.GameModel;
import main.game.model.entity.HeroUnit;
import main.game.model.entity.Projectile;
import main.game.model.entity.Team;
import main.game.model.entity.Unit;
import main.game.model.entity.unit.DefaultHeroUnit;
import main.game.model.entity.unit.DefaultUnit;
import main.game.model.entity.unit.UnitType;
import main.game.model.entity.unit.attack.Attack;
import main.game.model.entity.unit.state.TargetMapPoint;
import main.game.model.entity.unit.state.TargetToAttack;
import main.game.model.world.World;
import main.images.DefaultUnitSpriteSheet;
import main.util.MapPoint;
import main.util.MapSize;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for DefaultUnits.
 *
 * @author paladogabr
 * @author chongdyla (Secondary Author)
 */
public class DefaultUnitTest {

  //note that CreateDefaultUnit creates a Spearman who travels with 0.1 travel speed

  /**
   * Creates and returns a default unit that is on the player's team.
   *
   * @return DefaultUnit friendly
   */
  public static DefaultUnit getPlayerUnit() {
    return new DefaultUnit(
        new MapPoint(1, 1), new MapSize(1, 1), Team.PLAYER, new StubUnitSpriteSheet(),
        UnitType.ARCHER
    );
  }

  /**
   * Creates and returns a default unit that is on the enemy's team.
   *
   * @return DefaultUnit foe
   */
  public static Unit getEnemyUnit() {
    return new DefaultUnit(
        new MapPoint(2, 2), new MapSize(1, 1), Team.ENEMY, new StubUnitSpriteSheet(),
        UnitType.ARCHER
    );
  }

  /**
   * Returns a list of coordinates going down in a straight line.
   *
   * @return path downwards.
   */
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

  /**
   * Returns a list of coordinates going across the screen in a straight line.
   *
   * @return path to the right.
   */
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

  /**
   * Returns a list of coordinates going from top right to bottom left.
   *
   * @return path diagonal.
   */
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

  /**
   * Returns a mock of the World class.
   *
   * @return World mock.
   */
  public static World getWorld(ArrayList<Unit> units, HeroUnit heroUnit) {
    World world = mock(World.class);
    when(world.getAllEntities()).thenReturn(new ArrayList<>(units));
    when(world.getAllUnits()).thenReturn(new ArrayList<>(units));
    when(world.getHeroUnit()).thenReturn(heroUnit);
    when(world.isPassable(any())).thenReturn(true);
    return world;
  }

  /**
   * Returns a DefaultHeroUnit.
   *
   * @return DHU.
   */
  public static HeroUnit getHeroUnit() {
    return new DefaultHeroUnit(
        new MapPoint(1, 1),
        new MapSize(1, 1),
        new DefaultUnitSpriteSheet(GOLDEN_HERO_SPRITE_SHEET),
        UnitType.ARCHER,
        Arrays.asList(),
        1
    );
  }

  private Unit getPlayer() {
    return getPlayerUnit();
  }

  private Unit getEnemy() {
    return getEnemyUnit();
  }

  @Test
  public void testDamageEnemy() {
    Unit player = new DefaultUnit(
        new MapPoint(0, 0),
        new MapSize(100, 100),
        Team.PLAYER,
        new StubUnitSpriteSheet(),
        UnitType.SWORDSMAN
    );
    Unit enemy = getEnemy();

    // Make sure they are in range of each other (by making them on top of each other)
    double dx = enemy.getCentre().x - player.getCentre().x;
    double dy = enemy.getCentre().y - player.getCentre().y;
    player.translatePosition(dx, dy);

    ArrayList<Unit> units = new ArrayList<>();
    units.add(player);
    units.add(enemy);
    World world = getWorld(units, getHeroUnit());
    when(world.findPath(any(), any()))
        .thenReturn(Arrays.asList(enemy.getCentre()));
    player.setTarget(new TargetToAttack(player, enemy, player.getUnitType().getBaseAttack()));
    double prevHealth = enemy.getHealth();
    for (int i = 0; i < 900; i++) {
      player.tick(GameModel.DELAY, world);
    }
    assertTrue(prevHealth > enemy.getHealth());
  }

  @Test
  public void testMovingEntity_oneSpaceHorizontally() {
    DefaultUnit unit = getPlayerUnit();
    ArrayList<Unit> units = new ArrayList<>();
    units.add(unit);
    World world = getWorld(units, getHeroUnit());

    List<MapPoint> path = getPathAcross();
    MapPoint targetPoint = path.get(path.size() - 1);
    when(world.findPath(any(), any())).thenReturn(path);

    unit.setTarget(new TargetMapPoint(unit, targetPoint));
    unit.translatePosition(-unit.getSize().width / 2, -unit.getSize().height / 2);

    // Allow unit to go to from idle to walking state
    unit.tick(GameModel.DELAY, world);

    // speed = 0.1, delay = 50
    for (int i = 0; i < 10; i++) {
      MapPoint mapPoint = new MapPoint(1D + Math.min(unit.getSpeed() * i, 1), 1);
      assertEquals(mapPoint.x, unit.getCentre().x, 0.001);
      assertEquals(mapPoint.y, unit.getCentre().y, 0.001);
      unit.tick(GameModel.DELAY, world);
    }
  }

  @Test
  public void testMovingEntity_twoSpacesHorizontally() {
    DefaultUnit unit = getPlayerUnit();
    ArrayList<Unit> units = new ArrayList<>();
    units.add(unit);
    World world = getWorld(units, getHeroUnit());

    List<MapPoint> path = getPathAcross();
    MapPoint targetPoint = path.get(path.size() - 1);
    when(world.findPath(any(), any())).thenReturn(path);

    unit.setTarget(new TargetMapPoint(unit, targetPoint));
    unit.translatePosition(-unit.getSize().width / 2, -unit.getSize().height / 2);

    // Allow unit to go to from idle to walking state
    unit.tick(GameModel.DELAY, world);

    for (int i = 0; i < 20; i++) {
      MapPoint mapPoint = new MapPoint(1D + unit.getSpeed() * i, 1);
      assertEquals(mapPoint.x, unit.getCentre().x, 0.001);
      assertEquals(mapPoint.y, unit.getCentre().y, 0.001);
      unit.tick(GameModel.DELAY, world);
    }
  }

  @Test
  public void testMovingEntity_oneSpaceVertically() {
    DefaultUnit unit = getPlayerUnit();
    ArrayList<Unit> units = new ArrayList<>();
    units.add(unit);
    World world = getWorld(units, getHeroUnit());

    List<MapPoint> path = getPathDown();
    MapPoint targetPoint = path.get(path.size() - 1);
    when(world.findPath(any(), any())).thenReturn(path);

    unit.setTarget(new TargetMapPoint(unit, targetPoint));
    unit.translatePosition(-unit.getSize().width / 2, -unit.getSize().height / 2);

    // Allow unit to go to from idle to walking state
    unit.tick(GameModel.DELAY, world);

    for (int i = 0; i < 10; i++) {
      MapPoint mapPoint = new MapPoint(1, 1D + unit.getSpeed() * i);
      assertEquals(mapPoint.x, unit.getCentre().x, 0.001);
      assertEquals(mapPoint.y, unit.getCentre().y, 0.001);
      unit.tick(GameModel.DELAY, world);
    }
  }

  @Test
  public void testMovingEntity_twoSpacesVertically() {
    DefaultUnit unit = getPlayerUnit();
    ArrayList<Unit> units = new ArrayList<>();
    units.add(unit);
    World world = getWorld(units, getHeroUnit());
    List<MapPoint> path = getPathDown();
    MapPoint targetPoint = path.get(path.size() - 1);
    when(world.findPath(any(), any())).thenReturn(path);

    unit.setTarget(new TargetMapPoint(unit, targetPoint));
    unit.translatePosition(-unit.getSize().width / 2, -unit.getSize().height / 2);

    // Allow unit to go to from idle to walking state
    unit.tick(GameModel.DELAY, world);

    for (int i = 0; i < 20; i++) {
      MapPoint mapPoint = new MapPoint(1, 1D + unit.getSpeed() * i);
      assertEquals(mapPoint.x, unit.getCentre().x, 0.001);
      assertEquals(mapPoint.y, unit.getCentre().y, 0.001);
      unit.tick(GameModel.DELAY, world);
    }
  }

  @Test
  public void testMovingEntity_oneSpaceDiagonally() {
    Unit unit = getPlayerUnit();
    ArrayList<Unit> units = new ArrayList<>();
    units.add(unit);
    World world = getWorld(units, getHeroUnit());;

    List<MapPoint> path = getPathDiagonal();
    MapPoint targetPoint = path.get(path.size() - 1);
    when(world.findPath(any(), any())).thenReturn(path);

    unit.setTarget(new TargetMapPoint(unit, targetPoint));
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
  public void testEntityAttack() {
    Unit playerUnit = new DefaultUnit(
        new MapPoint(0, 0),
        new MapSize(0.5, 0.5),
        Team.PLAYER,
        new StubUnitSpriteSheet(),
        UnitType.SWORDSMAN
    );
    Unit enemyUnit = new DefaultUnit(
        new MapPoint(1, 1),
        new MapSize(0.5, 0.5),
        Team.ENEMY,
        new StubUnitSpriteSheet(),
        UnitType.SPEARMAN
    );
    HeroUnit heroUnit = new DefaultHeroUnit(
        new MapPoint(50, 50),
        new MapSize(0.5, 0.5),
        new StubUnitSpriteSheet(),
        UnitType.ARCHER,
        new ArrayList<>(),
        0
    );

    ArrayList<Unit> units = new ArrayList<>();
    units.add(playerUnit);
    units.add(enemyUnit);
    World world = getWorld(units, heroUnit);
    when(world.findPath(any(), any()))
        .thenAnswer(invocation -> Arrays.asList(enemyUnit.getCentre()));

    playerUnit.setTarget(new TargetToAttack(playerUnit,
        enemyUnit,
        playerUnit.getUnitType().getBaseAttack()));

    double previousHealth = enemyUnit.getHealth();

    for (int i = 0; i < 200; i++) {
      playerUnit.tick(50, world);
    }

    assertNotEquals(enemyUnit.getHealth(), previousHealth);
  }

  /**
   * Tests related to a unit firing projectiles.
   *
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
          new StubUnitSpriteSheet(),
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
      unit.setTarget(new TargetToAttack(unit, enemyUnit, unit.getUnitType().getBaseAttack()));

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
      unit.setTarget(new TargetToAttack(unit, enemyUnit, unit.getUnitType().getBaseAttack()));
      // and the initial health of the enemy
      final double enemyStartingHealth = enemyUnit.getHealth();

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
      double projectileDamage =
          ((Attack) unit.getUnitType().getBaseAttack()).getAmount();
      assertTrue(projectileDamage > 0);

      // when the projectile eventually hits something
      while (!projectile.hasHit()) {
        unit.tick(GameModel.DELAY, world);
        // (only tick this projectile, ignore others)
        projectile.tick(GameModel.DELAY, world);
      }

      // then the enemy health should be reduced
      double hitEnemyHealth = enemyUnit.getHealth();
      assertEquals(enemyStartingHealth - projectileDamage, hitEnemyHealth, 0.001);
    }

    @Test
    public void testTeamsCanAttack() {
      assertTrue(Team.ENEMY.canAttack(Team.PLAYER));
      assertFalse(Team.PLAYER.canAttack(Team.PLAYER));
    }

    @Test
    public void testDamage() {
      Unit unit = createPlayerUnit(UnitType.SWORDSMAN);
      Unit attacker = createEnemyUnit(UnitType.SWORDSMAN);
      double prevHealth = unit.getHealth();
      unit.takeDamage(5, this.world, attacker);
      assertEquals(prevHealth - 5, unit.getHealth(), 0.001);
    }

    private Unit createPlayerUnit(UnitType unitType) {
      return new DefaultUnit(
          new MapPoint(0, 0),
          new MapSize(1, 1),
          Team.PLAYER,
          new StubUnitSpriteSheet(),
          unitType
      );
    }

    private Unit createEnemyUnit(UnitType unitType) {
      return new DefaultUnit(
          new MapPoint(0, 0),
          new MapSize(1, 1),
          Team.ENEMY,
          new StubUnitSpriteSheet(),
          unitType
      );
    }

  }

}
