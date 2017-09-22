package main.game.view;

import java.awt.image.BufferedImage;
import main.game.model.entity.Entity;
import main.util.MapPoint;

/**
 * Created by mgoo on 9/22/17.
 */
public class EntityRenderable implements main.renderer.Renderable {
  static long lastTickTime = -1;
  static long nextTickTime = -1;

  private Entity entity;

  private MapPoint oldPositions;

  EntityRenderable(Entity entity){
    this.entity = entity;
  }

  void update() {

  }

  Entity getEntity() {
    return this.entity;
  }

  @Override
  public MapPoint getImagePosition() {
    double multiplyer = lastTickTime / nextTickTime;
    oldPositions
  }

  @Override
  public BufferedImage getImage() {
    return null;
  }

  @Override
  public MapPoint getEffectiveEntityPosition() {
    return null;
  }
}
