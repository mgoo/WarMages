package test.game.view;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import main.game.view.DefaultGameView;
import main.common.GameView;
import main.common.entity.HeroUnit;
import main.common.GameController;
import main.common.events.AbilityIconClick;
import main.common.events.ItemIconClick;
import main.common.events.KeyEvent;
import main.common.events.MouseClick;
import main.common.events.MouseDrag;
import main.common.events.UnitIconClick;
import main.common.GameModel;
import main.common.entity.Unit;
import main.common.entity.Entity;
import main.common.World;
import main.common.images.GameImage;
import main.common.images.GameImageResource;
import main.common.images.ImageProvider;
import main.common.util.Config;
import main.common.util.MapPoint;
import main.common.util.MapSize;
import main.common.util.MapRect;
import org.junit.Before;
import org.junit.Test;


/**
 * tests for the DefaultGameView class.
 *
 * <p>checks position, initial load and image size.</p>
 *
 * @author Andrew McGhie
 */
public class GameViewTest {

  GameView gameView;
  GameModelMock gameModelMock;
  GameControllerMock gameControllerMock;
  List<Entity> entityList;
  Config config;

  /**
   * sets up the class variables that are used in every test.
   */
  @Before
  public void setUp() {
    final ImageProvider imageProvider = new ImageProvider() {
      @Override
      protected BufferedImage load(String filePath) throws IOException {
        return new BufferedImage(1,1, BufferedImage.TYPE_4BYTE_ABGR);
      }

      @Override
      protected void storeInCache(GameImage gameImage, BufferedImage image) {

      }

      @Override
      protected BufferedImage getFromCache(GameImage gameImage) {
        return new BufferedImage(1,1, BufferedImage.TYPE_4BYTE_ABGR);
      }
    };
    gameControllerMock = mock(GameControllerMock.class);
    this.gameModelMock = new GameModelMock();

    this.config = new Config();
    this.config.setScreenDim(1000, 1000);
    this.config.setEntityViewTilePixelsX(50);
    this.config.setEntityViewTilePixelsY(50);
    World world = mock(World.class);
    HeroUnit hero = mock(HeroUnit.class);
    when(hero.getCentre()).thenReturn(new MapPoint(20, 0));
    when(world.getHeroUnit()).thenReturn(hero);
    when(world.getCurrentLevelBounds()).thenReturn(new MapRect(-100, -100, 200, 200));
    this.gameView = new DefaultGameView(config,
        gameControllerMock,
        gameModelMock,
        imageProvider,
        world);

    EntityMock entity = new EntityMock(new MapPoint(0, 0), new MapSize(1, 1));
    entityList = new ArrayList<>();
    entityList.add(entity);
    this.gameModelMock.setEntities(entityList);
  }

  @Test
  public void testMovingViewBox() {
    MapRect originalView = this.gameView.getViewBox();
    this.gameView.updateMousePosition(0, 0);
    assertEquals(originalView.topLeft.x + config.getGameViewScrollSpeed(),
        this.gameView.getViewBox().topLeft.x + config.getGameViewScrollSpeed());
    assertEquals(originalView.topLeft.y + config.getGameViewScrollSpeed(),
        this.gameView.getViewBox().topLeft.y + config.getGameViewScrollSpeed());
    assertEquals(originalView.bottomRight.x + config.getGameViewScrollSpeed(),
        this.gameView.getViewBox().bottomRight.x + config.getGameViewScrollSpeed());
    assertEquals(originalView.bottomRight.y + config.getGameViewScrollSpeed(),
        this.gameView.getViewBox().bottomRight.y + config.getGameViewScrollSpeed());
  }

  @Test
  public void test_eventPassThroughs() {
    this.gameView.onLeftClick(0, 0, true, true);
    verify(this.gameControllerMock).onMouseEvent(any());
    this.gameView.onRightClick(0 ,0,false, false);
    verify(this.gameControllerMock, times(2)).onMouseEvent(any());
    this.gameView.onDbClick(0 , 0, true, true);
    verify(this.gameControllerMock).onDbClick(any());
    this.gameView.onDrag(0, 0, 5, 5, true, false);
    verify(this.gameControllerMock).onMouseDrag(any());
    this.gameView.onKeyDown('z', true, false);
    verify(this.gameControllerMock).onKeyPress(any());

    this.gameView.unitClick(null, true, true, true);
    verify(this.gameControllerMock).onUnitIconClick(any());
    this.gameView.abilityClick(null,false, false, true);
    verify(this.gameControllerMock).onAbilityIconClick(any());
    this.gameView.itemClick(null, true, true, true);
    verify(this.gameControllerMock).onItemIconClick(any());


  }

  static class GameModelMock implements GameModel {

    private List<Entity> entities = new ArrayList<>();

    @Override
    public Collection<Entity> getAllEntities() {
      return this.entities;
    }

    @Override
    public void startGame() {

    }

    @Override
    public void setUnitSelection(Collection<Unit> unitSelection) {

    }

    @Override
    public Collection<Unit> getUnitSelection() {
      return Arrays.asList();
    }

    @Override
    public void addToUnitSelection(Unit unit) {

    }

    @Override
    public Collection<Unit> getAllUnits() {
      return null;
    }

    @Override
    public World getWorld() {
      return null;
    }

    @Override
    public HeroUnit getHeroUnit() {
      return null;
    }

    @Override
    public void pauseGame() {

    }

    @Override
    public void resumeGame() {

    }

    @Override
    public void stopGame() {

    }

    void setEntities(List<Entity> entities) {
      this.entities = entities;
    }

  }

  static class EntityMock implements Entity {


    private MapPoint position;
    private final MapSize size;

    EntityMock(MapPoint position, MapSize size) {
      this.position = position;
      this.size = size;
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
      return position;
    }

    @Override
    public MapSize getSize() {
      return this.size;
    }

    @Override
    public MapRect getRect() {
      return null;
    }

    @Override
    public void translatePosition(double dx, double dy) {
      position = new MapPoint(position.x + dx, position.y + dy);
    }

    @Override
    public void slidePosition(double dx, double dy) {

    }
  }

  static class GameControllerMock implements GameController {

    GameControllerMock() {

    }

    @Override
    public void onKeyPress(KeyEvent keyevent) {

    }

    @Override
    public void onMouseEvent(MouseClick mouseEvent) {

    }

    @Override
    public void onMouseDrag(MouseDrag mouseEvent) {

    }

    @Override
    public void onDbClick(MouseClick mouseClick) {

    }

    @Override
    public void onUnitIconClick(UnitIconClick clickEvent) {

    }

    @Override
    public void onAbilityIconClick(AbilityIconClick clickEvent) {

    }

    @Override
    public void onItemIconClick(ItemIconClick clickEvent) {

    }
  }
}
