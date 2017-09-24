package test.game.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import main.game.controller.GameController;
import main.game.model.GameModel;
import main.game.model.entity.Entity;
import main.game.model.saveandload.WorldLoader;
import main.game.model.world.World;
import main.game.view.EntityRenderable;
import main.game.view.GameView;
import main.images.GameImage;
import main.renderer.Renderable;
import main.util.MapPoint;
import main.util.MapSize;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;

/**
 * Created by mgoo on 24/09/17.
 */
public class GameViewTest {

  GameView gameView;
  GameModelMock gameModelMock;
  List<Entity> entityList;

  @Before
  public void setUp() {
    GameControllerMock gameController = new GameControllerMock();
    World world = null;
    try {
      world = new WorldLoader(null).load("Test");
    } catch (IOException e) {
      // unreachable
    }
    this.gameModelMock = new GameModelMock();
    this.gameView = new GameView(gameController, gameModelMock);

    EntityMock entity = new EntityMock(new MapPoint(0,0), 1);
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

    entityList.add(new EntityMock(new MapPoint(1,3), 1));
    this.gameView.updateRenderables(0);

    assertEquals(2, this.gameView.getRenderables(0).size());
  }

  @Test
  public void testAnimation() {
    this.gameView.updateRenderables(0);
    ((EntityMock)this.entityList.get(0)).move(1, 0);
    this.gameView.updateRenderables(GameModel.delay);

    EntityRenderable er = ((EntityRenderable) this.gameView.getRenderables(0).get(0));

    // By time 50 the entitys effective position should have arrived to 1,0
    // Then it should continue afterwards until the next tick.
    for (int i = 0; i < GameModel.delay; i++) {
      MapPoint effEntityPos = er.getEffectiveEntityPosition(i);
      assertEquals(i/GameModel.delay, effEntityPos.x);
      assertEquals(0, effEntityPos.y);
    }

    this.gameView.updateRenderables(GameModel.delay*2);
    MapPoint effEntityPos = er.getEffectiveEntityPosition(GameModel.delay);
    assertEquals(1, effEntityPos.x);
    assertEquals(0, effEntityPos.y);


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
    public MapPoint getPosition(){
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

  private class GameControllerMock extends GameController{
    GameControllerMock(){

    }
  }
}
