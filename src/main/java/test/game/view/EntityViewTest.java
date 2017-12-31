package test.game.view;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

import main.game.view.EntityView;
import main.util.MapPoint;
import main.util.MapSize;
import org.junit.Before;
import org.junit.Test;


/**
 * tests for the EntityView class.
 *
 * <p>checks position, initial load and image size.</p>
 *
 * @author Andrew McGhie
 */
public class EntityViewTest extends GameViewTest {

  @Override
  @Before
  public void setUp() {
    super.setUp();
  }

  @Test
  public void testInitialization() {
    assertNotNull(this.gameView);
    assertTrue(this.gameView.getRenderables(0).size() == 0);

    this.gameView.onTick(0L);

    assertEquals(1, this.gameView.getRenderables(0).size());

    entityList.add(new EntityMock(new MapPoint(1, 3), new MapSize(1, 1)));
    this.gameView.onTick(0L);

    assertEquals(2, this.gameView.getRenderables(0).size());
  }

  @Test
  public void testAnimation_x_correctPosition() {
    this.gameView.onTick(0L);
    ((EntityMock) this.entityList.get(0)).translatePosition(1, 0);
    this.gameView.onTick((long)this.config.getGameModelDelay());

    EntityView er = ((EntityView) this.gameView.getRenderables(0).get(0));

    // By time 50 the entities effective position should have arrived to 1,0
    // Then it should continue afterwards until the next tick.
    for (int i = 0; i < this.config.getGameModelDelay() * 2; i++) {
      MapPoint effEntityPos = er.getEffectiveEntityPosition(i);
      assertEquals((double) i / (double) this.config.getGameModelDelay(), effEntityPos.x, 0.001);
      assertEquals(0D, effEntityPos.y, 0.001);
    }

    this.gameView.onTick((long)this.config.getGameModelDelay());
    MapPoint effEntityPos = er.getEffectiveEntityPosition(this.config.getGameModelDelay());
    assertEquals(1D, effEntityPos.x, 0.001);
    assertEquals(0D, effEntityPos.y, 0.001);
  }

  @Test
  public void testAnimation_y_correctPosition() {
    this.gameView.onTick(0L);
    ((EntityMock) this.entityList.get(0)).translatePosition(0, 1);
    this.gameView.onTick((long)this.config.getGameModelDelay());

    EntityView er = ((EntityView) this.gameView.getRenderables(0).get(0));

    // By time 50 the entities effective position should have arrived to 0,1
    // Then it should continue afterwards until the next tick.
    for (int i = 0; i < this.config.getGameModelDelay() * 2; i++) {
      MapPoint effEntityPos = er.getEffectiveEntityPosition(i);
      assertEquals(0D, effEntityPos.x, 0.001);
      assertEquals((double) i / (double) this.config.getGameModelDelay(), effEntityPos.y, 0.001);
    }

    this.gameView.onTick((long)this.config.getGameModelDelay());
    MapPoint effEntityPos = er.getEffectiveEntityPosition(this.config.getGameModelDelay());
    assertEquals(0D, effEntityPos.x, 0.001);
    assertEquals(1D, effEntityPos.y, 0.001);
  }

  @Test
  public void testAnimation_xy_correctPosition() {
    this.gameView.onTick(0L);
    ((EntityMock) this.entityList.get(0)).translatePosition(5, 5);
    this.gameView.onTick((long)this.config.getGameModelDelay());

    EntityView er = ((EntityView) this.gameView.getRenderables(0).get(0));

    // By time 50 the entities effective position should have arrived to 5,5
    // Then it should continue afterwards until the next tick to 10,10.
    for (int i = 0; i < this.config.getGameModelDelay() * 2; i++) {
      MapPoint effEntityPos = er.getEffectiveEntityPosition(i);
      assertEquals(
          5D * (double) i / (double) this.config.getGameModelDelay(),
          effEntityPos.x,
          0.001
      );
      assertEquals(
          5D * (double) i / (double) this.config.getGameModelDelay(),
          effEntityPos.y,
          0.001
      );
    }

    this.gameView.onTick((long)this.config.getGameModelDelay());
    MapPoint effEntityPos = er.getEffectiveEntityPosition(this.config.getGameModelDelay());
    assertEquals(5D, effEntityPos.x, 0.001);
    assertEquals(5D, effEntityPos.y, 0.001);
  }

  @Test
  public void testAnimation_screenPosition() {
    this.gameView.onTick(0L);
    EntityView er = ((EntityView) this.gameView.getRenderables(0).get(0));
    ((EntityMock) this.entityList.get(0)).translatePosition(1, 1);
    this.gameView.onTick((long)this.config.getGameModelDelay());

    for (double i = 0; i < this.config.getGameModelDelay(); i++) {
      MapPoint imagePosition = er.getImagePosition((long) i);
      MapPoint effEntityPos = er.getEffectiveEntityPosition((long) i);

      assertEquals(i / (double) this.config.getGameModelDelay(), effEntityPos.x, 0.001);
      assertEquals(
          -25,
          imagePosition.x,
          0.001
      );
      assertEquals(
          config.getEntityViewTilePixelsY() * i / (double) this.config.getGameModelDelay()
              - config.getEntityViewTilePixelsY() / 2D,
          imagePosition.y,
          0.001
      );
    }

    this.gameView.onTick((long)this.config.getGameModelDelay() * 2);
    ((EntityMock) this.entityList.get(0)).translatePosition(1, 0);
    this.gameView.onTick((long)this.config.getGameModelDelay() * 3);

    // effective position should have arrived to 10,10
    // image dimensions are 1x1
    for (int i = this.config.getGameModelDelay() * 2; i < this.config.getGameModelDelay() * 3;
        i++) {
      MapPoint imagePosition = er.getImagePosition(i);

      assertEquals(
          (config.getEntityViewTilePixelsX() / 2)
              * (i - this.config.getGameModelDelay() * 2) / (double) this.config.getGameModelDelay()
              - config.getEntityViewTilePixelsX() / 2D,
          imagePosition.x, 0.001
      );
      assertEquals(config.getEntityViewTilePixelsY()
              + (config.getEntityViewTilePixelsY() / 2)
              * (i - this.config.getGameModelDelay() * 2) / (double) this.config.getGameModelDelay()
              - config.getEntityViewTilePixelsY() / 2D,
          imagePosition.y, 0.001
      );
    }
  }

  @Test
  public void testImageSize_basecase() {
    this.gameView.onTick(0L);
    EntityView er1 = ((EntityView) this.gameView.getRenderables(0).get(0));
    MapSize imageSize = er1.getImageSize();
    assertEquals(50D, imageSize.width, 0.001);
    assertEquals(50D, imageSize.height, 0.001);

    this.entityList.add(new EntityMock(new MapPoint(1, 1), new MapSize(0.2F, 0.2F)));
    this.gameView.onTick(1L);
    EntityView er2 = ((EntityView) this.gameView.getRenderables(0).get(1));
    imageSize = er2.getImageSize();
    assertEquals(10D, imageSize.width, 0.001);
    assertEquals(10D, imageSize.height, 0.001);

  }
}
