package main.game.view;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import main.common.GameController;
import main.common.GameModel;
import main.common.World;
import main.common.entity.Entity;
import main.common.entity.Unit;
import main.common.entity.usable.Ability;
import main.common.entity.usable.Item;
import main.common.images.GameImageResource;
import main.common.images.ImageProvider;
import main.common.util.Config;
import main.common.util.Event;
import main.common.util.MapPoint;
import main.common.util.MapPolygon;
import main.common.util.MapRect;
import main.game.view.EntityView.EntityRenderableComparator;
import main.game.view.events.AbilityIconClick;
import main.game.view.events.ItemIconClick;
import main.game.view.events.MouseClick;
import main.game.view.events.MouseDrag;
import main.game.view.events.UnitIconClick;
import main.renderer.Renderable;

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
  private final GameModel model;
  private final ImageProvider imageProvider;
  private final Event<MouseClick> mouseClickEvent;
  private final World world;

  private MapRect viewBox;
  private MapPoint mousePosition;

  /**
   * {@link CopyOnWriteArrayList} is required to avoid modifications to the list while {@link
   * main.renderer.DefaultRenderer} is reading it. To avoid unnecessary amounts of copying, if
   * adding a large amount of items to this list, prefer using {@link List#addAll(Collection)}
   * rather than calling {@link List#add(Object)} for each element.
   */
  private List<EntityView> renderablesCache = new CopyOnWriteArrayList<>();
  private FogOfWarView fogOfWarView;
  private final BackGroundView backGroundView;

  /**
   * Constructor for game view sets the viewBox to start at origin 0,0.
   * @param config screen width and height must be set here
   */
  public GameView(Config config,
                  GameController gameController,
                  GameModel model,
                  ImageProvider imageProvider,
                  Event<MouseClick> mouseClickEvent,
                  World world) {
    this.config = config;
    this.gameController = gameController;
    this.model = model;
    this.imageProvider = imageProvider;
    this.mouseClickEvent = mouseClickEvent;
    this.world = world;
    MapPoint initialPositionPixel = EntityView.tileToPix(world.getHeroUnit().getCentre(), config);
    this.viewBox = new MapRect(initialPositionPixel.x - this.config.getContextScreenWidth() / 2 ,
        initialPositionPixel.y - this.config.getContextScreenHeight() / 2,
        this.config.getContextScreenWidth(),
        this.config.getContextScreenHeight());
    this.fogOfWarView = ViewFactory.makeFogOfWarView(config);
    this.mousePosition = new MapPoint(config.getContextScreenWidth() / 2,
        config.getContextScreenHeight() / 2);
    try {
      this.backGroundView = ViewFactory.makeBackGroundView(
          config,
          this,
          GameImageResource.GRASS_TILE.getGameImage().load(imageProvider)
      );
    } catch (IOException e) {
      throw new RuntimeException("Cannot load backgroud image");
    }
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

  public synchronized Renderable getFogOfWarView() {
    return this.fogOfWarView;
  }

  public synchronized Renderable getBackGroundView() {
    return this.backGroundView;
  }

  /**
   * called when the Main Game Loop ticks. It updates the current renderables.
   * @param tickTime the time that the tick happened.
   */
  public synchronized void updateRenderables(long tickTime) {
    final Set<EntityView> toRemove = new HashSet<>();
    final Set<Entity> entitiesToAdd = new HashSet<>(this.model.getAllEntities());

    this.renderablesCache.forEach(renderable -> {
      if (entitiesToAdd.contains(renderable.getEntity())) {
        entitiesToAdd.remove(renderable.getEntity());
      } else {
        toRemove.add(renderable);
      }
    });

    this.renderablesCache.removeAll(toRemove);

    List<EntityView> entityViewsToAdd = entitiesToAdd.stream()
        .map(entity -> ViewFactory.makeEntityView(this.config, entity, this.imageProvider))
        .collect(Collectors.toList());
    this.renderablesCache.addAll(entityViewsToAdd);

    this.renderablesCache.forEach(entityView -> {
      entityView.update(tickTime,
          this.model.getUnitSelection().contains(entityView.getEntity()));
    });

    // Update FoW

    Set<UnitView> revealingUnits = new HashSet<>();
    this.renderablesCache
        .stream()
        .filter(entityView -> entityView instanceof UnitView)
        .filter(unitView -> ((UnitView) unitView).revealsFogOfWar())
        .forEach(unitView -> revealingUnits.add((UnitView)unitView));
    fogOfWarView.calculate(revealingUnits, this, tickTime);
  }

  private synchronized void updateViewBoxPosition() {
    double dx = 0;
    double dy = 0;
    if (this.mousePosition.x <= SCROLL_AREA_WIDTH)  {
      dx = -this.config.getGameViewScrollSpeed();
    } else if (this.mousePosition.x >= this.config.getContextScreenWidth() - SCROLL_AREA_WIDTH) {
      dx = this.config.getGameViewScrollSpeed();
    }
    if (this.mousePosition.y <= SCROLL_AREA_WIDTH) {
      dy = -this.config.getGameViewScrollSpeed();
    } else if (this.mousePosition.y >= this.config.getContextScreenHeight() - SCROLL_AREA_WIDTH) {
      dy = this.config.getGameViewScrollSpeed();
    }
    if (dx != 0 || dy != 0) {
      // Make sure the new position is in the level bounds
      MapPoint topLeft
          = this.pixToTile(this.viewBox.topLeft.x + dx, this.viewBox.topLeft.y + dy);
      MapPoint topRight
          = this.pixToTile(this.viewBox.bottomRight.x + dx, this.viewBox.topLeft.y + dy);
      MapPoint bottomLeft
          = this.pixToTile(this.viewBox.topLeft.x + dx, this.viewBox.bottomRight.y + dy);
      MapPoint bottomRight
          = this.pixToTile(this.viewBox.bottomRight.x + dx, this.viewBox.bottomRight.y + dy);

      MapPolygon mapRectTiles = new MapPolygon(topLeft, topRight, bottomLeft, bottomRight);

      if (!mapRectTiles.contains(this.world.getCurrentLevelBounds())) {
        return;
      }
      this.viewBox = this.viewBox.move(dx, dy);
    }
  }

  /**
   * Ticks the gameView and also checks if game is completed.
   * @param timeSinceLastTick time since last tick
   */
  public void onTick(Long timeSinceLastTick) {
    this.updateRenderables(timeSinceLastTick);
    this.updateViewBoxPosition();
  }

  /**
   * Takes the position on the screen an turns it into the Map Point that it is on.
   */
  public /*@ pure; non_null @*/ MapPoint pixToTile(MapPoint screenPos) {
    return this.pixToTile(screenPos.x, screenPos.y);
  }

  /**
   * Takes the position on the screen an turns it into the Map Point that it is on.
   */
  public /*@ pure; non_null @*/ MapPoint pixToTile(double x, double y) {
    int originAdjustedX = (int)(x + this.viewBox.x());
    int originAdjustedY = (int)(y + this.viewBox.y());

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
   * Triggers the drag event.
   */
  public void onDrag(int x1, int y1, int x2, int y2, boolean wasShiftDown, boolean wasCtrlDown) {
    MapPolygon shape = new MapPolygon(this.pixToTile(new MapPoint(x1,y1)),
        this.pixToTile(new MapPoint(x2, y2)),
        this.pixToTile(new MapPoint(x1, y2)),
        this.pixToTile(new MapPoint(x2, y1)));
    gameController.onMouseDrag(new MouseDrag() {
      @Override
      public boolean wasShiftDown() {
        return wasShiftDown;
      }

      @Override
      public boolean wasCtrlDown() {
        return wasCtrlDown;
      }

      @Override
      public MapPolygon getMapShape() {
        return shape;
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
   * Triggers the double click event.
   */
  public void onDbClick(int x, int y, boolean wasShiftDown, boolean wasCtrlDown) {
    this.gameController.onDbClick(new MouseClick() {
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

  /**
   * Triggers the event when a units icon is clicked.
   */
  public void unitClick(Unit unit,
                        boolean wasShiftDown,
                        boolean wasCtrlDown,
                        boolean wasLeftClick) {
    this.gameController.onUnitIconClick(new UnitIconClick() {
      @Override
      public boolean wasShiftDown() {
        return wasShiftDown;
      }

      @Override
      public boolean wasCtrlDown() {
        return wasCtrlDown;
      }

      @Override
      public boolean wasLeftClick() {
        return wasLeftClick;
      }

      @Override
      public Unit getUnit() {
        return unit;
      }
    });
  }

  /**
   * Triggers the event when a abilities icon is clicked.
   */
  public void abilityClick(Ability ability,
                           boolean wasShiftDown,
                           boolean wasCtrlDown,
                           boolean wasLeftClick) {
    this.gameController.onAbilityIconClick(new AbilityIconClick() {
      @Override
      public Ability getAbility() {
        return ability;
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
      public boolean wasLeftClick() {
        return wasLeftClick;
      }
    });
  }

  /**
   * Triggers the event when a item icon is clicked.
   */
  public void itemClick(Item item,
                        boolean wasShiftDown,
                        boolean wasCtrlDown,
                        boolean wasLeftClick) {
    this.gameController.onItemIconClick(new ItemIconClick() {
      @Override
      public Item getItem() {
        return item;
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
      public boolean wasLeftClick() {
        return wasLeftClick;
      }

    });
  }

  /**
   * Pauses the main game loop inside model.
   */
  public void pauseGame() {
    model.pauseGame();
  }

  /**
   * Resumes the main game loop inside model.
   */
  public void resumeGame() {
    model.resumeGame();
  }

  /**
   * Stops the main game loop inside model.
   */
  public void stopGame() {
    model.stopGame();
  }
}
