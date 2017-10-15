package main.game.view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Comparator;
import main.common.entity.Entity;
import main.common.images.ImageProvider;
import main.common.util.Config;
import main.common.util.MapPoint;
import main.common.util.MapSize;

/**
 * View of an entity
 * @author Andrew McGhie
 */
public class EntityView implements main.renderer.Renderable {

  final Config config;

  private final Entity entity;
  private final ImageProvider imageProvider;

  BufferedImage currentImage;
  private MapPoint oldPosition;
  private MapPoint destination;

  private long lastTickTime;

  EntityView(Config config, Entity entity, ImageProvider imageProvider) {
    this.config = config;
    this.entity = entity;
    this.oldPosition = entity.getCentre();
    this.destination = entity.getCentre();
    this.imageProvider = imageProvider;
  }

  void update(long tickTime, boolean isSelected) {
    this.lastTickTime = tickTime;
    this.oldPosition = this.destination;
    this.destination = entity.getCentre();
    try {
      this.currentImage = entity.getImage().load(this.imageProvider);
    } catch (IOException e) {
      assert false : "An image was not loaded because: " + e.getMessage();
    }
  }

  Entity getEntity() {
    return this.entity;
  }

  @Override
  public MapPoint getImagePosition(long currentTime) {
    MapPoint entityPosition = this.getEffectiveEntityPosition(currentTime);
    MapPoint entityScreenPosition = tileToPix(entityPosition, config);

    MapSize imageSize = this.getImageSize();

    return new MapPoint(entityScreenPosition.x - imageSize.width / 2D,
        entityScreenPosition.y
            - (imageSize.height
              - (int)(this.entity.getSize().height * (double)this.config.getEntityViewTilePixelsY())
                / 2D));
  }

  @Override
  public MapSize getImageSize() {
    MapSize entityScreenSize = new MapSize(
        (int)(this.entity.getSize().width * this.config.getEntityViewTilePixelsX()),
        (int)(this.entity.getSize().height * this.config.getEntityViewTilePixelsY())
    );

    int entitySpriteHeight =
        (int)Math.round(currentImage.getHeight()
            * (entityScreenSize.width / (double)currentImage.getWidth()));

    return new MapSize(entityScreenSize.width, entitySpriteHeight);
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

  @Override
  public BufferedImage getImage() {
    return this.currentImage;
  }

  /**
   * Calculates the actual MapPoint of this object based on the animation state.
   * This is the position relative to the map not the screen
   *
   * @return the MapPoint of the object considering the animation state
   */
  public MapPoint getEffectiveEntityPosition(long currentTime) {
    double animationMultiplyer = this.calculateAnimationMultiplyer(currentTime);
    double deltaX = (this.destination.x - this.oldPosition.x) * animationMultiplyer;
    double deltaY = (this.destination.y - this.oldPosition.y) * animationMultiplyer;
    return new MapPoint(oldPosition.x + deltaX, oldPosition.y + deltaY);
  }

  /**
   * Calculates the multiplier for a linear animation.
   */
  private double calculateAnimationMultiplyer(long currentTime) {
    return 1D - (((double)this.lastTickTime)
        - ((double)currentTime)) / ((double)this.config.getGameModelDelay());
  }

  static class EntityRenderableComparator implements Comparator<EntityView> {
    private final long currentTime;

    /**
     * .
     * @param currentTime The time stamp for when you want to sort for
     */
    EntityRenderableComparator(long currentTime) {
      this.currentTime = currentTime;
    }

    @Override
    public int compare(EntityView er1, EntityView er2) {
      MapPoint er1Pos = er1.getEffectiveEntityPosition(this.currentTime);
      MapPoint er2Pos = er2.getEffectiveEntityPosition(this.currentTime);
      return (int)((er1Pos.x + er1Pos.y) - (er2Pos.x + er2Pos.y));
    }
  }
}
