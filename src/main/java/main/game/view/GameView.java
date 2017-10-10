package main.game.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import main.game.controller.GameController;
import main.game.model.GameModel;
import main.game.model.entity.Entity;
import main.game.view.EntityView.EntityRenderableComparator;
import main.game.view.events.MouseClick;
import main.common.images.ImageProvider;
import main.common.util.Config;
import main.common.util.Event;
import main.common.util.MapPoint;
import main.common.util.MapRect;

/**
 * A View of the Game.
 * Is responsible for creating all the EntityView objects and keeping their
 * locations up-to-date with the locations in the models.
 *
 * @author Andrew McGhie
 */
public class GameView {

  private static final int SCROLL_AREA_WIDTH = 5;

  private final Config config;

  private final GameController gameController;
  private final GameModel gameModel;
  private final ImageProvider imageProvider;
  private final Event<MouseClick> mouseClickEvent;

  private MapRect viewBox;
  private MapPoint mousePosition = new MapPoint(2,2);

  private List<EntityView> renderablesCache =
      Collections.synchronizedList(new ArrayList<>());

  /**
   * Constructor for game view sets the viewBox to start at origin 0,0.
   * @param config screen width and height must be set here
   */
  public GameView(Config config,
                  GameController gameController,
                  GameModel gameModel,
                  ImageProvider imageProvider,
                  Event<MouseClick> mouseClickEvent) {
    this.config = config;
    this.gameController = gameController;
    this.gameModel = gameModel;
    this.imageProvider = imageProvider;
    this.mouseClickEvent = mouseClickEvent;
    this.viewBox = new MapRect(0, 0,
        this.config.getContextScreenWidth(), this.config.getContextScreenHeight());
  }

  /**
   * Gets a list of the renderables.
   *
   * <p><b>N.B.</b> sorts the list so try to minimise calls.</p>
   * @param currentTime the time stap for the render iteration
   * @return unmodifiable sorted list
   */
  public synchronized List<EntityView> getRenderables(long currentTime) {
    this.renderablesCache.sort(new EntityRenderableComparator(currentTime));
    return Collections.unmodifiableList(this.renderablesCache);
  }

  /**
   * called when the Main Game Loop ticks. It updates the current renderables.
   * @param tickTime the time that the tick happened.
   */
  public synchronized void updateRenderables(long tickTime) {
    final Set<EntityView> toRemove = new HashSet<>();
    final Set<Entity> enitiesToCheck = new HashSet<>(this.gameModel.getAllEntities());

    this.renderablesCache.forEach(renderable -> {
      if (!enitiesToCheck.contains(renderable.getEntity())) {
        toRemove.add(renderable);
      } else {
        enitiesToCheck.remove(renderable.getEntity());
      }
    });

    this.renderablesCache.removeAll(toRemove);

    enitiesToCheck.forEach(entity -> {
      this.renderablesCache.add(new EntityView(this.config, entity, this.imageProvider));
    });

    this.renderablesCache.forEach(entityView -> {
      entityView.update(tickTime,
          this.gameModel.getUnitSelection().contains(entityView.getEntity()));
    });
  }

  private synchronized void updateViewBoxPosition() {
    if (this.mousePosition.x <= SCROLL_AREA_WIDTH)  {
      this.viewBox = this.viewBox.move(this.config.getGameViewScrollSpeed(), 0);
    }
    if (this.mousePosition.x >= this.config.getContextScreenWidth() - SCROLL_AREA_WIDTH) {
      this.viewBox = this.viewBox.move(-this.config.getGameViewScrollSpeed(), 0);
    }
    if (this.mousePosition.y <= SCROLL_AREA_WIDTH) {
      this.viewBox = this.viewBox.move(0, this.config.getGameViewScrollSpeed());
    }
    if (this.mousePosition.y >= this.config.getContextScreenHeight() - SCROLL_AREA_WIDTH) {
      this.viewBox = this.viewBox.move(0, -this.config.getGameViewScrollSpeed());
    }
  }

  public void onTick(Long tickTime) {
    this.updateRenderables(tickTime);
    this.updateViewBoxPosition();
  }

  /**
   * Takes the position on the screen an turns it into the Map Point that it is on.
   */
  public /*@ pure; non_null @*/ MapPoint pixToTile(MapPoint screenPos) {
    int originAdjustedX = (int)(screenPos.x - this.viewBox.x());
    int originAdjustedY = (int)(screenPos.y - this.viewBox.y());

    double tileWidthHalf = this.config.getEntityViewTilePixelsX() / 2D;
    double tileHeightHalf = this.config.getEntityViewTilePixelsY() / 2D;

    double mapX = (originAdjustedX / tileWidthHalf + originAdjustedY / tileHeightHalf) / 2;
    double mapY = (originAdjustedY / tileHeightHalf - (originAdjustedX / tileWidthHalf)) / 2;

    return new MapPoint(mapX, mapY);
  }

  public MapRect getViewBox() {
    return this.viewBox;
  }

  public void updateMousePosition(int x, int y) {
    this.mousePosition = new MapPoint(x, y);
  }

  /**
   * Triggers event for when Game View is clicked.
   */
  public void onLeftClick(int x, int y, boolean wasShiftDown, boolean wasCtrlDown) {
    this.mouseClickEvent.broadcast(new MouseClick() {
      @Override
      public boolean wasLeft() {
        return true;
      }

      @Override
      public boolean wasShiftDown() {
        return wasShiftDown;
      }

      @Override
      public boolean wasCtrlDown() {
        return wasCtrlDown;
      }

      @Override
      public MapPoint getLocation() {
        return pixToTile(new MapPoint(x, y));
      }
    });
  }

  /**
   * Triggers event for when Game View is clicked.
   */
  public void onRightClick(int x, int y, boolean wasShiftDown, boolean wasCtrlDown) {
    this.mouseClickEvent.broadcast(new MouseClick() {
      @Override
      public boolean wasLeft() {
        return false;
      }

      @Override
      public boolean wasShiftDown() {
        return wasShiftDown;
      }

      @Override
      public boolean wasCtrlDown() {
        return wasCtrlDown;
      }

      @Override
      public MapPoint getLocation() {
        return pixToTile(new MapPoint(x, y));
      }
    });
  }

  /**
   * Triggers a key event.
   */
  public void onKeyDown(char key, boolean wasShiftDown, boolean wasCtrlDown) {
    this.gameController.onKeyPress(new main.game.view.events.KeyEvent() {
      @Override
      public char getKey() {
        return key;
      }

      @Override
      public boolean wasShiftDown() {
        return wasShiftDown;
      }

      @Override
      public boolean wasCtrlDown() {
        return wasCtrlDown;
      }
    });
  }
}
