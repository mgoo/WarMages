package test.game.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Collections;
import main.common.Renderable;
import main.common.World;
import main.common.entity.DeadUnit;
import main.common.entity.Direction;
import main.common.entity.Team;
import main.common.entity.Unit;
import main.common.entity.usable.Effect;
import main.common.images.GameImage;
import main.common.images.UnitSpriteSheet;
import main.common.util.MapPoint;
import main.common.util.MapSize;
import main.game.model.entity.unit.UnitType;
import main.game.model.entity.unit.state.Target;
import main.game.view.FogOfWarView;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the FogOfWarView class.
 *
 * @author Andrew McGhie
 */
public class FogOfWarViewTest extends GameViewTest {

  @Override
  @Before
  public void setUp() {
    super.setUp();
  }

  @Test
  public void testFogOfWar_withoutUnit() {
    this.gameView.onTick(0L);

    FogOfWarView fow = this.gameView.getFogOfWarView();
    assertEquals(0, fow.getImagePosition(0).x, 0.001);
    assertEquals(0, fow.getImagePosition(0).y, 0.001);
    assertEquals(this.config.getContextScreenWidth(), fow.getImageSize().width, 0.001);
    assertEquals(this.config.getContextScreenHeight(), fow.getImageSize().height, 0.001);

    // Check center is not see though
    assertNotEquals(0, fow.getImage().getRGB(0 ,0));
    assertNotEquals(0, fow.getImage().getRGB(config.getEntityViewTilePixelsX(), 0));

  }

  @Test
  public void testFogOfWar_withUnit() {
    this.gameModelMock.setEntities(
        Collections.singletonList(new UnitMock(new MapPoint(0, 0), new MapSize(1, 1)))
    );
    this.gameView.onTick(0L);

    FogOfWarView fow = this.gameView.getFogOfWarView();
    assertEquals(0, fow.getImagePosition(0).x, 0.001);
    assertEquals(0, fow.getImagePosition(0).y, 0.001);
    assertEquals(this.config.getContextScreenWidth(), fow.getImageSize().width, 0.001);
    assertEquals(this.config.getContextScreenHeight(), fow.getImageSize().height, 0.001);

    // Check center is see though
    assertEquals(0, fow.getImage().getRGB(0 ,0));

    // Check LOS
    assertNotEquals(0, fow.getImage().getRGB(config.getEntityViewTilePixelsX(), 0));

  }

  static class UnitMock extends EntityMock implements Unit {

    UnitMock(MapPoint position, MapSize size) {
      super(position, size);
    }

    @Override
    public void takeDamage(double amount, World world, Unit attacker) {
      return;
    }

    @Override
    public void gainHealth(double amount) {

    }

    @Override
    public double getHealth() {
      return 100;
    }

    @Override
    public double getMaxHealth() {
      return 100;
    }

    @Override
    public double getLineOfSight() {
      return 1;
    }

    @Override
    public DeadUnit createDeadUnit() {
      return null;
    }

    @Override
    public Team getTeam() {
      return Team.PLAYER;
    }

    @Override
    public void addEffect(Effect effect) {

    }

    @Override
    public UnitSpriteSheet getSpriteSheet() {
      return null;
    }

    @Override
    public Direction getCurrentDirection() {
      return null;
    }

    @Override
    public void setTarget(Target target) {

    }

    @Override
    public double getDamageModifier() {
      return 0;
    }

    @Override
    public double getRangeModifier() {
      return 0;
    }

    @Override
    public double getHealthPercent() {
      return 0;
    }

    @Override
    public boolean isSameTypeAs(Unit other) {
      return false;
    }

    @Override
    public String getType() {
      return null;
    }

    @Override
    public UnitType getUnitType() {
      return null;
    }

    @Override
    public void nextLevel() {

    }

    @Override
    public int getLevel() {
      return 0;
    }

    @Override
    public GameImage getIcon() {
      return null;
    }

    @Override
    public double getAttackSpeedModifier() {
      return 0;
    }

    @Override
    public double getSpeed() {
      return 0;
    }
  }
}
