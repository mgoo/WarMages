package main.game.model.world.saveandload;

import static main.game.model.entity.usable.BaseAbility.INFINITE_USES;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import main.game.model.GameModel;
import main.game.model.Level;
import main.game.model.Level.Goal;
import main.game.model.data.DataLoader;
import main.game.model.entity.Entity;
import main.game.model.entity.HeroUnit;
import main.game.model.entity.MapEntity;
import main.game.model.entity.Projectile;
import main.game.model.entity.Team;
import main.game.model.entity.unit.DefaultHeroUnit;
import main.game.model.entity.unit.DefaultUnit;
import main.game.model.entity.usable.BaseAbility;
import main.game.model.entity.usable.DefaultItem;
import main.game.model.entity.usable.Item;
import main.game.model.world.DefaultWorld;
import main.game.model.world.World;
import main.game.model.world.pathfinder.DefaultPathFinder;
import main.util.MapPoint;
import main.util.MapRect;
import main.util.MapSize;

/**
 * Loads a complex enough world to be played enjoyably.
 *
 * @author chongdyla
 */
public class DefaultWorldLoader implements WorldLoader {

  private static final MapSize HERO_SIZE = new MapSize(0.9, 0.9);
  private static final MapSize STANDARD_UNIT_SIZE = new MapSize(0.5, 0.5);

  @Override
  public World load() {
    DataLoader dataLoader = new DataLoader();
    return loadMultilevelWorld(dataLoader);
  }

  /**
   * Creates a new {@link GameModel} with the single level and example data.
   * This level doesn't have a wall of {@link MapEntity}s around the bounds.
   * This should have every non {@link Projectile} {@link Entity} in the
   * {@link main.game.model.entity} package for maximum coverage in tests.
   */
  public World loadSingleLevelTestWorld(DataLoader dataLoader) {
    return null;
  }

  /**
   * Create a complex enough level to play the game. For simplicity, the y axis has a fixed width
   * and the player moves along the x axis.
   */
  public World loadMultilevelWorld(DataLoader dataLoader) {
    final HeroUnit heroUnit = new DefaultHeroUnit(
        dataLoader.getDataForUnitType("unittype:icemage"),
        new MapPoint(3, 5),
        Arrays.asList(
            BaseAbility.buildAbility(
                dataLoader.getDataForAbility("ability:turret"), dataLoader, INFINITE_USES
            ),
            BaseAbility.buildAbility(
                dataLoader.getDataForAbility("ability:firebolt"), dataLoader, INFINITE_USES
            ),
            BaseAbility.buildAbility(
                dataLoader.getDataForAbility("ability:healspell"), dataLoader, INFINITE_USES
            ),
            BaseAbility.buildAbility(
                dataLoader.getDataForAbility("ability:lightning"), dataLoader, INFINITE_USES
            )
        ),
        dataLoader,
        3
    );

    LinkedList<Level> levels = new LinkedList<>();

    MapRect bounds = new MapRect(new MapPoint(0, 0), new MapPoint(48, 15));
    {
      // Example level to allow the player to learn how to attack

      // Bounds is 11 high, so y == 5 is center
      levels.add(new Level(
          bounds,
          Arrays.asList(
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:swordsman"),
                  new MapPoint(4, 4),
                  Team.PLAYER,
                  dataLoader,
                  1
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:swordsman"),
                  new MapPoint(4, 6),
                  Team.PLAYER,
                  dataLoader,
                  1
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:spearman"),
                  new MapPoint(3, 4),
                  Team.PLAYER,
                  dataLoader,
                  1
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:spearman"),
                  new MapPoint(3, 6),
                  Team.PLAYER,
                  dataLoader,
                  1
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:orcspearman"),
                  new MapPoint(12, 5),
                  Team.ENEMY,
                  dataLoader,
                  0
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:orcspearman"),
                  new MapPoint(11, 4),
                  Team.ENEMY,
                  dataLoader,
                  0
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:orcspearman"),
                  new MapPoint(11, 6),
                  Team.ENEMY,
                  dataLoader,
                  0
              )
          ),
          Arrays.asList(
              new DefaultItem(
                new MapPoint(7, 6),
                  BaseAbility.buildAbility(
                      dataLoader.getDataForAbility("ability:healpotion"), dataLoader, 3
                  ),
                  dataLoader.getDataForImage("sprite:potion_blue"),
                "Health Potion"
              )),
          Collections.emptyList(),
          Collections.emptyList(),
          new Goal.AllEnemiesKilled(),
          "Kill the enemy soldier with your hero and foot-knights<br>"
            + "<br>"
            + "<b>Left-Click</b> to select units<br>"
            + "<b>Right-Click</b> to attack and move"
      ));
    }

    {
      // A level with a few units, and items
      levels.add(new Level(
          bounds,
          Arrays.asList(
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:archer"),
                  new MapPoint(4, 4),
                  Team.PLAYER,
                  dataLoader,
                  1
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:archer"),
                  new MapPoint(4, 5),
                  Team.PLAYER,
                  dataLoader,
                  1
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:archer"),
                  new MapPoint(4, 6),
                  Team.PLAYER,
                  dataLoader,
                  1
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:lasermage"),
                  new MapPoint(5, 5),
                  Team.PLAYER,
                  dataLoader,
                  1
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:orcspearman"),
                  new MapPoint(22, 4),
                  Team.ENEMY,
                  dataLoader,
                  1
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:orcspearman"),
                  new MapPoint(22, 5),
                  Team.ENEMY,
                  dataLoader,
                  1
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:orcspearman"),
                  new MapPoint(22, 6),
                  Team.ENEMY,
                  dataLoader,
                  1
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:orcspearman"),
                  new MapPoint(23.5, 7),
                  Team.ENEMY,
                  dataLoader,
                  1
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:skeletonarcher"),
                  new MapPoint(23, 4.5),
                  Team.ENEMY,
                  dataLoader
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:skeletonarcher"),
                  new MapPoint(23, 5),
                  Team.ENEMY,
                  dataLoader
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:skeletonarcher"),
                  new MapPoint(23, 5.5),
                  Team.ENEMY,
                  dataLoader
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:skeletonarcher"),
                  new MapPoint(23, 6),
                  Team.ENEMY,
                  dataLoader
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:skeletonarcher"),
                  new MapPoint(23, 6.6),
                  Team.ENEMY,
                  dataLoader
              )
          ),
          Arrays.asList(
              new DefaultItem(
                  new MapPoint(19, 6),
                  BaseAbility.buildAbility(
                      dataLoader.getDataForAbility("ability:healpotion"), dataLoader, 3
                  ),
                  dataLoader.getDataForImage("sprite:potion_blue"),
                  "Health Potion"
              )
          ),
          Collections.emptyList(),
          Collections.emptyList(),
          new Goal.AllEnemiesKilled(),
          "Some Archer have joined your cause<br>"
              + "Try out your new archers on the new enemies<br>"
              + "<br>"
              + "To use abilities: <b>Left-Click</b> the icon then <b>Left-Click</b> a unit"

      ));
    }

    {
      // A level with a few units, and items
      Item goldRing = new DefaultItem(
          new MapPoint(24, 5),
          BaseAbility.buildAbility(
              dataLoader.getDataForAbility("ability:damagebuff"), dataLoader, INFINITE_USES
          ),
          dataLoader.getDataForImage("sprite:ring_gold"),
          "Ring of Damage boost"
      );
      levels.add(new Level(
          bounds,
          Collections.emptyList(),
          Collections.singletonList(
              goldRing
          ),
          Collections.emptyList(),
          Collections.emptyList(),
          (level, world)
              -> world.getHeroUnit().getItemAbilities().contains(
                  BaseAbility.buildAbility(
                      dataLoader.getDataForAbility("ability:damagebuff"),
                      dataLoader,
                      INFINITE_USES
                  )
          ),
          "Pick up the Gold Ring<br>"
            + "<b>Right-Click</b> on the ring when the hero is over it"
      ));
    }

    {
      // A level with more units
      levels.add(new Level(
          bounds,
          Arrays.asList(
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:firemage"),
                  new MapPoint(25, 4),
                  Team.PLAYER,
                  dataLoader
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:archer"),
                  new MapPoint(25, 5),
                  Team.PLAYER,
                  dataLoader
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:swordsman"),
                  new MapPoint(26, 4),
                  Team.PLAYER,
                  dataLoader
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:spearman"),
                  new MapPoint(26, 6),
                  Team.PLAYER,
                  dataLoader
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:spearman"),
                  new MapPoint(26, 5),
                  Team.PLAYER,
                  dataLoader
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:spearman"),
                  new MapPoint(27, 4),
                  Team.PLAYER,
                  dataLoader
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:spearman"),
                  new MapPoint(27, 6),
                  Team.PLAYER,
                  dataLoader
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:spearman"),
                  new MapPoint(27, 5),
                  Team.PLAYER,
                  dataLoader
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:firemage"),
                  new MapPoint(25, 6),
                  Team.PLAYER,
                  dataLoader
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:skeletonarcher"),
                  new MapPoint(35, 5),
                  Team.ENEMY,
                  dataLoader
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:skeletonarcher"),
                  new MapPoint(35, 4),
                  Team.ENEMY,
                  dataLoader
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:skeletonarcher"),
                  new MapPoint(35, 3),
                  Team.ENEMY,
                  dataLoader
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:orcspearman"),
                  new MapPoint(34, 4),
                  Team.ENEMY,
                  dataLoader
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:orcspearman"),
                  new MapPoint(34, 5),
                  Team.ENEMY,
                  dataLoader
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:orcspearman"),
                  new MapPoint(34, 6),
                  Team.ENEMY,
                  dataLoader
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:orcspearman"),
                  new MapPoint(35, 3.5),
                  Team.ENEMY,
                  dataLoader
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:orcspearman"),
                  new MapPoint(35, 4.5),
                  Team.ENEMY,
                  dataLoader
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:orcspearman"),
                  new MapPoint(35, 5.5),
                  Team.ENEMY,
                  dataLoader
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:orcspearman"),
                  new MapPoint(35, 6.5),
                  Team.ENEMY,
                  dataLoader
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:firedarkelf"),
                  new MapPoint(36, 5.5),
                  Team.ENEMY,
                  dataLoader,
                  4
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:orcspearman"),
                  new MapPoint(34, 6),
                  Team.ENEMY,
                  dataLoader,
                  1
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:skeletonarcher"),
                  new MapPoint(35, 7),
                  Team.ENEMY,
                  dataLoader
              )
          ),
          Collections.emptyList(),
          Collections.emptyList(),
          Collections.emptyList(),
          new Goal.AllEnemiesKilled(),
          "The Gold ring is was shiny and expensive<br>"
              + "now more people want to be your friend"
      ));
    }

    {
      // Surprise ambush level
      levels.add(new Level(
          bounds,
          Arrays.asList(
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:firedarkelf"),
                  new MapPoint(41, 3),
                  Team.ENEMY,
                  dataLoader,
                  4
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:firedarkelf"),
                  new MapPoint(41, 8),
                  Team.ENEMY,
                  dataLoader,
                  4
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:skeletonarcher"),
                  new MapPoint(42, 4),
                  Team.ENEMY,
                  dataLoader
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:skeletonarcher"),
                  new MapPoint(42, 7),
                  Team.ENEMY,
                  dataLoader
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:orcspearman"),
                  new MapPoint(40, 4),
                  Team.ENEMY,
                  dataLoader
              ),
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:orcspearman"),
                  new MapPoint(40, 6),
                  Team.ENEMY,
                  dataLoader
              )
          ),
          Arrays.asList(),
          Arrays.asList(),
          Arrays.asList(),
          new Goal.AllEnemiesKilled(),
          "Slaughter the infidels that do not like your gold ring"
      ));
    }

    {
      // MARCO LEVEL
      levels.add(new Level(
          bounds,
          Arrays.asList(
              new DefaultUnit(
                  dataLoader.getDataForUnitType("unittype:firedarkelf"),
                  new MapPoint(40, 6),
                  Team.ENEMY,
                  dataLoader,
                  20
              )
          ),
          Arrays.asList(),
          Arrays.asList(),
          Arrays.asList(),
          new Goal.AllEnemiesKilled(),
          "Gold ring is love. Gold ring is life"
      ));
    }
    return new DefaultWorld(levels, heroUnit, new DefaultPathFinder());
  }
}
