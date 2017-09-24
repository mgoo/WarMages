package main.game.view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Comparator;
import main.game.model.GameModel;
import main.game.model.entity.Entity;
import main.images.DefaultImageProvider;
import main.util.MapPoint;
import main.util.MapRect;
import main.util.MapSize;

/**
 * Created by mgoo on 9/22/17.
 */
public class EntityRenderable implements main.renderer.Renderable {

  private static final int tileSize = 50;

  private final Entity entity;

  private BufferedImage currentImage;
  private MapPoint oldPosition;
  private MapPoint destination;
  private long lastTickTime;

  EntityRenderable(Entity entity){
    this.entity = entity;
    this.oldPosition = entity.getPosition();
    this.destination = entity.getPosition();
  }

  void update(long tickTime){
    this.lastTickTime = tickTime;
    this.oldPosition = this.destination;
    this.destination = entity.getPosition();

    try {
      this.currentImage = entity.getImage().load(new DefaultImageProvider());
    } catch (IOException e) {
      System.err.println("Could not load Image " + entity.getImage().name() + ". Using previous image");
      e.printStackTrace();
    }
  }

  Entity getEntity() {
    return this.entity;
  }

  @Override
  public MapPoint getImagePosition(long currentTime) {
    MapPoint entityPosition = this.getEffectiveEntityPosition(currentTime);
    MapPoint entityScreenPosition = new MapPoint(this.calcScreenX(entityPosition),
        this.calcScreenY(entityPosition));

    MapSize entityScreenSize = new MapSize((int)(this.entity.getSize().width * EntityRenderable.tileSize),
        (int)(this.entity.getSize().height * EntityRenderable.tileSize));

    int entitySpriteHeight = (int)(currentImage.getHeight() * (entityScreenSize.width / currentImage.getWidth()));

    return new MapPoint(entityScreenPosition.x - entityScreenSize.width/2,
        entityScreenPosition.y - (entitySpriteHeight - entityScreenSize.height/2));
  }

  /**
   * Calculates the pixel position from a poistion on the tile grid.
   * @param entityPosition
   * @return
   */
  private int calcScreenX(MapPoint entityPosition) {
    return (int)((EntityRenderable.tileSize/2) * (entityPosition.x - entityPosition.y));
  }

  /**
   * Calculates the pixel position from a poistion on the tile grid.
   * @param entityPosition
   * @return
   */
  private int calcScreenY(MapPoint entityPosition) {
    return (int)((EntityRenderable.tileSize/2) * (entityPosition.x - entityPosition.y));
  }

  @Override
  public BufferedImage getImage() {
    return this.currentImage;
  }

  @Override
  public MapPoint getEffectiveEntityPosition(long currentTime) {
    double animationMultiplyer = this.calculateAnimationMultiplyer(currentTime);
    double deltaX = (this.destination.x - this.oldPosition.x) * animationMultiplyer;
    double deltaY = (this.destination.y - this.oldPosition.y) * animationMultiplyer;
    return new MapPoint(oldPosition.x + deltaX, oldPosition.y + deltaY);
  }

  /**
   * Calculates the multiplier for a linear animation
   */
  private double calculateAnimationMultiplyer(long currentTime) {
    return (this.lastTickTime - currentTime) / GameModel.delay;
  }

  static class EntityRenderableComparator implements Comparator<EntityRenderable> {
    private final long currentTime;

    /**
     * @param currentTime The time stamp for when you want to sort for
     */
    EntityRenderableComparator(long currentTime) {
      this.currentTime = currentTime;
    }

    @Override
    public int compare(EntityRenderable er1, EntityRenderable er2) {
      MapPoint er1Pos = er1.getEffectiveEntityPosition(this.currentTime);
      MapPoint er2Pos = er2.getEffectiveEntityPosition(this.currentTime);
      return (int)((er1Pos.x + er1Pos.y) - (er2Pos.x + er2Pos.y));
    }
  }
}
