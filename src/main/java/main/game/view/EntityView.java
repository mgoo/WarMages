package main.game.view;

import java.awt.Graphics2D;
import java.util.Comparator;
import main.game.model.GameModel;
import main.game.model.data.dataobject.ImageData;
import main.game.model.entity.Entity;
import main.util.Config;
import main.util.MapPoint;
import main.util.MapSize;

/**
 * View of an entity.
 * @author Andrew McGhie
 */
public class EntityView implements Renderable {

  final Config config;

  private final Entity entity;

  private ImageData currentImage;
  private MapPoint oldPosition;
  private MapPoint destination;

  private long lastTickTime;

  /** The smaller the number the close to the front. */
  private final int layer;

  EntityView(Config config, Entity entity) {
    this.config = config;
    this.entity = entity;
    this.oldPosition = entity.getTopLeft();
    this.destination = entity.getTopLeft();
    if (entity.getLayer() != -1) {
      this.layer = entity.getLayer();
    } else {
      this.layer = 2;
    }
  }

  @Override
  public void onTick(long tickTime, GameModel model) {
    this.lastTickTime = tickTime;
    this.oldPosition = this.destination;
    this.destination = entity.getTopLeft();
    this.currentImage = entity.getImage();
  }

  Entity getEntity() {
    return this.entity;
  }

  @Override
  public MapPoint getImagePosition(long currentTime) {
    MapPoint entityScreenPosition = this.getEntityScreenPosition(currentTime);
    MapSize entityScreenSize = this.getEntityScreenSize();

    int spritePosX = (int)(entityScreenPosition.x
        - entityScreenSize.width * this.currentImage.getWestOverflow());
    int spritePosY = (int)(entityScreenPosition.y
        - entityScreenSize.height * this.currentImage.getNorthOverflow());

    return new MapPoint(spritePosX, spritePosY);
  }

  @Override
  public MapSize getImageSize() {
    MapSize entitySize = this.getEntityScreenSize();
    int spriteWidth = (int)(entitySize.width
        + entitySize.width * currentImage.getEastOverflow()
        + entitySize.width * currentImage.getWestOverflow());
    int spriteHeight = (int)(entitySize.height
        + entitySize.height * currentImage.getNorthOverflow()
        + entitySize.height * currentImage.getSouthOverflow());

    return new MapSize(spriteWidth, spriteHeight);
  }

  /**
   * Gets the position in pixels of the unit.
   */
  public MapPoint getEntityScreenPosition(long currentTime) {
    return tileToPix(this.getEffectiveEntityPosition(currentTime), config);
  }

  /**
   * Gets the size of the unit in pixels.
   */
  public MapSize getEntityScreenSize() {
    return  new MapSize(
        (int)(this.entity.getSize().width * this.config.getEntityViewTilePixelsX()),
        (int)(this.entity.getSize().height * this.config.getEntityViewTilePixelsY())
    );
  }

  /**
   * Calculates a pixel position (relative to the origin of the view) from a position on the map
   * of tiles for example entity position.
   * @param tilePosition Position in the model
   * @return pixel position still needs to br translated by current origin
   */
  public static MapPoint tileToPix(MapPoint tilePosition, Config config) {
    return new MapPoint(
        ((double)config.getEntityViewTilePixelsX() / 2D) * (tilePosition.x - tilePosition.y),
        ((double)config.getEntityViewTilePixelsY() / 2D) * (tilePosition.x + tilePosition.y)
    );
  }

  /**
   * Calculates the actual MapPoint of this object based on the unitAnimation state.
   * This is the position relative to the map not the screen
   *
   * @return the MapPoint of the object considering the unitAnimation state
   */
  public MapPoint getEffectiveEntityPosition(long currentTime) {
    double animationMultiplyer = 1D - (((double)this.lastTickTime)
        - ((double)currentTime)) / ((double)this.config.getGameModelDelay());
    double deltaX = (this.destination.x - this.oldPosition.x) * animationMultiplyer;
    double deltaY = (this.destination.y - this.oldPosition.y) * animationMultiplyer;
    return new MapPoint(oldPosition.x + deltaX, oldPosition.y + deltaY);
  }

  @Override
  public ImageData getImage() {
    return this.currentImage;
  }

  @Override
  public void drawDecorationsBeneth(Graphics2D g, int x, int y, int width, int height) {

  }

  @Override
  public void drawDecorationsOntop(Graphics2D g, int x, int y, int width, int height) {

  }

  @Override
  public int getLayer() {
    return this.layer;
  }

  static class EntityRenderableComparator implements Comparator<Renderable> {
    private final long currentTime;

    /**
     * .
     * @param currentTime The time stamp for when you want to sort for
     */
    EntityRenderableComparator(long currentTime) {
      this.currentTime = currentTime;
    }

    @Override
    public int compare(Renderable er1, Renderable er2) {
      if (er1.getLayer() == er2.getLayer()) {
        MapPoint er1Pos = er1.getEffectiveEntityPosition(this.currentTime);
        MapPoint er2Pos = er2.getEffectiveEntityPosition(this.currentTime);
        return Double.compare(er1Pos.x + er1Pos.y, er2Pos.x + er2Pos.y);
      } else {
        return Integer.compare(er2.getLayer(), er1.getLayer());
      }
    }
  }
}
