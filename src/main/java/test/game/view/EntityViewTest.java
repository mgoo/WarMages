package test.game.view;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import main.game.model.entity.DefaultMapEntity;
import main.game.model.entity.Entity;
import main.game.view.EntityView;
import main.images.GameImageResource;
import main.util.Event;
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

  Entity entity;

  @Override
  @Before
  public void setUp() {
    super.setUp();
    entity = mock(DefaultMapEntity.class);
    when(entity.getCentre()).thenReturn(new MapPoint(0, 0));
    when(entity.getSize()).thenReturn(new MapSize(1, 1));
    when(entity.getRemovedEvent()).thenReturn(new Event<Void>());
    when(entity.accept(any(), any())).thenCallRealMethod();
    when(entity.getImage()).thenReturn(GameImageResource.TEST_IMAGE_1_1.getGameImage());

    when(world.recieveRecentlyAddedEntities()).thenReturn(
        Collections.singletonList(entity),
        Collections.emptyList()
    );

  }

  @Test
  public void testInitialization() {

    assertNotNull(this.gameView);
    assertTrue(this.gameView.getRenderables(0).size() == 0);

    this.gameView.onTick(0L);

    assertEquals(1, this.gameView.getRenderables(0).size());

    this.gameView.onTick(0L);

    assertEquals(1, this.gameView.getRenderables(0).size());
  }

  @Test
  public void testAnimation_x_correctPosition() {
    this.gameView.onTick(0L);
    when(entity.getCentre()).thenReturn(new MapPoint(1, 0));
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
    when(entity.getCentre()).thenReturn(new MapPoint(0, 1));
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
    when(entity.getCentre()).thenReturn(new MapPoint(5, 5));
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
    when(entity.getCentre()).thenReturn(new MapPoint(1, 1));
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
    when(entity.getCentre()).thenReturn(new MapPoint(2, 1));
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
    MapPoint entityPosition = new MapPoint(0, 0);
    Entity entity1 = mock(DefaultMapEntity.class);
    when(entity1.getCentre()).thenReturn(entityPosition);
    when(entity1.getSize()).thenReturn(new MapSize(1, 1));
    when(entity1.getRemovedEvent()).thenReturn(new Event<Void>());
    when(entity1.accept(any(), any())).thenCallRealMethod();
    when(entity1.getImage()).thenReturn(GameImageResource.TEST_IMAGE_1_1.getGameImage());

    Entity entity2 = mock(DefaultMapEntity.class);
    when(entity2.getCentre()).thenReturn(entityPosition);
    when(entity2.getSize()).thenReturn(new MapSize(0.2F, 0.2F));
    when(entity2.getRemovedEvent()).thenReturn(new Event<Void>());
    when(entity2.accept(any(), any())).thenCallRealMethod();
    when(entity2.getImage()).thenReturn(GameImageResource.TEST_IMAGE_1_1.getGameImage());


    when(world.recieveRecentlyAddedEntities()).thenReturn(
        Collections.singletonList(entity1),
        Collections.singletonList(entity2)
    );

    this.gameView.onTick(0L);
    EntityView er1 = ((EntityView) this.gameView.getRenderables(0).get(0));
    MapSize imageSize = er1.getImageSize();
    assertEquals(50D, imageSize.width, 0.001);
    assertEquals(50D, imageSize.height, 0.001);

    this.gameView.onTick(1L);
    EntityView er2 = ((EntityView) this.gameView.getRenderables(0).get(1));
    imageSize = er2.getImageSize();
    assertEquals(10D, imageSize.width, 0.001);
    assertEquals(10D, imageSize.height, 0.001);

  }
}
