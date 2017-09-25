package test.game.view;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import main.game.controller.GameController;
import main.game.model.GameModel;
import main.game.model.entity.Entity;
import main.game.view.EntityRenderable;
import main.game.view.GameView;
import main.images.GameImage;
import main.util.MapPoint;
import main.util.MapSize;
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

  /**
   * sets up the class variables that are used in every test.
   */
  @Before
  public void setUp() {
    GameControllerMock gameController = new GameControllerMock();
    this.gameModelMock = new GameModelMock();
    this.gameView = new GameView(gameController, gameModelMock);

    EntityMock entity = new EntityMock(new MapPoint(0, 0), 1);
    entityList = new ArrayList<>();
    entityList.add(entity);
    this.gameModelMock.setEntities(entityList);
  }

  @Test
  public void testInitilisation() {
    assertNotNull(this.gameView);
    assertTrue(this.gameView.getRenderables(0).size() == 0);

    this.gameView.updateRenderables(0);

    assertEquals(1, this.gameView.getRenderables(0).size());

    entityList.add(new EntityMock(new MapPoint(1, 3), 1));
    this.gameView.updateRenderables(0);

    assertEquals(2, this.gameView.getRenderables(0).size());
  }

  @Test
  public void testAnimation_x_correctPosition() {
    this.gameView.updateRenderables(0);
    ((EntityMock) this.entityList.get(0)).move(1, 0);
    this.gameView.updateRenderables(GameModel.delay);

    EntityRenderable er = ((EntityRenderable) this.gameView.getRenderables(0).get(0));

    // By time 50 the entities effective position should have arrived to 1,0
    // Then it should continue afterwards until the next tick.
    for (int i = 0; i < GameModel.delay * 2; i++) {
      MapPoint effEntityPos = er.getEffectiveEntityPosition(i);
      assertEquals((double) i / (double) GameModel.delay, effEntityPos.x, 0.001);
      assertEquals(0D, effEntityPos.y, 0.001);
    }

    this.gameView.updateRenderables(GameModel.delay);
    MapPoint effEntityPos = er.getEffectiveEntityPosition(GameModel.delay);
    assertEquals(1D, effEntityPos.x, 0.001);
    assertEquals(0D, effEntityPos.y, 0.001);
  }

  @Test
  public void testAnimation_y_correctPosition() {
    this.gameView.updateRenderables(0);
    ((EntityMock) this.entityList.get(0)).move(0, 1);
    this.gameView.updateRenderables(GameModel.delay);

    EntityRenderable er = ((EntityRenderable) this.gameView.getRenderables(0).get(0));

    // By time 50 the entities effective position should have arrived to 0,1
    // Then it should continue afterwards until the next tick.
    for (int i = 0; i < GameModel.delay * 2; i++) {
      MapPoint effEntityPos = er.getEffectiveEntityPosition(i);
      assertEquals(0D, effEntityPos.x, 0.001);
      assertEquals((double) i / (double) GameModel.delay, effEntityPos.y, 0.001);
    }

    this.gameView.updateRenderables(GameModel.delay);
    MapPoint effEntityPos = er.getEffectiveEntityPosition(GameModel.delay);
    assertEquals(0D, effEntityPos.x, 0.001);
    assertEquals(1D, effEntityPos.y, 0.001);
  }

  @Test
  public void testAnimation_xy_correctPosition() {
    this.gameView.updateRenderables(0);
    ((EntityMock) this.entityList.get(0)).move(5, 5);
    this.gameView.updateRenderables(GameModel.delay);

    EntityRenderable er = ((EntityRenderable) this.gameView.getRenderables(0).get(0));

    // By time 50 the entities effective position should have arrived to 5,5
    // Then it should continue afterwards until the next tick to 10,10.
    for (int i = 0; i < GameModel.delay * 2; i++) {
      MapPoint effEntityPos = er.getEffectiveEntityPosition(i);
      assertEquals(5D * (double) i / (double) GameModel.delay, effEntityPos.x, 0.001);
      assertEquals(5D * (double) i / (double) GameModel.delay, effEntityPos.y, 0.001);
    }

    this.gameView.updateRenderables(GameModel.delay);
    MapPoint effEntityPos = er.getEffectiveEntityPosition(GameModel.delay);
    assertEquals(5D, effEntityPos.x, 0.001);
    assertEquals(5D, effEntityPos.y, 0.001);
  }

  @Test
  public void test_getImagePosition() {

  }

  private class GameModelMock extends GameModel {

    private List<Entity> entities = new ArrayList<>();

    /**
     * Creates a mock for testing game model.
     */
    GameModelMock() {
      super(null);
    }

    @Override
    public Collection<Entity> getAllEntities() {
      return this.entities;
    }

    void setEntities(List<Entity> entities) {
      this.entities = entities;
    }
  }

  private class EntityMock extends Entity {

    public EntityMock(MapPoint position, float size) {
      super(position, size);
    }

    @Override
    public GameImage getImage() {
      return GameImage._TEST_FULL_SIZE;
    }

    @Override
    public MapPoint getPosition() {
      return this.position;
    }

    @Override
    public MapSize getSize() {
      return new MapSize(this.size, this.size);
    }

    void move(double dX, double dY) {
      this.position = new MapPoint(this.position.x + dX, this.position.y + dY);
    }
  }

  private class GameControllerMock extends GameController {

    GameControllerMock() {

    }
  }
}
