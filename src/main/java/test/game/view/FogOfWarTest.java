package test.game.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Arrays;
import java.util.Collections;
import main.common.World;
import main.common.entity.Direction;
import main.common.entity.Team;
import main.common.entity.Unit;
import main.common.entity.usable.Effect;
import main.common.images.GameImage;
import main.common.images.UnitSpriteSheet;
import main.common.util.MapPoint;
import main.common.util.MapSize;
import main.game.model.entity.unit.DeadUnit;
import main.game.model.entity.unit.UnitType;
import main.game.view.FogOfWarView;
import main.renderer.Renderable;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Andrew McGhie
 */
public class FogOfWarTest extends GameViewTest {

  @Override
  @Before
  public void setUp() {
    super.setUp();
  }

  @Test
  public void testFogOfWarImageSize() {
    this.gameModelMock.setEntities(
        Collections.singletonList(new UnitMock(new MapPoint(0, 0), new MapSize(1, 1)))
    );
    this.gameView.onTick(0L);

    Renderable fow = this.gameView.getFogOfWarView();
    assertEquals(0, fow.getImagePosition(0).x, 0.001);
    assertEquals(0, fow.getImagePosition(0).y, 0.001);
    assertEquals(this.config.getContextScreenWidth(), fow.getImageSize().width, 0.001);
    assertEquals(this.config.getContextScreenHeight(), fow.getImageSize().height, 0.001);

    // Check center is see though
    assertEquals(0, fow.getImage().getRGB(0 ,0));

    // Check LOS
    assertNotEquals(0, fow.getImage().getRGB(config.getEntityViewTilePixelsX(), 0));

  }

  class UnitMock extends EntityMock implements Unit {

    UnitMock(MapPoint position, MapSize size) {
      super(position, size);
    }

    @Override
    public boolean takeDamage(double amount, World world) {
      return false;
    }

    @Override
    public void gainHealth(double amount) {

    }

    @Override
    public double getHealth() {
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
    public Unit getTarget() {
      return null;
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
    public void setTargetUnit(Unit targetUnit) {

    }

    @Override
    public void setTargetPoint(MapPoint targetPoint) {

    }

    @Override
    public void clearTarget() {

    }

    @Override
    public double getDamageAmount() {
      return 0;
    }

    @Override
    public double getHealthPercent() {
      return 0;
    }

    @Override
    public UnitType getType() {
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
  }
}
