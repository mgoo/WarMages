package test.game.view;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import main.game.controller.DefaultGameController;
import main.game.model.DefaultGameModel;
import main.common.entity.Entity;
import main.game.model.entity.DefaultEntity;
import main.common.World;
import main.game.view.EntityView;
import main.game.view.GameView;
import main.images.DefaultImageProvider;
import main.common.images.GameImage;
import main.common.images.GameImageResource;
import main.common.images.ImageProvider;
import main.common.util.Config;
import main.common.util.Event;
import main.common.util.MapPoint;
import main.common.util.MapSize;
import main.common.util.MapRect;
import org.junit.Before;
import org.junit.Test;


/**
 * tests for the GameView class.
 *
 * <p>checks position, initial load and image size.</p>
 *
 * @author Andrew McGhie
 */
public class GameViewTest {

  GameView gameView;
  GameModelMock gameModelMock;
  List<Entity> entityList;
  Config config;

  /**
   * sets up the class variables that are used in every test.
   */
  @Before
  public void setUp() {
    final ImageProvider imageProvider = new DefaultImageProvider();
    final GameControllerMock gameController = new GameControllerMock();
    this.gameModelMock = new GameModelMock();
    this.config = new Config();
    this.config.setScreenDim(1000, 1000);
    this.config.setEntityViewTilePixelsX(50);
    this.config.setEntityViewTilePixelsY(50);
    this.gameView = new GameView(config,
        gameController, gameModelMock, imageProvider, new Event<>());

    EntityMock entity = new EntityMock(new MapPoint(0, 0), new MapSize(1, 1));
    entityList = new ArrayList<>();
    entityList.add(entity);
    this.gameModelMock.setEntities(entityList);
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

  @Test
  public void testMovingViewBox() {
    MapRect originalView = this.gameView.getViewBox();
    this.gameView.updateMousePosition(0, 0);
    assertEquals(originalView.topLeft.x - config.getGameViewScrollSpeed(),
        this.gameView.getViewBox().topLeft.x - config.getGameViewScrollSpeed());
    assertEquals(originalView.topLeft.y - config.getGameViewScrollSpeed(),
        this.gameView.getViewBox().topLeft.y - config.getGameViewScrollSpeed());
    assertEquals(originalView.bottomRight.x - config.getGameViewScrollSpeed(),
        this.gameView.getViewBox().bottomRight.x - config.getGameViewScrollSpeed());
    assertEquals(originalView.bottomRight.y - config.getGameViewScrollSpeed(),
        this.gameView.getViewBox().bottomRight.y - config.getGameViewScrollSpeed());
  }

  @Test
  public void testPixToTile() {
    assertEquals(new MapPoint(1, 1), this.gameView.pixToTile(new MapPoint(0, 50)));
    assertEquals(new MapPoint(1, 0), this.gameView.pixToTile(new MapPoint(25, 25)));
    assertEquals(new MapPoint(0, 1), this.gameView.pixToTile(new MapPoint(-25, 25)));
    assertEquals(new MapPoint(7, 5), this.gameView.pixToTile(new MapPoint(50, 300)));
  }

  private class GameModelMock extends DefaultGameModel {

    private List<Entity> entities = new ArrayList<>();

    /**
     * Creates a mock for testing game model.
     */
    GameModelMock() {
      super(null, null);
    }

    @Override
    public Collection<Entity> getAllEntities() {
      return this.entities;
    }

    void setEntities(List<Entity> entities) {
      this.entities = entities;
    }
  }

  private class EntityMock extends DefaultEntity {

    public EntityMock(MapPoint position, MapSize size) {
      super(position, size);
    }

    @Override
    public GameImage getImage() {
      return GameImageResource.TEST_IMAGE_1_1.getGameImage();
    }

    @Override
    public void tick(long timeSinceLastTick, World world) {

    }

    @Override
    public boolean contains(MapPoint point) {
      return false;
    }

    @Override
    public MapPoint getTopLeft() {
      throw new AssertionError("This method is not used here");
    }

    @Override
    public MapPoint getCentre() {
      return super.getTopLeft();
    }
  }

  private class GameControllerMock extends DefaultGameController {

    GameControllerMock() {
      super(null);
    }
  }
}
